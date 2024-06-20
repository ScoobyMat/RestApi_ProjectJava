package com.project.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final SecretKey accessSecretKey;
    private final Integer accessTokenValidityInMin;
    private final SecretKey refreshSecretKey;
    private final Integer refreshTokenValidityInMin;

    public JwtService(@Value("${jwt.secret-key}") String accessSecretKey,
                      @Value("${jwt.token-validity-in-min}") Integer accessTokenValidityInMin,
                      @Value("${jwt.refresh-secret-key}") String refreshSecretKey,
                      @Value("${jwt.refresh-token-validity-in-min}") Integer refreshTokenValidityInMin) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMin = accessTokenValidityInMin;
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
        this.refreshTokenValidityInMin = refreshTokenValidityInMin;
    }

    private SecretKey getSignKey() {
        return accessSecretKey;
    }

    private Claims extractAllClaims(String token, SecretKey secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, SecretKey secretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    public String extractUserNameFromAccessToken(String token) {
        return extractUserName(token, this.accessSecretKey);
    }

    public String extractUserNameFromRefreshToken(String token) {
        return extractUserName(token, this.refreshSecretKey);
    }

    public String extractUserName(String token, SecretKey secretKey) {
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    private Date extractExpiration(String token, SecretKey secretKey) {
        return extractClaim(token, secretKey, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final Claims claims = extractAllClaims(token, this.accessSecretKey);
        final Date expiration = claims.getExpiration();
        final String userName = claims.getSubject();
        boolean valid = !expiration.before(Date.from(Instant.now()));
        return userName.equals(userDetails.getUsername()) && valid;
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final Claims claims = extractAllClaims(token, this.refreshSecretKey);
        final Date expiration = claims.getExpiration();
        final String userName = claims.getSubject();
        boolean valid = !expiration.before(Date.from(Instant.now()));
        return userName.equals(userDetails.getUsername()) && valid;
    }

    public boolean isTokenExpired(String token, SecretKey secretKey) {
        return extractExpiration(token, secretKey).before(Date.from(Instant.now()));
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, SecretKey secretKey, Integer tokenValidityInMin) {
        final Instant now = Instant.now();
        final Instant expiration = now.plus(tokenValidityInMin, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(Map.of(), userDetails);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return generateToken(extraClaims, userDetails, this.accessSecretKey, this.accessTokenValidityInMin);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(Map.of(), userDetails);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return generateToken(extraClaims, userDetails, this.refreshSecretKey, this.refreshTokenValidityInMin);
    }
}
