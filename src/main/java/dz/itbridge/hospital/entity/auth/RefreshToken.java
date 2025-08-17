package dz.itbridge.hospital.entity.auth;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshtoken_id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(name = "expirydate")
    private Instant expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(String token, String username, Instant expiryDate) {
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}