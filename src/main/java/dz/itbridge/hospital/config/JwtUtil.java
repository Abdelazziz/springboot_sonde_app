package dz.itbridge.hospital.config;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import dz.itbridge.hospital.entity.auth.RefreshToken;
import dz.itbridge.hospital.exceptions.TokenExpiredException;
import dz.itbridge.hospital.repository.auth.RefreshTokenRepository;
import dz.itbridge.hospital.utils.EnvConfig;
import dz.itbridge.hospital.utils.MessageUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private RefreshTokenRepository refreshTokenRepository;

    public JwtUtil(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(MessageUtils.ROLE_TXT, userDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EnvConfig.JWT_EXPIRATION_MS))
                .signWith(Keys.hmacShaKeyFor(
                        EnvConfig.JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Check if token is valid
    public boolean validateAccessToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isAccessTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private boolean isAccessTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(EnvConfig.JWT_SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public RefreshToken createRefreshToken(String username) {
        try {
            refreshTokenRepository.deleteByUsername(username);

            RefreshToken token = new RefreshToken(
                    UUID.randomUUID().toString(),
                    username,
                    Instant.now().plusMillis(EnvConfig.JWT_REFRESH_EXPIRATION_MS));

            return refreshTokenRepository.save(token);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public Optional<RefreshToken> validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenExpiredException(MessageUtils.INVALID_REFRESH_TOKEN));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenExpiredException(MessageUtils.INVALID_REFRESH_TOKEN);
        }

        return Optional.of(refreshToken);
    }

}
