package app.teamwize.api.scheduling.service.runner;


import app.teamwize.api.scheduling.model.ExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationJobRunner implements JobRunner {

    private static final Logger logger = LoggerFactory.getLogger(NotificationJobRunner.class);

    @Override
    public JobResult execute(Map<String,Object> metadata) {
        logger.info("Sending notification ");
        return new JobResult(ExecutionStatus.SUCCESS, Map.of("name", "Mohsen"));
    }

    @Override
    public String name() {
        return NotificationJobRunner.class.getSimpleName();
    }
}
