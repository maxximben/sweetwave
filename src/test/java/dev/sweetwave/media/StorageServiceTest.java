package dev.sweetwave.media;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.sweetwave.config.S3Properties;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

class StorageServiceTest {
    @Test
    void rejectsFileNameWithoutSafeCharacters() {
        StorageService service = new StorageService(S3Presigner.builder().region(software.amazon.awssdk.regions.Region.US_EAST_1).build(),
                new S3Properties("http://localhost:9000", "key", "secret", "bucket", "us-east-1", 10));
        assertThatThrownBy(() -> service.uploadUrl("   ", "audio/wav"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File name is invalid");
    }
}
