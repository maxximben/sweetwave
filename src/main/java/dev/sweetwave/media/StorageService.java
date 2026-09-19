package dev.sweetwave.media;

import dev.sweetwave.config.S3Properties;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class StorageService {

    private final S3Presigner presigner;
    private final S3Properties properties;

    public StorageService(S3Presigner presigner, S3Properties properties) {
        this.presigner = presigner;
        this.properties = properties;
    }

    public MediaDtos.UploadUrlResponse uploadUrl(String fileName, String contentType) {
        String objectKey = "tracks/" + UUID.randomUUID() + "/" + cleanName(fileName);
        Duration duration = Duration.ofMinutes(properties.presignTtlMinutes());
        PresignedPutObjectRequest request = presigner.presignPutObject(
                PutObjectPresignRequest.builder()
                        .signatureDuration(duration)
                        .putObjectRequest(PutObjectRequest.builder()
                                .bucket(properties.bucket())
                                .key(objectKey)
                                .contentType(contentType)
                                .build())
                        .build()
        );
        return new MediaDtos.UploadUrlResponse(objectKey, request.url().toString(), Instant.now().plus(duration));
    }

    public String streamUrl(String objectKey) {
        PresignedGetObjectRequest request = presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(properties.presignTtlMinutes()))
                        .getObjectRequest(GetObjectRequest.builder()
                                .bucket(properties.bucket())
                                .key(objectKey)
                                .build())
                        .build()
        );
        return request.url().toString();
    }

    private String cleanName(String name) {
        String clean = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (clean.isBlank()) throw new IllegalArgumentException("File name is invalid");
        return clean;
    }
}
