package app.teamwize.api.scheduling.model;

public enum ExecutionStatus {
    SUCCESS,      // The job completed successfully
    FAILED,       // The job execution failed
    RETRY,        // The job is set to retry after a failure
    SKIPPED,      // The job was skipped, possibly due to certain conditions
    IN_PROGRESS,  // The job is currently in progress
    CANCELED      // The job execution was canceled
}
