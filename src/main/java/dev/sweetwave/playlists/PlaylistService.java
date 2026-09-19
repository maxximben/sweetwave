package dev.sweetwave.playlists;

import dev.sweetwave.catalog.CatalogDtos;
import dev.sweetwave.catalog.CatalogService;
import dev.sweetwave.catalog.Track;
import dev.sweetwave.catalog.TrackRepository;
import dev.sweetwave.common.ConflictException;
import dev.sweetwave.common.NotFoundException;
import dev.sweetwave.users.AppUser;
import dev.sweetwave.users.AppUserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlists;
    private final PlaylistTrackRepository playlistTracks;
    private final AppUserRepository users;
    private final TrackRepository tracks;
    private final CatalogService catalog;

    public PlaylistService(
        PlaylistRepository playlists,
        PlaylistTrackRepository playlistTracks,
        AppUserRepository users,
        TrackRepository tracks,
        CatalogService catalog
    ) {
        this.playlists = playlists;
        this.playlistTracks = playlistTracks;
        this.users = users;
        this.tracks = tracks;
        this.catalog = catalog;
    }

    @Transactional(readOnly = true)
    public List<PlaylistDtos.PlaylistResponse> mine(UUID userId) {
        return playlists.findByOwnerIdOrderByCreatedAtDesc(userId).stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public PlaylistDtos.PlaylistResponse one(UUID userId, UUID playlistId) {
        return response(owned(userId, playlistId));
    }

    @Transactional
    public PlaylistDtos.PlaylistResponse create(UUID userId, PlaylistDtos.PlaylistRequest request) {
        return response(playlists.save(new Playlist(user(userId), request.name().trim(), request.description(), request.isPublic())));
    }

    @Transactional
    public PlaylistDtos.PlaylistResponse update(UUID userId, UUID playlistId, PlaylistDtos.PlaylistRequest request) {
        Playlist playlist = owned(userId, playlistId);
        playlist.update(request.name().trim(), request.description(), request.isPublic());
        return response(playlist);
    }

    @Transactional
    public void delete(UUID userId, UUID playlistId) {
        playlists.delete(owned(userId, playlistId));
    }

    @Transactional
    public PlaylistDtos.PlaylistResponse addTrack(UUID userId, UUID playlistId, UUID trackId) {
        Playlist playlist = owned(userId, playlistId);
        if (playlistTracks.findByPlaylistIdAndTrackId(playlistId, trackId).isPresent()) {
            throw new ConflictException("Track already exists in playlist");
        }
        playlistTracks.save(new PlaylistTrack(playlist, track(trackId), (int) playlistTracks.countByPlaylistId(playlistId)));
        return response(playlist);
    }

    @Transactional
    public void removeTrack(UUID userId, UUID playlistId, UUID trackId) {
        Playlist playlist = owned(userId, playlistId);
        PlaylistTrack value = playlistTracks.findByPlaylistIdAndTrackId(playlist.getId(), trackId)
                .orElseThrow(() -> new NotFoundException("Playlist track", trackId));
        playlistTracks.delete(value);
    }

    private Playlist owned(UUID userId, UUID id) {
        Playlist playlist = playlists.findById(id)
                .orElseThrow(() -> new NotFoundException("Playlist", id));
        if (!playlist.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Playlist", id);
        }
        return playlist;
    }

    private AppUser user(UUID id) {
        return users.findById(id).orElseThrow(() -> new NotFoundException("User", id));
    }

    private Track track(UUID id) {
        return tracks.findById(id).orElseThrow(() -> new NotFoundException("Track", id));
    }

    private PlaylistDtos.PlaylistResponse response(Playlist playlist) {
        List<CatalogDtos.TrackResponse> values = playlistTracks.findByPlaylistIdOrderByPositionAsc(playlist.getId()).stream()
                .map(item -> catalog.track(item.getTrack().getId())).toList();
        return new PlaylistDtos.PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                playlist.isPublic(),
                values
        );
    }
}
