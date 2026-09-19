package dev.sweetwave.library;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeningEventRepository extends JpaRepository<ListeningEvent, UUID> {
    List<ListeningEvent> findTop20ByUserIdOrderByListenedAtDesc(UUID userId);
}
