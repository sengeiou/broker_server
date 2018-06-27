package com.xyauto.interact.broker.server.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xyauto.interact.broker.server.util.SnowflakeIdWorker;

@Configuration
public class SnowflakeIdWorkerConfig {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        long workerId = getRantom();
        long datacenterId = getRantom();
        return new SnowflakeIdWorker(workerId, datacenterId);
    }

    private long getRantom() {
        int max = 31;
        int min = 1;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return Long.valueOf("" + s);
    }
}
