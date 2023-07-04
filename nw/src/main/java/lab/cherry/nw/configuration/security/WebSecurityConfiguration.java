package lab.cherry.nw.configuration.security;

import lab.cherry.nw.configuration.security.jwt.CustomAccessDeniedHandler;
import lab.cherry.nw.configuration.security.jwt.JwtFilter;
import lab.cherry.nw.configuration.security.jwt.UnauthorizedHandler;
import lab.cherry.nw.util.Security.jwt.IJwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 * ClassName : WebSecurityConfiguration
 * Type : class
 * Descrption : Spring Security 설정과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : Spring Security
 * </pre>
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UnauthorizedHandler unauthorizedHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationProvider authenticationProvider;
    private final IJwtTokenProvider iJwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 토큰 사용으로 csrf 설정 Disable 처리
        http
            .cors()
                .disable()
            .csrf()
                .disable()
            .formLogin()
                .disable()
            .httpBasic()
                .disable();

        // 엔트리 포인트
        http
            .authorizeHttpRequests()
            .requestMatchers(
                    "/api/auth/**",
                    "/docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
            )
              .permitAll()
            .requestMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER") // spring boot 에서 ROLE_ 은 자동으로 붙음
            .anyRequest()
              .authenticated();

        http
            // 권한이 없는 경우 Exception 핸들링 지정
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(new JwtFilter(iJwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // 세션 사용하지 않음 (STATELESS 처리)

        return http.build();
    }
}