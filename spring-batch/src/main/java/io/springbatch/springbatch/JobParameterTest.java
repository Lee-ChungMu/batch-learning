package io.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobParameterTest implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 2L)
                .addDate("date", new Date())
                .addDouble("age", 16.5)
                .toJobParameters();

        //위처럼 추가하지 않고 jar파일을 직접실행할때 (maven 패키지를 하여)
        //java -jar spring-batch-0.0.1-SNAPSHOT.jar name=user1 seq(long)=2L date(date)=2023/03/07 age(double)=16.5와 같이 파라미터 추가가 가능하다.
        //또한 spring 구성 편집에서 program arguments를 추가하여 사용도 가능하다.
        jobLauncher.run(job, jobParameters);
    }
}
