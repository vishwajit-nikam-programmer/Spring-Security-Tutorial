package com.security.code.serviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTServiceImpl {
   public static final String SECRET = "MySuperSecretKey12345#129vs1299x9191gu29snsnhxh18w20bx";

    public String generateToken(String username,String role){
         HashMap<String, Object> claims = new HashMap<>();
         claims.put("Role", role);

           return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 3600*1000*24))
                   //Here we added hashmap wala claims
                .addClaims(claims)
                .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //Mechanism or function for token validation
    public Claims verifySignatureAndExtractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String ExtractUsername(String token){
        return verifySignatureAndExtractAllClaims(token).getSubject();
    }

    public Date getExpiration(String token){
        return verifySignatureAndExtractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
       return getExpiration(token).before(new Date());
    }
}
