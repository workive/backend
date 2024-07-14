package app.workive.api.base.jpa;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class DayOfWeekConverter implements AttributeConverter<List<DayOfWeek>, String> {

    @Override
    public String convertToDatabaseColumn(List<DayOfWeek> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(DayOfWeek::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<DayOfWeek> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(","))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }
}
