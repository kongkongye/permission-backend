package com.kongkongye.backend.permission.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kongkongye.backend.permission.common.RedisCacheManager;
import com.kongkongye.backend.permission.common.RedisConsts;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.repository.BizDirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

@Configuration
public class RedisConfig {
    @Autowired
    private BizDirRepository bizDirRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean("bizDirTreeCacheManager")
    public RedisCacheManager<List<BizDir>> bizDirRedisCacheManager() {
        return new RedisCacheManager<List<BizDir>>("业务对象目录", RedisConsts.REDIS_CACHE_BIZ_DIR_TREE, RedisConsts.REDIS_SUB_BIZ_DIR_TREE, stringRedisTemplate) {

            @Override
            protected List<BizDir> loadData() {
                return Lists.newArrayList(bizDirRepository.findAll());
            }

            @Override
            protected String encode(List<BizDir> data) {
                return JSON.toJSONString(data);
            }

            @Override
            protected List<BizDir> decode(String data) {
                return JSON.parseArray(data, BizDir.class);
            }
        };
    }

    @Bean
    public RedisMessageListenerContainer messageDelegate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("bizDirTreeCacheManager") RedisCacheManager<List<BizDir>> bizDirRedisCacheManager
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(bizDirRedisCacheManager, new ChannelTopic(RedisConsts.REDIS_SUB_BIZ_DIR_TREE));
        return container;
    }
}
