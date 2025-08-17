package dz.itbridge.hospital.controller.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dz.itbridge.hospital.dto.auth.AuthRequest;
import dz.itbridge.hospital.dto.auth.AuthResponse;
import dz.itbridge.hospital.dto.auth.RefreshTokenRequest;
import dz.itbridge.hospital.service.auth.AuthService;
import dz.itbridge.hospital.utils.MessageUtils;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Optional<AuthResponse> authResponse = authService.login(request);

        return ResponseEntity.ok(authResponse.get());
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {

        Optional<AuthResponse> authRefreshResponse = authService.refreshToken(request);

        return ResponseEntity.ok(authRefreshResponse
                .orElseThrow(() -> new RuntimeException(MessageUtils.INVALID_REFRESH_TOKEN)));

    }

}
