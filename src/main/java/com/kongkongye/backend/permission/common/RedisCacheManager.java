package com.kongkongye.backend.permission.common;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 内存 - 缓存 - db
 */
@Slf4j
public abstract class RedisCacheManager<V> implements MessageListener {
    @FunctionalInterface
    public interface UpdateNotifier {
        void onUpdate();
    }

    private final UUID uid = UUID.randomUUID();
    private final String name;//显示名称
    private final String cacheKey;//缓存key
    private final String subKey;//订阅key
    private final StringRedisTemplate template;

    private Optional<V> data = Optional.empty();

    private final List<UpdateNotifier> updateNotifiers = new ArrayList<>();

    public RedisCacheManager(String name, String cacheKey, String subKey, StringRedisTemplate template) {
        this.name = name;
        this.cacheKey = cacheKey;
        this.subKey = subKey;
        this.template = template;
    }

    public void addUpdateNotifier(UpdateNotifier notifier) {
        updateNotifiers.add(notifier);
    }

    /**
     * 刷新缓存
     * (外部更新了db时需要调用)
     */
    public void refreshCache() {
        //请求db
        V v = loadData();
        //更新缓存
        updateCache(v);
    }

    /**
     * 获取数据
     */
    public V getData() {
        //1. 从内存获取
        if (data.isPresent()) {
            return data.get();
        }

        //2. 请求缓存
        V v = getDataFromCache();
        //更新内存
        updateMemory(v);
        //返回
        return v;
    }

    /**
     * 从缓存中加载数据
     */
    private V getDataFromCache() {
        //1. 查询缓存
        if (template.hasKey(cacheKey)) {
            String encodedData = template.opsForValue().get(cacheKey);
            if (!Strings.isNullOrEmpty(encodedData)) {
                return decode(encodedData);
            } else {
                return null;
            }
        }

        //2. 请求db
        V v = loadData();
        //更新缓存
        updateCache(v);
        //返回
        return v;
    }

    /**
     * 更新内存
     */
    private void updateMemory(@Nullable V v) {
        //更新内存
        data = Optional.ofNullable(v);
        //通知更新
        for (UpdateNotifier updateNotifier : updateNotifiers) {
            updateNotifier.onUpdate();
        }
    }

    /**
     * 更新缓存
     */
    private void updateCache(@Nullable V v) {
        //更新缓存
        String encodedData = v != null ? encode(v) : "";
        template.opsForValue().set(cacheKey, encodedData);
        //通知更新
        template.convertAndSend(subKey, uid.toString());
        log.info("[{}]发布缓存更新通知", name);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String uid = new String(message.getBody());
        boolean update = !Objects.equals(uid, this.uid.toString());
        log.info("[{}]收到更新通知{}", name, update ? "" : "(自己发出，忽略)");
        //重置内存
        data = Optional.empty();
        //重新加载数据
        getData();
    }

    /**
     * 读取数据（从数据库里）
     */
    @Nullable
    protected abstract V loadData();

    protected abstract String encode(V data);

    protected abstract V decode(String data);
}
