package dev.sweetwave.catalog;

import dev.sweetwave.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    protected Genre() { }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void rename(String name) {
        this.name = name;
    }
}
