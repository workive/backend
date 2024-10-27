//package app.teamwize.api.notification.job;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class EmailSenderTask implements Tasklet {
//
//    private static final Logger logger = LoggerFactory.getLogger(EmailSenderTask.class);
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        // Your email sending logic here
//        logger.info("Sending emails : {} : {}", contribution, chunkContext);
//
//        // For demonstration, we will just print. In real use cases, you would call the email service here.
//        // emailService.sendEmails();
//
//        return RepeatStatus.CONTINUABLE;
//    }
//}
