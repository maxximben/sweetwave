package dev.sweetwave.streaming;

import dev.sweetwave.catalog.Track;
import dev.sweetwave.catalog.TrackRepository;
import dev.sweetwave.common.NotFoundException;
import dev.sweetwave.config.S3Properties;
import dev.sweetwave.library.LibraryService;
import dev.sweetwave.media.StorageService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StreamingService {

    private final TrackRepository tracks;
    private final LibraryService library;
    private final StorageService storage;
    private final S3Properties properties;

    public StreamingService(
        TrackRepository tracks,
        LibraryService library,
        StorageService storage,
        S3Properties properties
    ) {
        this.tracks = tracks;
        this.library = library;
        this.storage = storage;
        this.properties = properties;
    }

    @Transactional
    public StreamingDtos stream(UUID userId, UUID trackId) {
        Track track = tracks.findById(trackId).orElseThrow(() -> new NotFoundException("Track", trackId));
        library.recordListen(userId, trackId);
        return new StreamingDtos(
                storage.streamUrl(track.getMediaKey()),
                Instant.now().plus(properties.presignTtlMinutes(), ChronoUnit.MINUTES)
        );
    }
}
