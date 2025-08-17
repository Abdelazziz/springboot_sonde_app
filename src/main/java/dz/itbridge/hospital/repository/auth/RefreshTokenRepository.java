package dz.itbridge.hospital.repository.auth;

import dz.itbridge.hospital.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUsername(String username);
}