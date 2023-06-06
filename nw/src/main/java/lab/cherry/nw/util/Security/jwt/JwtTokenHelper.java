package lab.cherry.nw.util.Security.jwt;

import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.util.Security.AccessToken;
import lab.cherry.nw.util.Security.SecretKey;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenHelper implements IJwtTokenHelper {

    @Override
    public String generateJwtToken(SecretKey secretKey, String username, Set<RoleEntity> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities",roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));


        Date issuedAt = new Date(System.currentTimeMillis());
        Date validUntil = new Date(System.currentTimeMillis() + secretKey.getExpirationInMiliseconds());
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(validUntil)
                .signWith(getSignInKey(secretKey))
                .compact();
    }

    @Override
    public boolean validateJwtToken(SecretKey secretKey, AccessToken token) {
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token.getToken());
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;

//        catch (JwtException | IllegalArgumentException exception) {
//            throw new CustomSecurityException(ApiMessages.INVALID_TOKEN, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @Override
    public String getUsernameFromJwtToken(SecretKey secretKey, AccessToken accessToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(accessToken.getToken())
                .getBody()
                .getSubject();
    }

    private Key getSignInKey(SecretKey secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
