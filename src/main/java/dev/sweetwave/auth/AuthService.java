package dev.sweetwave.auth;

import dev.sweetwave.common.ConflictException;
import dev.sweetwave.users.AppUser;
import dev.sweetwave.users.AppUserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final RefreshTokenRepository refreshTokens;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final dev.sweetwave.config.JwtProperties properties;

    public AuthService(
        AppUserRepository users,
        RefreshTokenRepository refreshTokens,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        dev.sweetwave.config.JwtProperties properties
    ) {
        this.users = users;
        this.refreshTokens = refreshTokens;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.properties = properties;
    }

    @Transactional
    public AuthDtos.TokenResponse register(AuthDtos.RegisterRequest request) {
        if (users.findByEmailIgnoreCase(request.email()).isPresent()) throw new ConflictException("Email is already registered");
        AppUser user = users.save(new AppUser(request.email(), passwordEncoder.encode(request.password()), request.displayName()));
        return issue(user);
    }

    @Transactional
    public AuthDtos.TokenResponse login(AuthDtos.LoginRequest request) {
        AppUser user = users.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return issue(user);
    }

    @Transactional
    public AuthDtos.TokenResponse refresh(String plainToken) {
        RefreshToken token = refreshTokens.findByTokenHash(hash(plainToken))
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        if (token.isRevoked() || token.getExpiresAt().isBefore(Instant.now())) throw new IllegalArgumentException("Invalid refresh token");
        token.revoke();
        return issue(token.getUser());
    }

    @Transactional
    public void logout(String plainToken) {
        refreshTokens.findByTokenHash(hash(plainToken)).ifPresent(RefreshToken::revoke);
    }

    private AuthDtos.TokenResponse issue(AppUser user) {
        String plainRefreshToken = UUID.randomUUID().toString() + UUID.randomUUID();
        refreshTokens.save(new RefreshToken(user, hash(plainRefreshToken), Instant.now().plus(properties.refreshTtlDays(), ChronoUnit.DAYS)));
        return new AuthDtos.TokenResponse(jwtService.createAccessToken(user), plainRefreshToken, "Bearer", properties.accessTtlMinutes() * 60);
    }

    private String hash(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }
}
