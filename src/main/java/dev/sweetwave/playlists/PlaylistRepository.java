package dev.sweetwave.playlists;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
    List<Playlist> findByOwnerIdOrderByCreatedAtDesc(UUID ownerId);
}
