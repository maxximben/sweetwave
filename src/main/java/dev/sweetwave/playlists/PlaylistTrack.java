package dev.sweetwave.playlists;

import dev.sweetwave.catalog.Track;
import dev.sweetwave.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "playlist_tracks", uniqueConstraints = @UniqueConstraint(columnNames = {"playlist_id", "track_id"}))
public class PlaylistTrack extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Track track;

    @Column(nullable = false)
    private int position;

    protected PlaylistTrack() {
    }

    public PlaylistTrack(Playlist playlist, Track track, int position) {
        this.playlist = playlist;
        this.track = track;
        this.position = position;
    }

    public Track getTrack() {
        return track;
    }

    public int getPosition() {
        return position;
    }

    public void moveTo(int position) {
        this.position = position;
    }
}
