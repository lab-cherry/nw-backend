package lab.cherry.nw.configuration.filter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logRequestDetails(request);
        filterChain.doFilter(request, response);
    }

    private void logRequestDetails(HttpServletRequest request) {

        Instant currentTime = Instant.now();
        String PATTERN_FORMAT = "yyyy/MM/dd HH:mm:ss";
        String formattedInstant = DateTimeFormatter
                                    .ofPattern(PATTERN_FORMAT)
                                    .withZone(ZoneId.of("Asia/Seoul"))
                                    .format(currentTime);

        log.info("RestAPI Call Log\n---\nTimestamp: {}\nIP: {}\nMethod: {}\nRequestURI: {}\nContent-Type: {}\n" + //
                "---",
                formattedInstant,
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI(),
                request.getContentType());
    }
}