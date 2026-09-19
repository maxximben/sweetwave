package dev.sweetwave.catalog;

import dev.sweetwave.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String biography;

    protected Artist() {}

    public Artist(String name, String biography) {
        this.name = name; this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public void update(String name, String biography) {
        this.name = name; this.biography = biography;
    }
}
