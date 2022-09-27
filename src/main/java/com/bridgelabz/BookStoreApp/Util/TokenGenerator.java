package com.bridgelabz.BookStoreApp.Util;

import com.bridgelabz.BookStoreApp.Model.UserData;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenGenerator {


    public String generateLoginToken(UserData userDetails) {
        System.out.println("generate token id:   " + userDetails.getUserId());

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .setId(String.valueOf(userDetails.getUserId()))
                .setSubject(userDetails.getFirstName() + userDetails.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + 100000000))
                .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                .compact();
    }

    public String generateVerificationToken(UserData userDetails) {

        long currentTime = System.currentTimeMillis();
        System.out.println("generate token id:   " + userDetails.getUserId());
        return Jwts.builder()
                .setId(String.valueOf(userDetails.getUserId()))
                .setSubject(userDetails.getFirstName() + userDetails.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + 100000000))
                .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                .compact();
    }

    public String decodeJWT(String jwt) throws JwtException {
        System.out.println(jwt);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("sd5745FAHFW").parseClaimsJws(jwt).getBody();
            System.out.println(claims);

            System.out.println("jwt id: " + claims.getId());
            return claims.getId();
        } catch (ExpiredJwtException e) {
            throw new JwtException("session time out");
        }
    }
}