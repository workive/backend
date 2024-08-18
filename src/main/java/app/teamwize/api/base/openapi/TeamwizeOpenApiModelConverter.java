package app.teamwize.api.base.openapi;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import jakarta.annotation.Nullable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.stream.Stream;

@Slf4j
@Component
public class TeamwizeOpenApiModelConverter implements ModelConverter {

    @Override
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        Schema schema = null;
        if (type.getType() instanceof JavaType) {
            var javaType = (JavaType) type.getType();
            if (javaType != null) {
                Class<?> cls = javaType.getRawClass();
                if (JsonNullable.class.equals(cls)) {
                    schema = buildJsonNullableSchema(type, context);
                }
                if (LocalTime.class.equals(cls)) {
                    schema = buildLocalTimeSchema(type, context);
                }
                if (Duration.class.equals(cls)) {
                    schema = buildDurationSchema(type, context);
                }
            }
        }

        if (schema == null) {
            schema = next(type, context, chain);
            schema.setNullable(isNullable(type));
        }
        return schema;
    }

    public boolean isNullable(AnnotatedType type) {
        return type.getCtxAnnotations() != null && Stream.of(type.getCtxAnnotations()).anyMatch(s -> s instanceof Nullable);
    }

    private Schema buildJsonNullableSchema(AnnotatedType type, ModelConverterContext context) {
        var javaType = (JavaType) type.getType();
        var innerType = javaType.getBindings().getBoundType(0);
        if (innerType == null) return new StringSchema();
        return context.resolve(new AnnotatedType(innerType)
                .jsonViewAnnotation(type.getJsonViewAnnotation())
                .ctxAnnotations((type.getCtxAnnotations()))
                .resolveAsRef(true));
    }

    private Schema buildLocalTimeSchema(AnnotatedType type, ModelConverterContext context) {
        return new StringSchema()
                .example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .pattern("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
    }

    private Schema buildDurationSchema(AnnotatedType type, ModelConverterContext context) {
        return new IntegerSchema().example(120L);
    }

    private Schema next(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        return (chain.hasNext()) ? chain.next().resolve(type, context, chain) : null;
    }
}
