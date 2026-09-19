package dev.sweetwave.library;

import dev.sweetwave.catalog.Track;
import dev.sweetwave.common.BaseEntity;
import dev.sweetwave.users.AppUser;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "listening_events")
public class ListeningEvent extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Track track;
    private Instant listenedAt;
    protected ListeningEvent() {
    }

    public ListeningEvent(AppUser user, Track track) {
        this.user = user;
        this.track = track;
        this.listenedAt = Instant.now();
    }

    public Track getTrack() {
        return track;
    }

    public Instant getListenedAt() {
        return listenedAt;
    }
}
