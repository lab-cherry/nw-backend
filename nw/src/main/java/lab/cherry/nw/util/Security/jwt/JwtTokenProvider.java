package lab.cherry.nw.util.Security.jwt;

import java.util.*;

import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.service.security.CustomUserDetailsService;
import lab.cherry.nw.util.Security.AccessToken;
import lab.cherry.nw.util.Security.SecretKey;
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

    @Value("${lab.cherry.nw.jwtSecret}")
    private String jwtSecretKey;

    @Value("${lab.cherry.nw.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final IJwtTokenHelper tokenHelper;
    private final CustomUserDetailsService userDetailsService;

    public AccessToken createJwtToken(String username, RoleEntity role) {
        log.error("username is {}", username);
        log.error("role is {}", role);
        SecretKey secretKey = new SecretKey(jwtSecretKey, jwtExpirationMs);
        String token = tokenHelper.generateJwtToken(secretKey, username, role);
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
