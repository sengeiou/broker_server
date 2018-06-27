package com.xyauto.interact.broker.server.config;

import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class GuavaConfig {

    private static final int DEFAULT_MAXSIZE = 10000;
    private static final int DEFAULT_TTL = 3600;

    public enum Caches {
        data_attach(60, 100000),
        series(3600, 100000),
        subbrand(3600, 10000),
        brand(3600, 1000),
        car(3600, 100000),
        city(3600*24, 10000),
        province(3600*24, 100);

        Caches() {
        }

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        private int maxSize = DEFAULT_MAXSIZE;    //最大數量
        private int ttl = DEFAULT_TTL;        //过期时间（秒）

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        ArrayList<GuavaCache> caches = new ArrayList<>();
        for (Caches c : Caches.values()) {
            caches.add(new GuavaCache(c.name(), CacheBuilder.newBuilder().recordStats().expireAfterWrite(c.getTtl(), TimeUnit.SECONDS).maximumSize(c.getMaxSize()).build()));
        }
        manager.setCaches(caches);
        return manager;
    }
}
