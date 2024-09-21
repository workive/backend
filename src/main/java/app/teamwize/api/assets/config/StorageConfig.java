package app.teamwize.api.assets.config;


import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.storage.s3.endpoint-url}")
    private String s3EndpointUrl;

    @Value("${app.storage.s3.access-key}")
    private String s3AccessKey;

    @Value("${app.storage.s3.secret-key}")
    private String s3SecretKey;

    @Bean
    public S3Client s3Client() throws URISyntaxException {
        return S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .forcePathStyle(true)
                .endpointOverride(new URI(s3EndpointUrl))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(s3AccessKey, s3SecretKey)))
                .httpClientBuilder(serviceDefaults -> ApacheHttpClient.builder().build())
                .build();
    }

}
