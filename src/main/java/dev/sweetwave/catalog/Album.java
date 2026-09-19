package dev.sweetwave.catalog;

import dev.sweetwave.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist artist;

    private LocalDate releaseDate;

    private String coverKey;

    protected Album() {}

    public Album(String title, Artist artist, LocalDate releaseDate, String coverKey) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.coverKey = coverKey;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getCoverKey() {
        return coverKey;
    }

    public void update(String title, Artist artist, LocalDate releaseDate, String coverKey) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.coverKey = coverKey;
    }
}
