package com.zerobase.store.global.security;

import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.domain.user.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.zerobase.store.global.security.JwtAuthenticationFilter.TOKEN_HEADER;
import static com.zerobase.store.global.security.JwtAuthenticationFilter.TOKEN_PREFIX;

@RequiredArgsConstructor
@Component
public class TokenProvider {


    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
    private final UserService userService;


    @Value("{spring.jwt.secret}")
    private String secretKey;


    // JWT 토큰 생성
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

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = this.userService.loadUserByUsername(this.getUserName(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


//    public String getTokenFromRequest(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            return authorizationHeader.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserName(String token){
        return this.parseClaims(token).getSubject();
    }

    private Set<String> invalidateTokens = new HashSet<>();

    public boolean validateToken(String token){
        if(!StringUtils.hasText(token)) return false;
        if(invalidateTokens.contains(token)) return false;
        Claims claims = this.parseClaims(token);

        return !claims.getExpiration().before(new Date());
    }

    public void invalidateToken(String token){
        invalidateTokens.add(token);
    }

    public boolean isTokenInvalidated(String token) {
        return invalidateTokens.contains(token);
    }
}
