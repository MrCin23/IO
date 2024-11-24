package pl.lodz.p.ias.io;

import org.springframework.boot.SpringApplication;

public class TestSkphApplication {

	public static void main(String[] args) {
		SpringApplication.from(SkphApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
