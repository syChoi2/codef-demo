package com.codef.api.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.codef.api.configuration.JwtConfiguration;
import com.codef.api.dto.JwtAuthentication;
import com.codef.api.entity.Users;
import com.codef.api.enums.Role;
import com.codef.api.exception.BusinessException;
import com.codef.api.exception.ErrorCode;
import com.codef.api.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

    private final UserService userService;

    private final String header;

    private final String issuer;

    private final String serverKey;

    private final int expirySeconds;

    public JwtProvider(JwtConfiguration jwtConfiguration, UserService userService) {
        this.userService = userService;
        this.header = jwtConfiguration.getHeader();
        this.issuer = jwtConfiguration.getIssuer();
        this.serverKey = jwtConfiguration.getServerKey();
        this.expirySeconds = jwtConfiguration.getExpirySeconds();
    }

    public String createToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims();
        claims.put("roles", roles);
        claims.put("userId", userId);
        Date now = new Date();
        JwtBuilder jwts = Jwts.builder();
        jwts.setClaims(claims);
        jwts.setIssuer(issuer);
        jwts.setIssuedAt(now);
        if (expirySeconds > 0) {
            jwts.setExpiration(new Date(now.getTime() + expirySeconds * 1_000L));
        }
        jwts.signWith(SignatureAlgorithm.HS512, serverKey);
        return jwts.compact();
    }

    public Authentication getAuthentication(String token) {
        Long userId = getUserId(token);
        Users users = userService.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return new UsernamePasswordAuthenticationToken(new JwtAuthentication(users.getUserId()), "", Stream.of(Role.USER.value()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    private Long getUserId(String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(serverKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userId")
                .toString()
        );
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(header);
    }

    public boolean validateToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(serverKey).parseClaimsJws(jwtToken);
        if(claims.getBody().getExpiration() == null) return true;
        return !claims.getBody().getExpiration().before(new Date());
    }

}
