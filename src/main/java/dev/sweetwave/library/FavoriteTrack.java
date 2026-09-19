package dev.sweetwave.library;

import dev.sweetwave.catalog.Track;
import dev.sweetwave.common.BaseEntity;
import dev.sweetwave.users.AppUser;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favorite_tracks", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "track_id"}))
public class FavoriteTrack extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Track track;
    protected FavoriteTrack() {
    }

    public FavoriteTrack(AppUser user, Track track) {
        this.user = user;
        this.track = track;
    }

    public AppUser getUser() {
        return user;
    }

    public Track getTrack() {
        return track;
    }
}
