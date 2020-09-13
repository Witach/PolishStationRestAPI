package pl.polishstation.polishstationbackend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polishstation.polishstationbackend.auth.userdetails.UserDetailsImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtils {

    public static String SECRET = "asdasdasdasd";

    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }
    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(getSubstringOfJWT(token))
                .getBody();
    }

    private static String getSubstringOfJWT(String token) {
        return token.startsWith("Bearer ")? token.substring(7): token;
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public static String generateToken(UserDetails userDetails) {
        var userDetailsImpl = (UserDetailsImpl)userDetails;
        var claimsMap = new HashMap<String, Object>();
        claimsMap.put("userDetails", userDetailsImpl);
        return createToken(claimsMap, userDetails.getUsername());
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static JwtTokenBuilder builder() {
        return new JwtTokenBuilder();
    }

    public static class JwtTokenBuilder {
        private Map<String, Object> claims = new HashMap<>();
        private String subject;
        private Date issuedAt = new Date(System.currentTimeMillis());
        private Date expiration;

        public JwtTokenBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public JwtTokenBuilder expiration(LocalDateTime expiration) {
            var zoneDateTime = expiration.atZone(ZoneId.systemDefault());
            this.expiration = Date.from(zoneDateTime.toInstant());
            return this;
        }

        public JwtTokenBuilder issuedAt(LocalDateTime issuedAt) {
            var zoneDateTime = issuedAt.atZone(ZoneId.systemDefault());
            this.issuedAt = Date.from(zoneDateTime.toInstant());
            return this;
        }

        public String build() {
             return Jwts.builder()
                     .setClaims(claims)
                     .setSubject(subject)
                     .setIssuedAt(issuedAt)
                     .setExpiration(expiration)
                     .signWith(SignatureAlgorithm.HS256, SECRET)
                     .compact();
         }
    }

}
