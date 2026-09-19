package dev.sweetwave.library;

import dev.sweetwave.catalog.Track;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteTrackRepository extends JpaRepository<FavoriteTrack, UUID> {
    boolean existsByUserIdAndTrackId(UUID userId, UUID trackId);
    void deleteByUserIdAndTrackId(UUID userId, UUID trackId);
    List<FavoriteTrack> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
