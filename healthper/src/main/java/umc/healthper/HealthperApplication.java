package umc.healthper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class HealthperApplication {
	@PostConstruct
	private void start() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));}

	public static void main(String[] args) {
		SpringApplication.run(HealthperApplication.class, args);
	}
}
