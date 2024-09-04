package app.teamwize.api.assets.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class StorageConfig {

    @Bean
    public S3Client s3Client() throws URISyntaxException {
        return S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .forcePathStyle(true)
                .endpointOverride(new URI("https://kmpbtlgcehzzrgbsintq.supabase.co/storage/v1/s3"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("132f72964cf6df2c486e297ad84cbb23", "08af29561274f905971ec862c98557776992ab3a277ec69af222c447c50ff17b")))
                .httpClientBuilder(serviceDefaults -> ApacheHttpClient.builder().build())
                .build();
    }

}
