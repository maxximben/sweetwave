package dev.sweetwave.playlists;

import dev.sweetwave.common.BaseEntity;
import dev.sweetwave.users.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlists")
public class Playlist extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser owner;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean isPublic;

    protected Playlist() {
    }

    public Playlist(AppUser owner, String name, String description, boolean isPublic) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }

    public AppUser getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void update(String name, String description, boolean isPublic) {
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }
}
