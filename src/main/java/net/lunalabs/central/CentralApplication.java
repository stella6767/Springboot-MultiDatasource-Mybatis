package net.lunalabs.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CentralApplication {

	private static final String PROPERTIES = "spring.config.location=" + "classpath:/application.yml"
			+ ",classpath:/ftpconfig.yml";

	public static void main(String[] args) {

		new SpringApplicationBuilder(CentralApplication.class).run(args);  //properties(PROPERTIES).

	}

}
