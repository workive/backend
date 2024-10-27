//package app.teamwize.api.notification.config;
//
//
//import app.teamwize.api.notification.job.EmailSenderTask;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableBatchProcessing(
//        dataSourceRef = "dataSource",
//        tablePrefix = "batch_",
//        databaseType    = "POSTGRES"
//)
//@RequiredArgsConstructor
//public class JobConfig {
//
//
//    private final PlatformTransactionManager transactionManager;
//    private final EmailSenderTask emailSenderTasklet;
//
//
////    @Bean
////    public JobRepository jobRepository() throws Exception {
////        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
////        factory.setDataSource(dataSource);
////        factory.setTransactionManager(new ResourcelessTransactionManager());
////        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
////        factory.setTablePrefix("BATCH_");
////        factory.setDatabaseType("postgresql");
////        return factory.getObject();
////    }
//
////    @Bean
////    public Job emailSenderJob(JobRepository jobRepository) {
////        return new JobBuilder("emailSenderJob", jobRepository)
////                .start(emailSenderStep(jobRepository))
////                .build();
////    }
////
////
////    public Step emailSenderStep(JobRepository jobRepository) {
////        return new StepBuilder("emailSenderStep", jobRepository)
////                .tasklet(emailSenderTasklet, transactionManager)
////                .build();
////    }
//
////    @Bean
////    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
////        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
////        jobLauncher.setJobRepository(jobRepository);
////        jobLauncher.afterPropertiesSet();
////        return jobLauncher;
////    }
//}
