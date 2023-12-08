package supplobang.services.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import supplobang.services.JWTService;

@Service
public class JWTServiceImpl implements JWTService {

    public static final int jwtTokenTime = 1000 * 60 * 24;
    public static final int jwtRefreshTokenTime = 604800000;
    // public static final String SECRETKEY = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

      private String secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = generateRandomKey();
    }

    private String generateRandomKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating random key", e);
        }
        keyGenerator.init(256); // Set the key size as needed, here it's set to 256 bits
        Key secretKey = keyGenerator.generateKey();
        return java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() +  jwtTokenTime))
               .signWith(getSignKey(), SignatureAlgorithm.HS256)
               .setHeaderParam("typ", "JWT")
               .compact();
    }

    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshTokenTime))
               .signWith(getSignKey(), SignatureAlgorithm.HS256)
               .setHeaderParam("typ", "JWT")
               .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSignKey(){
        // byte[] key = Decoders.BASE64.decode(secretKey);
        // return Keys.hmacShaKeyFor(key);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // private Claims extractAllClaims(String token){
    //     System.out.println(Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token).getBody());
    //     return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token).getBody();
    // }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            // Log the exception or handle it according to your application's needs
            System.err.println("Error occurred while parsing the JWT token: " + e.getMessage());
            throw new JwtAuthenticationException("Invalid JWT token", e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
