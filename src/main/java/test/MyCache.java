package test;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class MyCache {

    private static final Logger LOG = LoggerFactory.getLogger(MyCache.class);

    public static final String CACHE_NAME = "myCache";
    public static final long EXPIRATION = 60L;

    private RedisCache redisCache;
    private RedisTemplate redisTemplate;

    public MyCache(RedisCache redisCache, RedisTemplate redisTemplate) {
        this.redisCache = redisCache;
        this.redisTemplate = redisTemplate;
    }

    public Cache.ValueWrapper get(final Object key) {
        return this.redisCache.get(key);
    }

    public void put(final Object key, final Object value) {
        this.redisCache.put(key, value);
    }

    public Iterable getKeys() {
        Iterable result = ImmutableSet.of();

        try {
            Cursor<byte[]> cursor = this.scan();
            if (cursor != null) {
                result = deserializeKeys(cursor);
                this.close(cursor);
            }
        } catch (QueryTimeoutException e) {
            LOG.info("Redis timeout.", e);
        } catch (Exception e) {
            LOG.error("Exception", e);
        }

        return result;
    }

    private Set deserializeKeys(final Cursor<byte[]> cursor) {
        Set keys = new HashSet(100);
        while (cursor.hasNext()) {
            byte[] key = cursor.next();
            byte[] rawKey = new byte[key.length - CACHE_NAME.getBytes().length];
            System.arraycopy(key, CACHE_NAME.getBytes().length, rawKey, 0, rawKey.length);
            keys.add(redisTemplate.getDefaultSerializer().deserialize(rawKey));
        }

        return keys;
    }

    private void close(final Cursor<byte[]> cursor) {
        try {
            cursor.close();
        } catch (IOException e) {
            LOG.warn("IOException when we tried to close the cursor.", e);
        }
    }

    private Cursor<byte[]> scan() {
        Object o = redisTemplate.execute(new RedisCallback<Cursor<byte[]>>() {
            @Override
            public Cursor<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.scan(new ScanOptions.ScanOptionsBuilder().match(CACHE_NAME + "*").build());
            }
        });

        Cursor<byte[]> cursor = null;
        if (o != null) {
            cursor = (Cursor<byte[]>) o;
        }

        return cursor;
    }
}
