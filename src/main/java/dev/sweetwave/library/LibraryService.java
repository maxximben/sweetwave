package dev.sweetwave.library;

import dev.sweetwave.catalog.CatalogDtos;
import dev.sweetwave.catalog.CatalogService;
import dev.sweetwave.catalog.Track;
import dev.sweetwave.catalog.TrackRepository;
import dev.sweetwave.common.NotFoundException;
import dev.sweetwave.users.AppUser;
import dev.sweetwave.users.AppUserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryService {

    private final FavoriteTrackRepository favorites;
    private final ListeningEventRepository events;
    private final AppUserRepository users;
    private final TrackRepository tracks;
    private final CatalogService catalog;

    public LibraryService(
        FavoriteTrackRepository favorites,
        ListeningEventRepository events,
        AppUserRepository users,
        TrackRepository tracks,
        CatalogService catalog
    ) {
        this.favorites = favorites;
        this.events = events;
        this.users = users;
        this.tracks = tracks;
        this.catalog = catalog;
    }

    @Transactional
    public void favorite(UUID userId, UUID trackId) {
        if (!favorites.existsByUserIdAndTrackId(userId, trackId)) {
            favorites.save(new FavoriteTrack(user(userId), track(trackId)));
        }
    }

    @Transactional
    public void unfavorite(UUID userId, UUID trackId) {
        favorites.deleteByUserIdAndTrackId(userId, trackId);
    }

    @Transactional(readOnly = true)
    public List<CatalogDtos.TrackResponse> favorites(UUID userId) {
        return favorites.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(value -> catalog.track(value.getTrack().getId()))
                .toList();
    }

    @Transactional
    public void recordListen(UUID userId, UUID trackId) {
        events.save(new ListeningEvent(user(userId), track(trackId)));
    }

    @Transactional(readOnly = true)
    public List<LibraryDtos.ListeningEventResponse> history(UUID userId) {
        return events.findTop20ByUserIdOrderByListenedAtDesc(userId).stream()
                .map(event -> new LibraryDtos.ListeningEventResponse(
                        catalog.track(event.getTrack().getId()),
                        event.getListenedAt()
                ))
                .toList();
    }

    private AppUser user(UUID id) {
        return users.findById(id).orElseThrow(() -> new NotFoundException("User", id));
    }

    private Track track(UUID id) {
        return tracks.findById(id).orElseThrow(() -> new NotFoundException("Track", id));
    }
}
