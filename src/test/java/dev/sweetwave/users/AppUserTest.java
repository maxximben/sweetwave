package dev.sweetwave.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppUserTest {
    @Test
    void normalizesEmailAndStartsAsUser() {
        AppUser user = new AppUser("HELLO@EXAMPLE.COM", "hash", "Hello");
        assertThat(user.getEmail()).isEqualTo("hello@example.com");
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void canPromoteUserToAdmin() {
        AppUser user = new AppUser("hello@example.com", "hash", "Hello");
        user.promoteToAdmin();
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }
}
