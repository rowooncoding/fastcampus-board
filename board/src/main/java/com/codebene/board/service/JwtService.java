package com.codebene.board.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    // username으로 jwt 토큰 생성 -> 액세스 토큰으로 활용
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    // subject(username) 추출
    public String getUsername(String accessToken) {
        return getSubject(accessToken);
    }

    // 토큰 생성
    private String generateToken(String subject) {
        // 만료시점 : 현재 시각을 기준으로 세시간 이후
        Date now = new Date();
        Date exp = new Date(now.getTime() + (1000 * 60 * 60 * 3));

        return Jwts.builder().subject(subject).signWith(key).issuedAt(now).expiration(exp).compact();
    }

    // subject 추출
    private String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();

        } catch (JwtException e) {
            log.error("JwtException", e);
            throw e;
        }
    }
}
