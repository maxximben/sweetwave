package dev.sweetwave.library;

import dev.sweetwave.catalog.CatalogDtos;
import java.time.Instant;

public final class LibraryDtos {

    private LibraryDtos() {
    }

    public record ListeningEventResponse(CatalogDtos.TrackResponse track, Instant listenedAt) {
    }
}
