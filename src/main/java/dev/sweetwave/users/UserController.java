package dev.sweetwave.users;

import dev.sweetwave.auth.CurrentUser;
import dev.sweetwave.common.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final AppUserRepository users;

    public UserController(AppUserRepository users) {
        this.users = users;
    }

    @GetMapping("/me")
    UserDtos.ProfileResponse me(@AuthenticationPrincipal CurrentUser currentUser) {
        AppUser user = users.findById(currentUser.id())
                .orElseThrow(() -> new NotFoundException("User", currentUser.id()));
        return new UserDtos.ProfileResponse(user.getId(), user.getEmail(), user.getDisplayName(), user.getRole());
    }
}
