package lab.cherry.nw.configuration.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        log.info("RestAPI Call Log\n---\nTimestamp: {}\nIP: {}\nMethod: {}\nRequestURI: {}\n[Body]\n{}\n---",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI(),
                getRequestBody(request));
    }

    private String getRequestBody(HttpServletRequest request) {
        try {
            // Read the request body content
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("Error while reading request body: ", e);
            return "";
        }
    }
}