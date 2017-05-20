package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * RedisApp
 */
public class RedisApp {

    private static final Logger LOG = LoggerFactory.getLogger(RedisApp.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        MyCache myCache = ctx.getBean("myCache", MyCache.class);

        MyCacheKey seinfeldKey = new MyCacheKey();
        seinfeldKey.setSeasonNumber(1);
        seinfeldKey.setEpisodeNumber(1);
        seinfeldKey.setEpisodeName("The Seinfeld Chronicles");
        seinfeldKey.setFirstAired(LocalDateTime.of(1989, 7, 5, 16, 00));

        MyCacheValue seinfeldValue = new MyCacheValue();
        seinfeldValue.setEpisodeDescription("\"The Seinfeld Chronicles\" " +
                "(also known as \"Good News, Bad News\" or \"Pilot\")[2] " +
                "is the pilot episode of the American sitcom Seinfeld, " +
                "which first aired on NBC on July 5, 1989.");

        Cache.ValueWrapper value = myCache.get(seinfeldKey);
        if (value == null) {
            LOG.info("The key {} was not found in Redis.", seinfeldKey);
        } else {
            LOG.info("For the key {}, we have found the value {}", seinfeldKey, value.get());
        }

        myCache.put(seinfeldKey, seinfeldValue);

        Cache.ValueWrapper value2 = myCache.get(seinfeldKey);
        if (value2 == null) {
            LOG.info("The key {} was not found in Redis.", seinfeldKey);
        } else {
            LOG.info("For the key {}, we have found the value {}", seinfeldKey, value2.get());
        }

        LOG.info("Brace yourself, " +
                "we are trying to get all the keys of \"MyCache\" from Redis.");
        Iterable keys = myCache.getKeys();

        LOG.info("We found the keys {}", keys);

    }
}
