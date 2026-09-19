package dev.sweetwave.playlists;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, UUID> {
    List<PlaylistTrack> findByPlaylistIdOrderByPositionAsc(UUID playlistId);
    Optional<PlaylistTrack> findByPlaylistIdAndTrackId(UUID playlistId, UUID trackId);
    long countByPlaylistId(UUID playlistId);
}
