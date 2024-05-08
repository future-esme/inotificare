package utm.md.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class MinioConfiguration {

    public static final String MINIO_CLIENT = "minioClient";

    private static final Logger log = LoggerFactory.getLogger(MinioConfiguration.class);

    private final ApplicationProperties properties;

    public MinioConfiguration(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MinioClient minioClient() {
        var minio = properties.minio();
        return MinioClient.builder().endpoint(minio.uri()).credentials(minio.accessKey(), minio.secretKey()).build();
    }

    @EventListener
    public void eventListener(ApplicationReadyEvent event) throws Exception {
        var context = event.getApplicationContext();
        MinioClient minioClient = (MinioClient) context.getBean(MINIO_CLIENT);
        String bucketName = properties.minio().bucketName();
        var bucketExists = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucketExists)) {
            log.info("Create default bucket '{}' in minio.", bucketName);
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }
}
