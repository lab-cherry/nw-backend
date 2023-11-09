package lab.cherry.nw.error.handler;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.util.FormatConverter;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * <pre>
 * ClassName : CustomAccessDeniedHandler
 * Type : class
 * Descrption : 권한이 없는 경우 CustomAccessDeniedHandler Exception 설정과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : Spring Security
 * </pre>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * [Exception] 권한 없는 경우 예외처리 함수
     * <pre>
     * 상태 코드와 관련 메세지를 반환합니다.
     * {
     *     "status": 401,
     *     "message": "Forbidden."
     * }
     * </pre>
     *
     * Author : taking(taking@duck.com)
     *
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(FormatConverter.toJson(
            ErrorCode.FORBIDDEN
        ));
        response.getWriter().flush();
        response.getWriter().close();

    }
}