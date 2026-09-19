package dev.sweetwave.catalog;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, UUID> {
    @Query("""
            select t from Track t join t.album a join a.artist ar
            where lower(t.title) like lower(concat('%', :query, '%'))
               or lower(a.title) like lower(concat('%', :query, '%'))
               or lower(ar.name) like lower(concat('%', :query, '%'))
            """)
    Page<Track> search(@Param("query") String query, Pageable pageable);
}
