package com.example.Food.JWT;

import com.example.Food.Entity.User.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private static final String SECRET_KEY = "e82c73692e6fa99b1770cfd6605bfc5b9ec3a12b362d9de5459a2612191497c4";
    private static final long JWT_EXPIRATION = 3600000L; // 1 giờ
    private static final long REFRESH_TOKEN_EXPIRATION = 2592000000L;
    public String generateToken(CustomUserDetails userDetails){
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(now)
                    .claim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setExpiration(expiryDate)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        }
        catch (JwtException e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Failed to generate refresh token", e);
        }
    }

    public String ExtractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = ExtractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
