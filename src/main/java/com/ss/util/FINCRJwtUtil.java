package com.ss.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class FINCRJwtUtil {
    private static final long EXPIRATION_TIME = 864000000;
    private static String SECRET_KEY = "secret";

    public static String generateToken(String userName)
    {
        return createToken(userName,new HashMap<String,Object>());
    }

    public static String createToken(String subject, Map<String,Object> claims)
    {
        String token =  Jwts.builder().setClaims(claims)
                            .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                            .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        return token;
    }

    public static String extractUserName(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public static Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public static Boolean validateToken(String token, UserDetails userDetails)
    {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
