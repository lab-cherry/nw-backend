package com.innogrid.medge.util.Security.jwt;

import java.util.*;

import com.innogrid.medge.model.RoleEntity;
import com.innogrid.medge.service.security.CustomUserDetailsService;
import com.innogrid.medge.util.Security.AccessToken;
import com.innogrid.medge.util.Security.SecretKey;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider implements IJwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${com.innogrid.medge.jwtSecret}")
    private String jwtSecretKey;

    @Value("${com.innogrid.medge.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final IJwtTokenHelper tokenHelper;
    private final CustomUserDetailsService userDetailsService;

    public AccessToken createJwtToken(String username, Set<RoleEntity> roles) {
        log.error("username is {}", username);
        log.error("roles is {}", roles);
        SecretKey secretKey = new SecretKey(jwtSecretKey, jwtExpirationMs);
        String token = tokenHelper.generateJwtToken(secretKey, username, roles);
        return new AccessToken(token);
    }

    public boolean validateToken(AccessToken token) {
        SecretKey secretKey = new SecretKey(jwtSecretKey,jwtExpirationMs);
        return tokenHelper.validateJwtToken(secretKey,token);
    }

    public AccessToken resolveJwtToken(HttpServletRequest request) {
        final String headerAuth = request.getHeader(AUTHORIZATION_HEADER);

        if (headerAuth == null) return null;
        if (!headerAuth.startsWith("Bearer ")) return null;

        return new AccessToken(headerAuth.substring(7));
    }

    public Authentication getAuthentication(AccessToken token) {
        SecretKey secretKey = new SecretKey(jwtSecretKey, jwtExpirationMs);
        String username = tokenHelper.getUsernameFromJwtToken(secretKey, token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
