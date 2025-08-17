package dz.itbridge.hospital.service.auth;

import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import dz.itbridge.hospital.config.JwtUtil;
import dz.itbridge.hospital.dto.auth.AuthRequest;
import dz.itbridge.hospital.dto.auth.AuthResponse;
import dz.itbridge.hospital.dto.auth.RefreshTokenRequest;
import dz.itbridge.hospital.repository.auth.RefreshTokenRepository;
import dz.itbridge.hospital.repository.doctor.DoctorRepository;
import dz.itbridge.hospital.service.doctor.DoctorUserDetailsService;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final DoctorUserDetailsService doctorUserDetailsService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            DoctorRepository doctorRepository, RefreshTokenRepository refreshTokenRepository,
            DoctorUserDetailsService doctorUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.doctorUserDetailsService = doctorUserDetailsService;

    }

    @Transactional
    public Optional<AuthResponse> login(AuthRequest request) {
        var auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = (User) auth.getPrincipal();
        String token = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(request.username()).getToken();

        return Optional.of(new AuthResponse(token, refreshToken));
    }

    public Optional<AuthResponse> refreshToken(RefreshTokenRequest request) {

        return jwtUtil.validateRefreshToken(request
                .refreshToken())
                .map(rt -> {
                    String accessToken = jwtUtil
                            .generateAccessToken(doctorUserDetailsService.loadUserByUsername(rt.getUsername()));
                    return new AuthResponse(accessToken, rt.getToken());
                });
    }

}
