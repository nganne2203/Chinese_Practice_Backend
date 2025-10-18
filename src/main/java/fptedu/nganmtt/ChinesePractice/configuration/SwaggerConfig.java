package fptedu.nganmtt.ChinesePractice.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final AppProperties appProperties;

    @Value(value = "${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI openApi(){
        String securitySchemeName = "bearerAuth";

        String serverUrl = appProperties.getBackendUrl() != null ?
                appProperties.getBackendUrl() : "http://localhost:8080";

        String contextPathSuffix = "";
        if (contextPath != null && !contextPath.isEmpty() && !contextPath.equals("/")) {
            contextPathSuffix = contextPath;
        }

        return new OpenAPI()
                .addServersItem(new Server()
                        .url(serverUrl + contextPathSuffix)
                        .description("Chinese Practice Production Backend Server")
                )
                .addServersItem(new Server()
                        .url("http://localhost:8080" + contextPathSuffix)
                        .description("Chinese Practice Local Backend Server")
                )
                .info(new Info()
                    .title("Chinese Practice API")
                    .version("1.0")
                    .description("API for Chinese Practice Application")
                    .contact(new Contact()
                            .name("NganMTT")
                            .email("thanhngan.pt2004@mail.com")
                            .url("https://www.nganmtt.com"))
                    .license(new License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/nganne2203/Chinese_Practice_Backend")
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter JWT token with 'Bearer ' prefix")
                        )
                );
    }
}
