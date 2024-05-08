package utm.md.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Inotificare.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public record ApplicationProperties(Minio minio) {
    public record Minio(
        @NotEmpty String uri,
        @NotEmpty String accessKey,
        @NotEmpty String secretKey,
        @NotEmpty String bucketName,
        @NotEmpty QrCodes qrCode
    ) {}

    public record QrCodes(@NotEmpty String telegram, @NotEmpty String viber) {}
}
