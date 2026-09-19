package dev.sweetwave.playlists;

import dev.sweetwave.auth.CurrentUser;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistService playlists;

    public PlaylistController(PlaylistService playlists) {
        this.playlists = playlists;
    }

    @GetMapping
    List<PlaylistDtos.PlaylistResponse> mine(@AuthenticationPrincipal CurrentUser user) {
        return playlists.mine(user.id());
    }

    @GetMapping("/{id}")
    PlaylistDtos.PlaylistResponse one(@AuthenticationPrincipal CurrentUser user, @PathVariable UUID id) {
        return playlists.one(user.id(), id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PlaylistDtos.PlaylistResponse create(
        @AuthenticationPrincipal CurrentUser user,
        @Valid @RequestBody PlaylistDtos.PlaylistRequest request
    ) {
        return playlists.create(user.id(), request);
    }

    @PutMapping("/{id}")
    PlaylistDtos.PlaylistResponse update(
        @AuthenticationPrincipal CurrentUser user,
        @PathVariable UUID id,
        @Valid @RequestBody PlaylistDtos.PlaylistRequest request
    ) {
        return playlists.update(user.id(), id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@AuthenticationPrincipal CurrentUser user, @PathVariable UUID id) {
        playlists.delete(user.id(), id);
    }

    @PostMapping("/{id}/tracks/{trackId}")
    PlaylistDtos.PlaylistResponse add(
        @AuthenticationPrincipal CurrentUser user,
        @PathVariable UUID id,
        @PathVariable UUID trackId
    ) {
        return playlists.addTrack(user.id(), id, trackId);
    }

    @DeleteMapping("/{id}/tracks/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void remove(
        @AuthenticationPrincipal CurrentUser user,
        @PathVariable UUID id,
        @PathVariable UUID trackId
    ) {
        playlists.removeTrack(user.id(), id, trackId);
    }
}
