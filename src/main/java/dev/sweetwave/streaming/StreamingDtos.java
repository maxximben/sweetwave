package dev.sweetwave.streaming;

import java.time.Instant;

public record StreamingDtos(
    String streamUrl,
    Instant expiresAt
) {}
