package dev.teamproject.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Utility class for generating, parsing, and validating JWT tokens.
 * This class provides methods to create tokens, extract information from tokens, 
 * and check token expiration.
 */
@Component
public class JwtUtil {

  private final String secretKey = "testtesttest";

  /**
   * Generate a token with an expiration time.
   * */
  public String generateToken(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours exp
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  // get email from token
  public String extractEmail(String token) {
    return extractClaims(token).getSubject();
  }

  // Check if token is expired
  public boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }
}
