package pl.lodz.p.ias.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"pl.lodz.p.ias.io.poszkodowani",
		"pl.lodz.p.ias.io.uwierzytelnianie"
})
public class SkphApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkphApplication.class, args);
	}

}
