package pl.lodz.p.ias.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SkphApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkphApplication.class, args);
	}

}
