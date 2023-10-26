package lab.cherry.nw.configuration;

import lab.cherry.nw.configuration.filter.JwtFilter;
import lab.cherry.nw.configuration.filter.RequestLoggingFilter;
import lab.cherry.nw.error.handler.CustomAccessDeniedHandler;
import lab.cherry.nw.error.handler.UnauthorizedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * <pre>
 * ClassName : WebSecurityConfiguration
 * Type : class
 * Description : Spring Security 설정과 관련된 함수를 포함하고 있는 클래스입니다.
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
    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final RequestLoggingFilter requestLoggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 토큰 사용으로 csrf 설정 Disable 처리
        http
            .csrf(csrf -> csrf
                    .disable())
            .formLogin(login -> login
                    .disable())
            .httpBasic(basic -> basic
                    .disable())
            .cors(cors -> cors
                    .configurationSource(corsConfigurationSource));

        // 엔트리 포인트
        http
            .authorizeHttpRequests()
            .requestMatchers(
                    "/api/auth/**",
                    "/docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api/v1/mail/**"
            )
              .permitAll()
            .requestMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER") // spring boot 에서 ROLE_ 은 자동으로 붙음
            .anyRequest()
              .authenticated();

        http
            // 권한이 없는 경우 Exception 핸들링 지정
            .exceptionHandling(handling -> handling
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler))
            .authenticationProvider(authenticationProvider)
            .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 세션 사용하지 않음 (STATELESS 처리)

        http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(requestLoggingFilter, JwtFilter.class);

        return http.build();
    }
}