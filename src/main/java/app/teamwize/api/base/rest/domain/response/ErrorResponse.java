package app.teamwize.api.base.rest.domain.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        List<ValidationErrorDetail> details
) {
    // Constructor with default timestamp and empty details
    public ErrorResponse(HttpStatus status, String message, String path) {
        this(status.value(), status.name(), message, path, LocalDateTime.now(), Collections.emptyList());
    }

    // Constructor with default timestamp and provided details
    public ErrorResponse(HttpStatus status, String message, String path, List<ValidationErrorDetail> details) {
        this(status.value(), status.name(), message, path, LocalDateTime.now(), details != null ? details : Collections.emptyList());
    }

    public record ValidationErrorDetail(
            String field,
            String message,
            String constraint,
            Object value
    ) {
    }
}


