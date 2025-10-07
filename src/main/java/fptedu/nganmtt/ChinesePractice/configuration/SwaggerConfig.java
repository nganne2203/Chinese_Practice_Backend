package fptedu.nganmtt.ChinesePractice.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
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
                            .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/nganne2203/Chinese_Practice_Backend"));
    }
}
