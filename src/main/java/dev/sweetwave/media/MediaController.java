package dev.sweetwave.media;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final StorageService storage;

    public MediaController(StorageService storage) {
        this.storage = storage;
    }

    @PostMapping("/upload-url")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    MediaDtos.UploadUrlResponse uploadUrl(@Valid @RequestBody MediaDtos.UploadRequest request) {
        return storage.uploadUrl(request.fileName(), request.contentType());
    }
}
