package com.innogrid.medge.util.Security.jwt;

import com.innogrid.medge.model.RoleEntity;
import com.innogrid.medge.util.Security.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

public interface IJwtTokenProvider {
    AccessToken createJwtToken(String username, Set<RoleEntity> roles);
    boolean validateToken(AccessToken token);
    AccessToken resolveJwtToken(HttpServletRequest request);
    Authentication getAuthentication(AccessToken token);
}
