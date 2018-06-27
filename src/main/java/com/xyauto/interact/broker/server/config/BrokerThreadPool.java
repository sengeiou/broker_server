package com.xyauto.interact.broker.server.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class BrokerThreadPool {

    private int corePoolSize = 50;//线程池维护线程的最少数量

    private int maxPoolSize = 8000;//线程池维护线程的最大数量

    private int queueCapacity = 2000; //缓存队列

    private int keepAlive = 600;//允许的空闲时间

    @Bean("executor")
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("executor-"); 
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(keepAlive);
        executor.initialize();
        return executor;
    }

}
