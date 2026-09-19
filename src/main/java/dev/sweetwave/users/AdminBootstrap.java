package dev.sweetwave.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private final AppUserRepository users;
    private final String adminEmail;

    public AdminBootstrap(AppUserRepository users, @Value("${ADMIN_EMAIL:}") String adminEmail) {
        this.users = users;
        this.adminEmail = adminEmail;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!adminEmail.isBlank()) users.findByEmailIgnoreCase(adminEmail).ifPresent(AppUser::promoteToAdmin);
    }
}
