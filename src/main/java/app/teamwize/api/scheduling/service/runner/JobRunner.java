package app.teamwize.api.scheduling.service.runner;

import app.teamwize.api.scheduling.model.ExecutionStatus;

import java.util.Map;

public interface JobRunner {

    JobResult execute(Map<String,Object> metadata);

    String name();


    record JobResult(ExecutionStatus status, Map<String, Object> metadata) {
    }
}
