package dev.sweetwave.library;

import dev.sweetwave.auth.CurrentUser;
import dev.sweetwave.catalog.CatalogDtos;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final LibraryService library;

    public LibraryController(LibraryService library) {
        this.library = library;
    }

    @GetMapping("/favorites")
    List<CatalogDtos.TrackResponse> favorites(@AuthenticationPrincipal CurrentUser user) {
        return library.favorites(user.id());
    }

    @PostMapping("/favorites/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void favorite(@AuthenticationPrincipal CurrentUser user, @PathVariable UUID trackId) {
        library.favorite(user.id(), trackId);
    }

    @DeleteMapping("/favorites/{trackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unfavorite(@AuthenticationPrincipal CurrentUser user, @PathVariable UUID trackId) {
        library.unfavorite(user.id(), trackId);
    }

    @GetMapping("/history")
    List<LibraryDtos.ListeningEventResponse> history(@AuthenticationPrincipal CurrentUser user) {
        return library.history(user.id());
    }
}
