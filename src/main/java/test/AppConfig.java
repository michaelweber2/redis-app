package test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 */
@Configuration
@EnableMBeanExport
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public RedisCacheConnectionPoolProperties redisCacheConnectionPoolProperties() {
        return new RedisCacheConnectionPoolProperties();
    }

    @Bean
    public DefaultLettucePool defaultLettucePool() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();

        DefaultLettucePool defaultLettucePool = null;

        try {
            GenericObjectPoolConfig poolConfig = createPoolConfig();
            defaultLettucePool = new DefaultLettucePool(
                    poolProperties.getHostname(),
                    poolProperties.getPort(),
                    poolConfig
            );

            if (poolProperties.isUseRedisPassword()) {
                defaultLettucePool.setPassword(poolProperties.getRedisPassword());
            }
            defaultLettucePool.setTimeout(poolProperties.getTimeout());
        } catch (Exception e) {
            LOG.warn("Exception", e);
        }

        return defaultLettucePool;
    }

    private GenericObjectPoolConfig createPoolConfig() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(poolProperties.getMaxTotal());
        poolConfig.setMinIdle(poolProperties.getMinIdle());
        poolConfig.setMaxIdle(poolProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(poolProperties.getMaxWaitMillis());
        poolConfig.setTestOnCreate(poolProperties.isTestOnCreate());
        poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
        poolConfig.setTestOnReturn(poolProperties.isTestOnReturn());
        poolConfig.setTestWhileIdle(poolProperties.isTestWhileIdle());
        poolConfig.setMinEvictableIdleTimeMillis(poolProperties.getMinEvictableIdleTimeMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(poolProperties.getTimeBetweenEvictionRunsMillis());

        return poolConfig;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();

        LettuceConnectionFactory lettuceConnectionFactory = null;

        try {
            lettuceConnectionFactory = new LettuceConnectionFactory(defaultLettucePool());
            lettuceConnectionFactory.setTimeout(poolProperties.getTimeout());
        } catch (Exception e) {
            LOG.warn("Exception", e);
        }

        return lettuceConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(poolProperties.getMaxTotal());
        poolConfig.setMinIdle(poolProperties.getMinIdle());
        poolConfig.setMaxIdle(poolProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(poolProperties.getMaxWaitMillis());
        poolConfig.setTestOnCreate(poolProperties.isTestOnCreate());
        poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
        poolConfig.setTestOnReturn(poolProperties.isTestOnReturn());
        poolConfig.setTestWhileIdle(poolProperties.isTestWhileIdle());
        poolConfig.setMinEvictableIdleTimeMillis(poolProperties.getMinEvictableIdleTimeMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(poolProperties.getTimeBetweenEvictionRunsMillis());

        return poolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
        jedisConnectionFactory.setHostName(poolProperties.getHostname());
        jedisConnectionFactory.setPort(poolProperties.getPort());
        jedisConnectionFactory.setTimeout((int) poolProperties.getTimeout());

        if (poolProperties.isUseRedisPassword()) {
            jedisConnectionFactory.setPassword(poolProperties.getRedisPassword());
        }

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisCacheConnectionPoolProperties poolProperties = redisCacheConnectionPoolProperties();
        RedisTemplate redisTemplate = null;

        try {
            redisTemplate = new RedisTemplate();

            if (poolProperties.isUseJedis()) {
                redisTemplate.setConnectionFactory(jedisConnectionFactory());
                LOG.info("redisTemplate has been initialized with Jedis.");
            } else {
                redisTemplate.setConnectionFactory(lettuceConnectionFactory());
                LOG.info("redisTemplate has been initialized with Lettuce.");
            }

        } catch (Exception e) {
            LOG.warn("Exception during ResidTemplate initialisation. " +
                    "Application will continue without Redis cache.", e);
        }

        return redisTemplate;
    }

    @Bean
    public RedisCache redisCache() {
        return new RedisCache(MyCache.CACHE_NAME, MyCache.CACHE_NAME.getBytes(), redisTemplate(), MyCache.EXPIRATION);
    }

    @Bean
    public MyCache myCache() {
        return new MyCache(redisCache(), redisTemplate());
    }
}
