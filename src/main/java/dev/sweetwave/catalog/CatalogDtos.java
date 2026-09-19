package dev.sweetwave.catalog;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public final class CatalogDtos {

    private CatalogDtos() {}

    public record GenreRequest(
        @NotBlank @Size(max = 80) String name
    ) {}

    public record GenreResponse(
        UUID id, String name
    ) {}

    public record ArtistRequest(
        @NotBlank @Size(max = 160) String name,
        @Size(max = 2000) String biography
    ) {}

    public record ArtistResponse(
        UUID id,
        String name,
        String biography
    ) {}

    public record AlbumRequest(
        @NotBlank @Size(max = 180) String title,
        @NotNull UUID artistId,
        LocalDate releaseDate,
        @Size(max = 512) String coverKey
    ) {}

    public record AlbumResponse(
        UUID id,
        String title,
        ArtistResponse artist,
        LocalDate releaseDate,
        String coverKey
    ) {}

    public record TrackRequest(
        @NotBlank @Size(max = 180) String title,
        @NotNull UUID albumId,
        UUID genreId,
        @Min(1) @Max(36000) int durationSeconds,
        @NotBlank @Size(max = 512) String mediaKey,
        @NotBlank @Size(max = 100) String mimeType
    ) {}

    public record TrackResponse(
        UUID id,
        String title,
        AlbumResponse album,
        GenreResponse genre,
        int durationSeconds,
        String mimeType
    ) {}
}
