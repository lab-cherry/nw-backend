package lab.cherry.nw.error.handler;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.util.FormatConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <pre>
 * ClassName : UnauthorizedHandler
 * Type : class
 * Descrption : 인증이 안된 경우 UnauthorizedHandler Exception 설정과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : Spring Security
 * </pre>
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * [Exception] 인증이 안된 경우 예외처리 함수
     * <pre>
     * 상태 코드와 관련 메세지를 반환합니다.
     * {
     *     "status": 401,
     *     "message": "Unauthorized."
     * }
     * </pre>
     *
     * Author : taking(taking@duck.com)
     *
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(FormatConverter.toJson(
            ErrorCode.ACCESS_DENIED_EXCEPTION
        ));
        response.getWriter().flush();
        response.getWriter().close();

    }
}