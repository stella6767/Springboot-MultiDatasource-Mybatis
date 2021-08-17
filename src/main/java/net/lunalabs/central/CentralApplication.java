package net.lunalabs.central;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import net.lunalabs.central.config.CustomBeanNaming;

@SpringBootApplication
public class CentralApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		final SpringApplicationBuilder builder = new SpringApplicationBuilder(CentralApplication.class);

		// beanNameGenerator 를 등록한다.
		builder.beanNameGenerator(new CustomBeanNaming());
		builder.run(args);		
	  
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(CentralApplication.class);
	}
}
