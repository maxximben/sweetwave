package dev.sweetwave.auth;

import dev.sweetwave.users.Role;
import java.util.UUID;

public record CurrentUser(
    UUID id,
    String email,
    Role role
) {}
