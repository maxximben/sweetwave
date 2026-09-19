package dev.sweetwave.users;

import java.util.UUID;

public final class UserDtos {

    private UserDtos() {
    }

    public record ProfileResponse(UUID id, String email, String displayName, Role role) {
    }
}
