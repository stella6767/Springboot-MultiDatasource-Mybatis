package net.lunalabs.central.config.async;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import net.lunalabs.central.handler.CustomAsyncExceptionHandler;

@EnableScheduling
@EnableAsync
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport{

    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
		executor.setMaxPoolSize(10000);
		executor.setQueueCapacity(10000);
		executor.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());
        executor.setThreadNamePrefix("kang-");
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();

    }

    @Bean
    public ThreadPoolTaskScheduler configureTasks() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(5000);
        threadPoolTaskScheduler.setThreadNamePrefix("kang-scheduled-");
        threadPoolTaskScheduler.initialize();

        return threadPoolTaskScheduler;
    }

	
}
