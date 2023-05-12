package com.cms_service.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    public static final String SECRET_KEY="73357638792F413F4428472B4B6250655368566D597133743677397A24432645";
    public String createToken(String subject){
        Map<String,Object> claims=new HashMap<>();
        String token= Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        return token;
    }

    public String extractID(String token){
        token=token.substring(7);
        Claims claims=Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}