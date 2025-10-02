package fptedu.nganmtt.ChinesePractice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Chinese Practice API", version = "1.0", description = "Documentation APIs v1.0"))
public class ChinesePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChinesePracticeApplication.class, args);
	}

}
