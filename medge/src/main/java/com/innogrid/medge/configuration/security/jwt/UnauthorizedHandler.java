package com.innogrid.medge.configuration.security.jwt;

import com.innogrid.medge.error.enums.ErrorCode;
import com.innogrid.medge.error.exception.CustomException;
import com.innogrid.medge.util.FormatConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(FormatConverter.toJson(
            ErrorCode.ACCESS_DENIED_EXCEPTION
        ));
        response.getWriter().flush();
        response.getWriter().close();

    }
}