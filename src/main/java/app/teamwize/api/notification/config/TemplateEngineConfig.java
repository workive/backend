package app.teamwize.api.notification.config;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.FileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateEngineConfig {

    @Bean
    public PebbleEngine pebbleEngine() {
        var loader = new FileLoader();

        // Set the custom directory path, relative to the project root
        loader.setPrefix(System.getProperty("user.dir") + "/static/templates/");
        loader.setSuffix(".html");

        return new PebbleEngine.Builder()
                .cacheActive(false)
                .loader(loader)
                .build();
    }

}
