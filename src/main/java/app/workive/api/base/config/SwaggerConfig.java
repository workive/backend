package app.workive.api.base.config;

import app.workive.api.base.validator.NotTempEmail;
import app.workive.api.base.validator.PhoneNumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final Map<String, List<String>> additionalResponses = new HashMap<>();
    private static final Map<String, Schema> additionalValidations = new HashMap<>();

    private final MessageSource messageSource;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private MessageSourceAccessor accessor;


    @PostConstruct
    public void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
        var handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();

        for (var item : handlerMethods.entrySet()) {
            var mapping = item.getKey();
            var requestMethod = mapping.getMethodsCondition().getMethods().stream().findFirst();
            var url = mapping.getPathPatternsCondition().getPatterns().stream().findFirst();
            if (requestMethod.isEmpty() || url.isEmpty()) {
                continue;
            }
            var key = requestMethod.get().name() + "#" + url.get();
            var method = item.getValue();

            // fetch custom exceptions
            var exceptions = method.getMethod().getExceptionTypes();
            for (var eachException : exceptions) {
                var exceptionMappingAnnotation = eachException.getDeclaredAnnotation(ResponseStatus.class);
                if (exceptionMappingAnnotation != null) {
                    var errorName = eachException.getSimpleName().replaceAll("Exception", "Error");
                    var errors = additionalResponses.getOrDefault(key, new ArrayList<>());
                    //var errorCode = className.getSimpleName().replaceAll("Exception","Error");
                    errors.add(errorName);
                    additionalResponses.put(key, errors);
                }
            }

            /*
                @NotNull(message = "site.create_user.role.null")
                @NotBlank(message = "driver.email.blank")
                @Email(message = "driver.email.email_format")
                @NotTempEmail(message = "driver.email.email_disposable")
                @Size(min = 5, max = 255, message = "driver.email.size")
                @PhoneNumber
                @DecimalMin(value = "0.1", message = "driver.driving_speed.min")
                @DecimalMax(value = "1.9", message = "driver.driving_speed.max")
             */
            // fetch validators
            var objectMapper = new ObjectMapper();
            var parameters = method.getMethod().getParameters();
            if (parameters.length > 0) {
                ArraySchema errors = null;
                var errorsArray = objectMapper.createArrayNode();
                for (var parameter : parameters) {
                    if (parameter.isAnnotationPresent(Valid.class)) {
                        var fields = parameter.getType().getDeclaredFields();
                        for (var field : fields) {
                            var annotations = field.getDeclaredAnnotations();
                            String code = "";
                            Integer min = null;
                            Integer max = null;
                            String value = null;
                            for (var annotation : annotations) {
                                if (annotation instanceof NotNull notNull) {
                                    code = notNull.message();
                                } else if (annotation instanceof NotBlank notBlank) {
                                    code = notBlank.message();
                                } else if (annotation instanceof NotEmpty notEmpty) {
                                    code = notEmpty.message();
                                } else if (annotation instanceof Email email) {
                                    code = email.message();
                                } else if (annotation instanceof NotTempEmail notTempEmail) {
                                    code = notTempEmail.message();
                                } else if (annotation instanceof PhoneNumber phoneNumber) {
                                    code = phoneNumber.message();
                                } else if (annotation instanceof DecimalMin decimalMin) {
                                    code = decimalMin.message();
                                    value = decimalMin.value();
                                } else if (annotation instanceof DecimalMax decimalMax) {
                                    code = decimalMax.message();
                                    value = decimalMax.value();
                                } else if (annotation instanceof Size size) {
                                    code = size.message();
                                    min = size.min();
                                    max = size.max();
                                }

                                if (!"".equals(code)) {
                                    var argumentsNode = objectMapper.createObjectNode();
                                    argumentsNode.put("property", field.getName());
                                    argumentsNode.put("invalid", "inputString");
                                    if (min != null) {
                                        argumentsNode.put("min", min);
                                    }
                                    if (max != null) {
                                        argumentsNode.put("max", max);
                                    }
                                    if (value != null) {
                                        argumentsNode.put("value", value);
                                    }
                                    var node = objectMapper.createObjectNode();
                                    node.put("code", code);
                                    node.put("message", accessor.getMessage(code, code));
                                    node.set("arguments", argumentsNode);
                                    errorsArray.add(node);
                                    if (errors == null) {
                                        errors = new ArraySchema();
                                        var errorsSchema = new Schema()
                                                .addProperties("code", new StringSchema())
                                                .addProperties("message", new StringSchema())
                                                .addProperties("arguments", new Schema()
                                                        .addProperties("property", new StringSchema())
                                                        .addProperties("invalid", new StringSchema()));
                                        errors.items(errorsSchema);
                                    }
                                }
                            }
                        }
                    }
                }
                if (errors != null) {
                    errors.example(errorsArray.toPrettyString());
                    additionalValidations.put(key, errors);
                }
            }
        }
    }

    @Bean
    public OpenAPI securitySchema() throws ClassNotFoundException {
        return new OpenAPI()
                .servers(List.of(new Server().url("/")))
                .security(List.of(new SecurityRequirement().addList("apiKeyAuth").addList("authorizationKeyAuth")))
                .components(buildComponentsWithAdditionalCustomExceptions())
                .info(new Info()
                        .title("Workive Service API Documentation")
                        .version("v1"));
    }

    @Bean
    public OpenApiCustomizer consumerTypeHeaderOpenAPICCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, operations) ->
                operations.readOperationsMap().forEach((httpVerb, operation) -> {
                    var errorCodes = additionalResponses.get(httpVerb.name() + "#" + path);
                    if (errorCodes != null) {
                        errorCodes.forEach(errorCode ->
                                operation.getResponses().addApiResponse(errorCode, new ApiResponse().content(
                                        new Content().addMediaType("application/json", new MediaType().schema(
                                                new Schema().$ref("#/components/schemas/" + errorCode))
                                        )
                                )));
                    }
                    var validations = additionalValidations.get(httpVerb.name() + "#" + path);
                    if (validations != null) {
                        operation.getResponses().addApiResponse("400", new ApiResponse().content(
                                new Content().addMediaType("application/json", new MediaType().schema(
                                                new Schema()
                                                        .addProperty("fingerprint", new StringSchema()
                                                                .example("9b2af5fe-a242-408a-8175-1165ca973177")
                                                        )
                                                        .addProperty("errors", validations)
                                        )
                                )));
                    }

                })
        );
    }

    private Components buildComponentsWithAdditionalCustomExceptions() throws ClassNotFoundException {
        var components = new Components();
        var scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(ResponseStatus.class));

        for (var bd : scanner.findCandidateComponents("com.routetitan.account.api")) {
            var className = Class.forName(bd.getBeanClassName());
            var annotation = className.getDeclaredAnnotation(ResponseStatus.class);
            var errorCode = className.getSimpleName().replaceAll("Exception", "Error");
            var statusCode = annotation.code();
            components.addSchemas(errorCode, buildErrorsSchema(errorCode, statusCode, bd));
        }

        components.addSecuritySchemes("apiKeyAuth", new SecurityScheme().type(SecurityScheme.Type.APIKEY).name("API-Key").in(SecurityScheme.In.HEADER));
        components.addSecuritySchemes("authorizationKeyAuth", new SecurityScheme().type(SecurityScheme.Type.APIKEY).name("Authorization").bearerFormat("jwt").in(SecurityScheme.In.HEADER));

        return components;
    }

    private Schema buildErrorsSchema(String errorCode, HttpStatus statusCode, BeanDefinition bean) throws ClassNotFoundException {
        var errorsSchema = new Schema()
                .addProperty("code", new StringSchema()
                        .example(errorCode))
                .addProperty("message", new StringSchema()
                        .example(accessor.getMessage(errorCode, "")));

        var arguments = new Schema();
        var declaredFields = Class.forName(bean.getBeanClassName()).getDeclaredFields();
        if (declaredFields.length > 0) {
            for (var field : declaredFields) {
                if (field.getType() == long.class || field.getType() == Long.class) {
                    arguments.addProperty(field.getName(), new IntegerSchema().example(1));
                } else {
                    arguments.addProperty(field.getName(), new StringSchema().example("String"));
                }
            }
            errorsSchema.addProperty("arguments", arguments);
        }

        return new ObjectSchema()
                .addProperty("fingerprint", new StringSchema().example("bfb36768-1d7e-40fa-bf6b-369d96294aec"))
                .addProperty("httpStatusCode", new IntegerSchema().example(statusCode.value()))
                .addProperty("errors", new ArraySchema().items(errorsSchema));
    }

}
