package com.innogrid.medge.configuration.security.jwt;

import java.io.IOException;

import com.innogrid.medge.error.enums.ErrorCode;
import com.innogrid.medge.error.exception.CustomException;
import com.innogrid.medge.util.Security.AccessToken;
import com.innogrid.medge.util.Security.jwt.IJwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final IJwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AccessToken token = tokenProvider.resolveJwtToken(request);

        log.error("jwt is {}", token);
        try {
            if (checkAccessToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다.", authentication.getName());
            }
            filterChain.doFilter(request, response);

        }catch (AuthenticationException e) {
            log.error("Cannot set user authentication: ", e);
            SecurityContextHolder.clearContext();
            throw new CustomException(ErrorCode.INVALID_USERNAME);
        }
    }

    private boolean checkAccessToken(AccessToken accessToken) {
        if (accessToken == null) return false;
        return tokenProvider.validateToken(accessToken);
    }
}