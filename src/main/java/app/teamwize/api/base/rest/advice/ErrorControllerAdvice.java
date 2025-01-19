package app.teamwize.api.base.rest.advice;


import app.teamwize.api.base.exception.BaseException;
import app.teamwize.api.base.exception.NotFoundException;
import app.teamwize.api.base.rest.domain.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Request body is not readable",
                request.getRequestURI()
        );
        return toResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapToValidationErrorDetail)
                .collect(Collectors.toList());

        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed: One or more fields contain invalid values.",
                request.getRequestURI(),
                details
        );
        return toResponseEntity(errorResponse);
    }

    @ExceptionHandler({NoResourceFoundException.class, NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage() != null ? ex.getMessage() : "Resource not found",
                request.getRequestURI()
        );
        return toResponseEntity(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestPartException(
            MissingServletRequestPartException ex, HttpServletRequest request) {
        logger.error("An unexpected error occurred", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage() != null ? ex.getMessage() : "Request body is not readable",
                request.getRequestURI()
        );
        return toResponseEntity(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        logger.error("An unexpected error occurred", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.status(),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred",
                request.getRequestURI()
        );
        return toResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("An unexpected error occurred", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request.getRequestURI()
        );
        return toResponseEntity(errorResponse);
    }

    private ErrorResponse.ValidationErrorDetail mapToValidationErrorDetail(FieldError fieldError) {
        return new ErrorResponse.ValidationErrorDetail(
                fieldError.getField(),
                fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "",
                fieldError.getCode() != null ? fieldError.getCode() : "",
                fieldError.getRejectedValue()
        );
    }

    private ResponseEntity<ErrorResponse> toResponseEntity(ErrorResponse error) {
        return ResponseEntity.status(HttpStatus.valueOf(error.status())).body(error);
    }
}