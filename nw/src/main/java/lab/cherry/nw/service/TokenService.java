package lab.cherry.nw.service;

import io.jsonwebtoken.Claims;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.util.Security.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface TokenService {
    AccessToken generateJwtToken(String userid, RoleEntity roles);
    boolean validateToken(AccessToken token);
    AccessToken resolveJwtToken(HttpServletRequest request);
    Authentication getAuthentication(AccessToken token);
	Claims getJwtInfo(String token);
}
