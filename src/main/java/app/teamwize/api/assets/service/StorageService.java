package app.teamwize.api.assets.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    private final S3Client amazonS3Client;
    private final S3Presigner s3Presigner;

    public PutObjectResponse putObject(String bucket, String fileName, String fileContent, ObjectCannedACL access) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .acl(access)
                .build();
        return amazonS3Client.putObject(objectRequest, RequestBody.fromString(fileContent));
    }

    public InputStream getObjectAsBytes(String bucket, String fileName) {
        var request = GetObjectRequest.builder().bucket(bucket)
                .key(fileName).build();
        return amazonS3Client.getObjectAsBytes(request).asInputStream();
    }

    public PutObjectResponse putObject(String bucket, String fileName, File file, ObjectCannedACL access) throws IOException {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .acl(access)
                .build();
        return amazonS3Client.putObject(objectRequest, RequestBody.fromFile(file));
    }

    public void setObjectAcl(String bucket, String fileName, ObjectCannedACL access) {
        var request = PutObjectAclRequest.builder().bucket(bucket).key(fileName).acl(access).build();
        amazonS3Client.putObjectAcl(request);
    }
}
