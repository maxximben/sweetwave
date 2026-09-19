package dev.sweetwave.playlists;

import dev.sweetwave.catalog.CatalogDtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public final class PlaylistDtos {

    private PlaylistDtos() {
    }

    public record PlaylistRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 1000) String description,
        boolean isPublic
    ) {}

    public record PlaylistResponse(
        UUID id,
        String name,
        String description,
        boolean isPublic,
        List<CatalogDtos.TrackResponse> tracks
    ) {}
}
