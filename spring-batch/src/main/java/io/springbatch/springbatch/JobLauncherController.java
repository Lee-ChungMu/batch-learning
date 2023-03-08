package io.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JobLauncherController {
    @Autowired
    private Job job;
    @Autowired
    private JobLauncher simpleJobLauncher;
    @Autowired
    private BasicBatchConfigurer basicBatchConfigurer;
    @PostMapping("/batch")
    public String launch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        //동기
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("id", member.getId())
//                .addDate("date", new Date())
//                .toJobParameters();
//        jobLauncher.run(job, jobParameters);

        //비동기
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();
        SimpleJobLauncher jobLauncher = (SimpleJobLauncher)basicBatchConfigurer.getJobLauncher();
//        SimpleJobLauncher jobLauncher = (SimpleJobLauncher)simpleJobLauncher; //이거는 오류 타입캐스팅 오류
//        simpleJobLauncher가 실제로는 그 타입의 상속을 받은 프록시 객체이다. 그래서 simpleJobLauncher를 실제 SimpleJobLauncher로 타입 캐스팅이 불가
        //프록시 객체의 실제 객체는 basicBatchConfigurer이 가지고있다.
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.run(job, jobParameters);
        return "batch completed";
    }
}
