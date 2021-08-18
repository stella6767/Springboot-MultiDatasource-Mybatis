package net.lunalabs.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CentralApplication {

	public static void main(String[] args) {
		

		SpringApplication.run(CentralApplication.class, args);
		
	}


//	@Bean
//	public CommandLineRunner emulatorStart(CSSocket emulator) {
//		return (args) -> {
//			// 데이터 초기화 하기			
//			emulator.start();;			
//		};
//	}

	
}
