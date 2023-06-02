package com.innogrid.medge.configuration.security.jwt;

import com.innogrid.medge.error.enums.ErrorCode;
import com.innogrid.medge.util.FormatConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

//        response.sendError((HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(FormatConverter.toJson(
            ErrorCode.FORBIDDEN
        ));
        response.getWriter().flush();
        response.getWriter().close();

    }
}