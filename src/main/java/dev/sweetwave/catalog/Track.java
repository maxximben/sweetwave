package dev.sweetwave.catalog;

import dev.sweetwave.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracks")
public class Track extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @Column(nullable = false)
    private int durationSeconds;

    @Column(nullable = false)
    private String mediaKey;

    @Column(nullable = false)
    private String mimeType;

    protected Track() {}

    public Track(String title, Album album, Genre genre, int durationSeconds, String mediaKey, String mimeType) {
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
        this.mediaKey = mediaKey;
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public Album getAlbum() {
        return album;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void update(String title, Album album, Genre genre, int durationSeconds, String mediaKey, String mimeType) {
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
        this.mediaKey = mediaKey;
        this.mimeType = mimeType;
    }
}
