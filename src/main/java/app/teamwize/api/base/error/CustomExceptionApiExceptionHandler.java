//package app.workive.api.base.error;
//
//import io.github.wimdeblauwe.errorhandlingspringbootstarter.*;
//import io.github.wimdeblauwe.errorhandlingspringbootstarter.handler.AbstractApiExceptionHandler;
//import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorCodeMapper;
//import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorMessageMapper;
//import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.HttpStatusMapper;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ElementKind;
//import jakarta.validation.Path;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.util.Pair;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Component
//public class CustomExceptionApiExceptionHandler extends AbstractApiExceptionHandler {
//
//    private static final Collection<String> IGNORE_ATTRIBUTES = List.of("groups", "payload", "message");
//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionApiExceptionHandler.class);
//    private final ErrorHandlingProperties properties;
//
//    public CustomExceptionApiExceptionHandler(ErrorHandlingProperties properties,
//                                              HttpStatusMapper httpStatusMapper,
//                                              ErrorCodeMapper errorCodeMapper,
//                                              ErrorMessageMapper errorMessageMapper) {
//        super(httpStatusMapper, errorCodeMapper, errorMessageMapper);
//        this.properties = properties;
//    }
//
//    @Override
//    public boolean canHandle(Throwable exception) {
//        return exception instanceof MethodArgumentNotValidException;
//    }
//
//    @Override
//    public ApiErrorResponse handle(Throwable exception) {
//        var errors = new ArrayList<ApiErrorItem>();
//        var ex = (MethodArgumentNotValidException) exception;
//        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
//                getErrorCode(exception),
//                getMessage(ex));
//        var violations = ex.getAllErrors().stream().map(s -> s.unwrap(ConstraintViolation.class)).toList();
//        violations.stream()
//                .sorted(Comparator.comparing(constraintViolation -> constraintViolation.getPropertyPath().toString()))
//                .map(constraintViolation -> {
//                    Optional<Path.Node> leafNode = getLeafNode(constraintViolation.getPropertyPath());
//                    if (leafNode.isPresent()) {
//                        Path.Node node = leafNode.get();
//                        ElementKind elementKind = node.getKind();
//                        if (elementKind == ElementKind.PROPERTY) {
//                            return Pair.of(new ApiFieldError(getCode(constraintViolation),
//                                    node.toString(),
//                                    getMessage(constraintViolation),
//                                    constraintViolation.getInvalidValue(),
//                                    getPath(constraintViolation)), constraintViolation);
//                        } else if (elementKind == ElementKind.BEAN) {
//                            return Pair.of(new ApiGlobalError(getCode(constraintViolation),
//                                    getMessage(constraintViolation)), constraintViolation);
//                        } else if (elementKind == ElementKind.PARAMETER) {
//                            return Pair.of(new ApiParameterError(getCode(constraintViolation),
//                                    node.toString(),
//                                    getMessage(constraintViolation),
//                                    constraintViolation.getInvalidValue()), constraintViolation);
//                        } else {
//                            LOGGER.warn("Unable to convert constraint violation with element kind {}: {}", elementKind, constraintViolation);
//                            return null;
//                        }
//                    } else {
//                        LOGGER.warn("Unable to convert constraint violation: {}", constraintViolation);
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull)
//                .forEach(error -> {
//                    var pair = error.getFirst();
//                    var second = error.getSecond();
//                    if (pair instanceof ApiFieldError e) {
//                        errors.add(new ApiErrorItem(e.getCode(), e.getMessage(), getArguments(second)));
//                    } else if (pair instanceof ApiGlobalError e) {
//                        errors.add(new ApiErrorItem(e.getCode(), e.getMessage(), getArguments(second)));
//                    } else if (pair instanceof ApiParameterError e) {
//                        errors.add(new ApiErrorItem(e.getCode(), e.getMessage(), getArguments(second)));
//                    }
//                });
//
//        response.addErrorProperty("errors", errors);
//
//        return response;
//    }
//
//    private Optional<Path.Node> getLeafNode(Path path) {
//        return StreamSupport.stream(path.spliterator(), false).reduce((a, b) -> b);
//    }
//
//    private String getPath(ConstraintViolation<?> constraintViolation) {
//        if (!properties.isAddPathToError()) {
//            return null;
//        }
//
//        return getPathWithoutPrefix(constraintViolation.getPropertyPath());
//    }
//
//    /**
//     * This method will truncate the first 2 parts of the full property path so the
//     * method name and argument name are not visible in the returned path.
//     *
//     * @param path the full property path of the constraint violation
//     * @return The truncated property path
//     */
//    private String getPathWithoutPrefix(Path path) {
//        String collect = StreamSupport.stream(path.spliterator(), false)
//                .limit(2)
//                .map(Path.Node::getName)
//                .collect(Collectors.joining("."));
//        String substring = path.toString().substring(collect.length());
//        return substring.startsWith(".") ? substring.substring(1) : substring;
//    }
//
//    private String getCode(ConstraintViolation<?> constraintViolation) {
//        String code = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
//        String fieldSpecificCode = constraintViolation.getPropertyPath().toString() + "." + code;
//        return errorCodeMapper.getErrorCode(fieldSpecificCode, code);
//    }
//
//    private String getMessage(ConstraintViolation<?> constraintViolation) {
//        String code = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
//        String fieldSpecificCode = constraintViolation.getPropertyPath().toString() + "." + code;
//        return errorMessageMapper.getErrorMessage(fieldSpecificCode, code, constraintViolation.getMessage());
//    }
//
//    private String getMessage(MethodArgumentNotValidException exception) {
//        return "Validation failed. Error count: " + exception.getAllErrors().size();
//    }
//
//    Map<String, Object> getArguments(ConstraintViolation<?> violation) {
//        var args = new HashMap<>(violation.getConstraintDescriptor().getAttributes());
//
//        for (var ignoreAttribute : IGNORE_ATTRIBUTES) {
//            args.remove(ignoreAttribute);
//        }
//
//        args.put("invalid", violation.getInvalidValue());
//        args.put("property", violation.getPropertyPath().toString());
//
//        return args;
//    }
//
//}