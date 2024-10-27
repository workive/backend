package app.teamwize.api.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender(org.springframework.core.env.Environment env) {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port","587")));
        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));

        var props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        props.put("mail.debug", "false"); // Set to true for debugging
        props.put("mail.transport.protocol", "smtp");

        return mailSender;
    }

//    @Bean
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob().ofType(EmailSenderJob.class)
//                .storeDurably()
//                .withIdentity("EmailSenderJob")
//                .withDescription("Invoke Sample Job service...")
//                .build();
//    }
//
//    @Bean
//    public Trigger trigger(JobDetail job) {
//        return TriggerBuilder.newTrigger().forJob(job)
//                .withIdentity("EmailSenderJobTrigger")
//                .withDescription("Sample trigger")
//                .withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(2))
//                .build();
//    }
//
//
//    @Bean
//    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory)
//            throws SchedulerException {
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.scheduleJob(job, trigger);
//        scheduler.start();
//        return scheduler;
//    }
}
