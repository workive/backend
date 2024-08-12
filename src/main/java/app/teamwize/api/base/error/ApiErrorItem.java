package app.teamwize.api.base.error;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ApiErrorItem {
    String code;
    String message;
    Map<String, Object> arguments;
}
