package dev.sweetwave.catalog;

import dev.sweetwave.common.PageResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CatalogController {

    private final CatalogService catalog;

    public CatalogController(CatalogService catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/genres")
    List<CatalogDtos.GenreResponse> genres() {
        return catalog.genres();
    }

    @PostMapping("/genres")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    CatalogDtos.GenreResponse createGenre(@Valid @RequestBody CatalogDtos.GenreRequest request) {
        return catalog.createGenre(request);
    }

    @PutMapping("/genres/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CatalogDtos.GenreResponse updateGenre(@PathVariable UUID id, @Valid @RequestBody CatalogDtos.GenreRequest request) {
        return catalog.updateGenre(id, request);
    }

    @DeleteMapping("/genres/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGenre(@PathVariable UUID id) {
        catalog.deleteGenre(id);
    }

    @GetMapping("/artists")
    PageResponse<CatalogDtos.ArtistResponse> artists(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return catalog.artists(page, size);
    }

    @PostMapping("/artists")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    CatalogDtos.ArtistResponse createArtist(@Valid @RequestBody CatalogDtos.ArtistRequest request) {
        return catalog.createArtist(request);
    }

    @PutMapping("/artists/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CatalogDtos.ArtistResponse updateArtist(@PathVariable UUID id, @Valid @RequestBody CatalogDtos.ArtistRequest request) {
        return catalog.updateArtist(id, request);
    }

    @DeleteMapping("/artists/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteArtist(@PathVariable UUID id) {
        catalog.deleteArtist(id);
    }

    @GetMapping("/albums")
    PageResponse<CatalogDtos.AlbumResponse> albums(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return catalog.albums(page, size);
    }

    @PostMapping("/albums")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    CatalogDtos.AlbumResponse createAlbum(@Valid @RequestBody CatalogDtos.AlbumRequest request) {
        return catalog.createAlbum(request);
    }

    @PutMapping("/albums/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CatalogDtos.AlbumResponse updateAlbum(@PathVariable UUID id, @Valid @RequestBody CatalogDtos.AlbumRequest request) {
        return catalog.updateAlbum(id, request);
    }

    @DeleteMapping("/albums/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAlbum(@PathVariable UUID id) {
        catalog.deleteAlbum(id);
    }

    @GetMapping("/tracks")
    PageResponse<CatalogDtos.TrackResponse> tracks(
        @RequestParam(required = false) String q,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return catalog.tracks(q, page, size);
    }

    @GetMapping("/tracks/{id}")
    CatalogDtos.TrackResponse track(@PathVariable UUID id) {
        return catalog.track(id);
    }

    @PostMapping("/tracks")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    CatalogDtos.TrackResponse createTrack(@Valid @RequestBody CatalogDtos.TrackRequest request) {
        return catalog.createTrack(request);
    }

    @PutMapping("/tracks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CatalogDtos.TrackResponse updateTrack(@PathVariable UUID id, @Valid @RequestBody CatalogDtos.TrackRequest request) {
        return catalog.updateTrack(id, request);
    }

    @DeleteMapping("/tracks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrack(@PathVariable UUID id) {
        catalog.deleteTrack(id);
    }
}
