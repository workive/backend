package app.teamwize.api.base.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

public class HibernateObjectMapper implements ObjectMapperSupplier {

    @Override
    public ObjectMapper get() {
        var timeModule = new JavaTimeModule();

        return Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .failOnUnknownProperties(false)
                .failOnEmptyBeans(false)
                .modules(timeModule)
                .build();
    }
}
