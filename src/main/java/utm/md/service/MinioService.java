package utm.md.service;

import io.minio.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.springframework.stereotype.Service;
import utm.md.config.ApplicationProperties;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String rootBucket;

    public MinioService(MinioClient minioClient, ApplicationProperties properties) {
        this.minioClient = minioClient;
        this.rootBucket = properties.minio().bucketName();
    }

    public String uploadDocumentToStorage(String fileName, byte[] data) {
        try {
            minioClient.putObject(
                PutObjectArgs.builder().bucket(rootBucket).object(fileName).stream(new ByteArrayInputStream(data), data.length, -1).build()
            );
            return rootBucket + File.separator + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Put object minio error : " + e.getMessage());
        }
    }

    public void createBucket(String bucketName) throws Exception {
        BucketExistsArgs bucketExists = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucketExists)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    public void removeObject(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(rootBucket).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException("Remove object from minio error : " + e.getMessage());
        }
    }

    public void copyObject(String objectName, String destPath) {
        try {
            CopyObjectArgs copyObjectArgs = CopyObjectArgs
                .builder()
                .bucket(rootBucket)
                .object(destPath)
                .source(CopySource.builder().bucket(rootBucket).object(objectName).build())
                .build();
            ObjectWriteResponse response = minioClient.copyObject(copyObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("Copy object from minio error : " + e.getMessage());
        }
    }

    public byte[] getObjectByPath(String object) {
        try {
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder().bucket(rootBucket).object(object).build());
            return response.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Get object from minio error : " + e.getMessage());
        }
    }
}
