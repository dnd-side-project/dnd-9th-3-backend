package com.dnd.gooding.springconfig.async;

import com.dnd.gooding.springconfig.log.MDCCopyTaskDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setTaskDecorator(new MDCCopyTaskDecorator());
        taskExecutor.setThreadNamePrefix("async-task-");
        taskExecutor.setThreadGroupName("async-group");
        return taskExecutor;
    }
}
