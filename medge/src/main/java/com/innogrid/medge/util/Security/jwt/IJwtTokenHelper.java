package com.innogrid.medge.util.Security.jwt;

import com.innogrid.medge.model.RoleEntity;
import com.innogrid.medge.util.Security.AccessToken;
import com.innogrid.medge.util.Security.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Set;

public interface IJwtTokenHelper {
    String generateJwtToken(SecretKey secretKey, String username, Set<RoleEntity> roles);
    boolean validateJwtToken(SecretKey secretKey, AccessToken accessToken);
    String getUsernameFromJwtToken(SecretKey secretKey,AccessToken accessToken);
}
