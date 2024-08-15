package app.teamwize.api.holiday.config;

import app.teamwize.api.holiday.provider.OpenHolidayProvider;
import app.teamwize.api.holiday.provider.PublicHolidayProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class HolidayConfig {

    @Bean
    PublicHolidayProvider openHolidayProvider() {
        var restClient = RestClient.builder().baseUrl("https://openholidaysapi.org")
                .build();
        return new OpenHolidayProvider(restClient);
    }

}
