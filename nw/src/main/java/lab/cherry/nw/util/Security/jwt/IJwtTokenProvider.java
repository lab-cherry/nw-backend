package lab.cherry.nw.util.Security.jwt;

import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.util.Security.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJwtTokenProvider {
    AccessToken createJwtToken(String username, RoleEntity roles);
    boolean validateToken(AccessToken token);
    AccessToken resolveJwtToken(HttpServletRequest request);
    Authentication getAuthentication(AccessToken token);
}
