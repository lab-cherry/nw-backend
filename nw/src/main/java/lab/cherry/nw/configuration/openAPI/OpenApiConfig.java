package lab.cherry.nw.configuration.openAPI;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * ClassName : OpenApiConfig
 * Type : class
 * Descrption : Swagger springdoc-ui 설정과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : springdoc-openapi-ui
 * </pre>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("NW API Document")
                .version("v0.0.1")
                .description("NW Project Swagger Docs.")
                .contact(new Contact().name("CherryLab").email("").url(""))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                .description("Develop Team : Cherry Lab");

        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}