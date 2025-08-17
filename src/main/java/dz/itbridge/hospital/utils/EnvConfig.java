package dz.itbridge.hospital.utils;

public class EnvConfig {

    // JWT Configuration
    public static final String JWT_SECRET = "bwnXbtxtudY9jRXGIcrCSl+oMpJvnIuddm3iJ2tS4vB4ts8q9SmS67qmpG6DRY13HsTTACsugju/TCI+jQlDfA==";
    public static final long JWT_EXPIRATION_MS = 1800000; // 1/2 hour
    public static final long JWT_REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000L;

    // API Endpoints
    public static final String API_AUTH = "/api/auth";
    public static final String API_DOCTORS = "/api/doctors";

}
