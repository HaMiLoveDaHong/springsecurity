package com.hami.spring.security.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 文件配置
 * @author zhanghongwei
 * @since 2019/11/6
 */
@Slf4j
@Configuration
//必须加，使配置生效
@EnableCaching
@EnableRedisHttpSession
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.url}")
    private  String host;
    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.timeout}")
    private  int timeout;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        return jedisConnectionFactory;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout,password);
        return  jedisPool;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        //设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        //使用：进行分割，可以很多显示出层级关系
        //这里其实就是new了一个keyGenerator对象，这是lambda表达式的写法
        return (target,method,params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object o:params){
                sb.append(":"+String.valueOf(o));
            }
            String rsToUse = String.valueOf(sb);
            log.info("自动生成Redis key -> [{}]",rsToUse);
            return rsToUse;
        };
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        //初始化缓存管理器，在这里我门可以缓存的整体过期时间什么的，我这里默认没有配置
        log.info("初始化 -> [{}]","CacheManage RedisCacheManager Start");
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory());
        return builder.build();
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        //设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        PolymorphicTypeValidator pt = BasicPolymorphicTypeValidator.builder().build();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(pt,ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //配置redisTemplate
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        //key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        //value 序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //hash key 序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        //hash value 序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return  redisTemplate;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        //异常处理，当Redis 发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]","Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError:key -> [{}]",key,e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError:key -> [{}]; value -> [{}]",key,value,e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError:key -> [{}]",key,e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError:key -> [{}]",e);
            }
        };
        return cacheErrorHandler;
    }


}
