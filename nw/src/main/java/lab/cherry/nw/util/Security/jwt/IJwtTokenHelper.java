package lab.cherry.nw.util.Security.jwt;

import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.util.Security.AccessToken;
import lab.cherry.nw.util.Security.SecretKey;

public interface IJwtTokenHelper {
    String generateJwtToken(SecretKey secretKey, String username, RoleEntity role);
    boolean validateJwtToken(SecretKey secretKey, AccessToken accessToken);
    String getUsernameFromJwtToken(SecretKey secretKey,AccessToken accessToken);
}
