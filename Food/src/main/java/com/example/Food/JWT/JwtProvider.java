package com.example.Food.JWT;

import com.example.Food.Entity.User.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
@Component
public class JwtProvider {
    private static final String SECRET_KEY = "e82c73692e6fa99b1770cfd6605bfc5b9ec3a12b362d9de5459a2612191497c4";
    private static final long JWT_EXPIRATION = 604800000L;
    public long TimeExpiration(){
        return JWT_EXPIRATION;
    }
    public String generateToken(CustomUserDetails userDetails){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getUserID()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(Map<String, Objects> extraClaim, CustomUserDetails userDetails){
        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder().setClaims(extraClaim)
                .setSubject(Long.toString(userDetails.getUser().getUserID()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public String ExtractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }//chích xuất tên người dùng từ token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }// so sánh thời gian trong jwt với thoi điểm hiện tại để xem token còn sử dụng được kh(hết hạn hay chưa)
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }//chích xuất thời gian hết hạn từ token
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
    }// kiểm tra tên người dùng có hợp lệ hay không và kiểm tra xem token đã hết hạn hay chưa
}
