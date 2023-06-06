package lab.cherry.nw.util.Security.jwt;

import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.util.Security.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface IJwtTokenProvider {
    AccessToken createJwtToken(String username, Set<RoleEntity> roles);
    boolean validateToken(AccessToken token);
    AccessToken resolveJwtToken(HttpServletRequest request);
    Authentication getAuthentication(AccessToken token);
}
