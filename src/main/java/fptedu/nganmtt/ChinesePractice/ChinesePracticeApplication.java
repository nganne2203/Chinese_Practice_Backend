package fptedu.nganmtt.ChinesePractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChinesePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChinesePracticeApplication.class, args);
	}

}
