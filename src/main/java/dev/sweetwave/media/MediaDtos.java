package dev.sweetwave.media;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public final class MediaDtos {

    private MediaDtos() {
    }

    public record UploadRequest(
        @NotBlank @Size(max = 180) String fileName,
        @NotBlank @Size(max = 100) String contentType
    ) {}

    public record UploadUrlResponse(
        String objectKey,
        String uploadUrl,
        Instant expiresAt
    ) {}
}
