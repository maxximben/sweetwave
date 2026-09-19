package dev.sweetwave.catalog;

import dev.sweetwave.common.ConflictException;
import dev.sweetwave.common.NotFoundException;
import dev.sweetwave.common.PageResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogService {

    private final GenreRepository genres;
    private final ArtistRepository artists;
    private final AlbumRepository albums;
    private final TrackRepository tracks;

    public CatalogService(GenreRepository genres, ArtistRepository artists, AlbumRepository albums, TrackRepository tracks) {
        this.genres = genres;
        this.artists = artists;
        this.albums = albums;
        this.tracks = tracks;
    }

    @Transactional(readOnly = true)
    public List<CatalogDtos.GenreResponse> genres() {
        return genres.findAll(Sort.by("name")).stream().map(this::genre).toList();
    }

    @Transactional
    public CatalogDtos.GenreResponse createGenre(CatalogDtos.GenreRequest request) {
        if (genres.findByNameIgnoreCase(request.name().trim()).isPresent()) throw new ConflictException("Genre already exists");
        return genre(genres.save(new Genre(request.name().trim())));
    }

    @Transactional
    public CatalogDtos.GenreResponse updateGenre(UUID id, CatalogDtos.GenreRequest request) {
        Genre value = genreEntity(id); value.rename(request.name().trim()); return genre(value);
    }

    @Transactional
    public void deleteGenre(UUID id) {
        genres.delete(genreEntity(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<CatalogDtos.ArtistResponse> artists(int page, int size) {
        return PageResponse.from(artists.findAll(PageRequest.of(page, size, Sort.by("name"))).map(this::artist));
    }

    @Transactional
    public CatalogDtos.ArtistResponse createArtist(CatalogDtos.ArtistRequest request) {
        return artist(artists.save(new Artist(request.name().trim(), request.biography())));
    }

    @Transactional
    public CatalogDtos.ArtistResponse updateArtist(UUID id, CatalogDtos.ArtistRequest request) {
        Artist value = artistEntity(id); value.update(request.name().trim(), request.biography()); return artist(value);
    }

    @Transactional
    public void deleteArtist(UUID id) {
        artists.delete(artistEntity(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<CatalogDtos.AlbumResponse> albums(int page, int size) {
        return PageResponse.from(albums.findAll(PageRequest.of(page, size, Sort.by("title"))).map(this::album));
    }

    @Transactional
    public CatalogDtos.AlbumResponse createAlbum(CatalogDtos.AlbumRequest request) {
        return album(albums.save(new Album(request.title().trim(), artistEntity(request.artistId()), request.releaseDate(), request.coverKey())));
    }

    @Transactional
    public CatalogDtos.AlbumResponse updateAlbum(UUID id, CatalogDtos.AlbumRequest request) {
        Album value = albumEntity(id);
        value.update(request.title().trim(), artistEntity(request.artistId()), request.releaseDate(), request.coverKey());
        return album(value);
    }

    @Transactional
    public void deleteAlbum(UUID id) {
        albums.delete(albumEntity(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<CatalogDtos.TrackResponse> tracks(String query, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("title"));
        var result = query == null || query.isBlank() ? tracks.findAll(pageable) : tracks.search(query.trim(), pageable);
        return PageResponse.from(result.map(this::track));
    }

    @Transactional(readOnly = true)
    public CatalogDtos.TrackResponse track(UUID id) {
        return track(trackEntity(id));
    }

    @Transactional
    public CatalogDtos.TrackResponse createTrack(CatalogDtos.TrackRequest request) {
        return track(tracks.save(new Track(request.title().trim(), albumEntity(request.albumId()), genreOrNull(request.genreId()),
                request.durationSeconds(), request.mediaKey(), request.mimeType())));
    }

    @Transactional
    public CatalogDtos.TrackResponse updateTrack(UUID id, CatalogDtos.TrackRequest request) {
        Track value = trackEntity(id);
        value.update(request.title().trim(), albumEntity(request.albumId()), genreOrNull(request.genreId()), request.durationSeconds(), request.mediaKey(), request.mimeType());
        return track(value);
    }

    @Transactional
    public void deleteTrack(UUID id) {
        tracks.delete(trackEntity(id));
    }

    Track trackEntity(UUID id) {
        return tracks.findById(id).orElseThrow(() -> new NotFoundException("Track", id));
    }

    private Genre genreEntity(UUID id) {
        return genres.findById(id).orElseThrow(() -> new NotFoundException("Genre", id));
    }

    private Genre genreOrNull(UUID id) {
        return id == null ? null : genreEntity(id);
    }

    private Artist artistEntity(UUID id) {
        return artists.findById(id).orElseThrow(() -> new NotFoundException("Artist", id));
    }

    private Album albumEntity(UUID id) {
        return albums.findById(id).orElseThrow(() -> new NotFoundException("Album", id));
    }

    private CatalogDtos.GenreResponse genre(Genre value) {
        return new CatalogDtos.GenreResponse(value.getId(), value.getName());
    }

    private CatalogDtos.ArtistResponse artist(Artist value) {
        return new CatalogDtos.ArtistResponse(value.getId(), value.getName(), value.getBiography());
    }

    private CatalogDtos.AlbumResponse album(Album value) {
        return new CatalogDtos.AlbumResponse(value.getId(), value.getTitle(), artist(value.getArtist()), value.getReleaseDate(), value.getCoverKey());
    }
    private CatalogDtos.TrackResponse track(Track value) {
        return new CatalogDtos.TrackResponse(value.getId(), value.getTitle(), album(value.getAlbum()),
                value.getGenre() == null ? null : genre(value.getGenre()), value.getDurationSeconds(), value.getMimeType());
    }
}
