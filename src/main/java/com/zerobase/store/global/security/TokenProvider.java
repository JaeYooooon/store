package com.zerobase.store.global.security;

import com.zerobase.store.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private final UserService userService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param userName 사용자 이름
     * @param roles    역할 리스트
     * @return JWT 토큰
     */
    public String createToken(String userName, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(KEY_ROLES, roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * JWT 토큰을 사용하여 인증 객체 반환.
     *
     * @param jwt JWT 토큰
     * @return 인증 객체
     */
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = this.userService.loadUserByUsername(this.getUserName(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * JWT 토큰을 해석 후 클레임 반환
     *
     * @param token JWT 토큰
     * @return 클레임
     */
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰에서 사용자 이름을 반환
     *
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String getUserName(String token) {
        return this.parseClaims(token).getSubject();
    }
}
