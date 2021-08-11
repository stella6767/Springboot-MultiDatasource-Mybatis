package net.lunalabs.central.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	 private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	
	@GetMapping("/test")
	public void test() {
		
		log.info("test 통신 성공");
	}
	
	
}
