package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.Task;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .tasklet((StepContribution stepContribution, ChunkContext chunkContext) -> {
                    System.out.println(">> step1 was executed");
                    return RepeatStatus.FINISHED;

                })
                .build();
    }
    @Bean
    public Step step2(){

        return stepBuilderFactory.get("step2")
                .tasklet((StepContribution stepContribution, ChunkContext chunkContext) -> {
                        System.out.println(">> step2 was executed");
                        throw new RuntimeException("step2 has failed");
//                        return RepeatStatus.FINISHED;

                })
                .build();
    }

    @Bean
    public Step step3(){
        return stepBuilderFactory.get("step3")
                .tasklet((StepContribution stepContribution, ChunkContext chunkContext) -> {
                    System.out.println(">> step3 was executed");
                    return RepeatStatus.FINISHED;

                })
                .build();
    }

}
