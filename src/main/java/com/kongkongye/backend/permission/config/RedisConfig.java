package com.kongkongye.backend.permission.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kongkongye.backend.permission.common.RedisCacheManager;
import com.kongkongye.backend.permission.common.RedisConsts;
import com.kongkongye.backend.permission.entity.dept.Dept;
import com.kongkongye.backend.permission.entity.per.BizDir;
import com.kongkongye.backend.permission.entity.per.PerValue;
import com.kongkongye.backend.permission.repository.BizDirRepository;
import com.kongkongye.backend.permission.repository.DeptRepository;
import com.kongkongye.backend.permission.repository.PerValueRepository;
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
    private DeptRepository deptRepository;
    @Autowired
    private PerValueRepository perValueRepository;
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

    @Bean("deptTreeCacheManager")
    public RedisCacheManager<List<Dept>> deptTreeCacheManager() {
        return new RedisCacheManager<List<Dept>>("部门", RedisConsts.REDIS_CACHE_DEPT_TREE, RedisConsts.REDIS_SUB_DEPT_TREE, stringRedisTemplate) {

            @Override
            protected List<Dept> loadData() {
                return Lists.newArrayList(deptRepository.findAll());
            }

            @Override
            protected String encode(List<Dept> data) {
                return JSON.toJSONString(data);
            }

            @Override
            protected List<Dept> decode(String data) {
                return JSON.parseArray(data, Dept.class);
            }
        };
    }

    @Bean("perValueCacheManager")
    public RedisCacheManager<List<PerValue>> perValueCacheManager() {
        return new RedisCacheManager<List<PerValue>>("权限值", RedisConsts.REDIS_CACHE_PER_VALUE_TREE, RedisConsts.REDIS_SUB_PER_VALUE_TREE, stringRedisTemplate) {

            @Override
            protected List<PerValue> loadData() {
                return Lists.newArrayList(perValueRepository.findAll());
            }

            @Override
            protected String encode(List<PerValue> data) {
                return JSON.toJSONString(data);
            }

            @Override
            protected List<PerValue> decode(String data) {
                return JSON.parseArray(data, PerValue.class);
            }
        };
    }

    @Bean
    public RedisMessageListenerContainer messageDelegate(
            RedisConnectionFactory connectionFactory,
            @Qualifier("bizDirTreeCacheManager") RedisCacheManager<List<BizDir>> bizDirRedisCacheManager,
            @Qualifier("deptTreeCacheManager") RedisCacheManager<List<Dept>> deptRedisCacheManager,
            @Qualifier("perValueCacheManager") RedisCacheManager<List<PerValue>> perValueCacheManager
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(bizDirRedisCacheManager, new ChannelTopic(RedisConsts.REDIS_SUB_BIZ_DIR_TREE));
        container.addMessageListener(deptRedisCacheManager, new ChannelTopic(RedisConsts.REDIS_SUB_DEPT_TREE));
        container.addMessageListener(perValueCacheManager, new ChannelTopic(RedisConsts.REDIS_SUB_PER_VALUE_TREE));
        return container;
    }
}
