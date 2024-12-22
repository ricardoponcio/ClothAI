package dev.poncio.ClothAI.aiexecution.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfiguration {

    @Value("${thread-pool.clothai-execution.core-pool-size}")
    private Integer corePoolSize;

    @Value("${thread-pool.clothai-execution.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${thread-pool.clothai-execution.queue-capacity}")
    private Integer queueCapacity;

    @Value("${thread-pool.clothai-execution.timeout}")
    private Integer timeout;

    @Bean(name = "clothAiExecutionThreadPool")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("ClothAiExecution-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(timeout);
        executor.initialize();
        return executor;
    }

}
