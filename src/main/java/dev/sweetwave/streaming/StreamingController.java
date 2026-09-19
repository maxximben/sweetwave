package dev.sweetwave.streaming;

import dev.sweetwave.auth.CurrentUser;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tracks")
public class StreamingController {

    private final StreamingService streaming;

    public StreamingController(StreamingService streaming) {
        this.streaming = streaming;
    }

    @GetMapping("/{id}/stream")
    StreamingDtos stream(@AuthenticationPrincipal CurrentUser user, @PathVariable UUID id) {
        return streaming.stream(user.id(), id);
    }
}
