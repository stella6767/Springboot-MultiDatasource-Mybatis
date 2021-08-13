package net.lunalabs.central.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.web.dto.CMRespDto;

@Slf4j
@RestController
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	
	@GetMapping("/test")
	public CMRespDto<?> test() {
		
		logger.info("test 통신 성공");
		
		return new CMRespDto<>(1, "test 통신 성공", null);
	}
	
	
	
	@GetMapping("/log")
	public void testLog() {
		
		log.info("lombok log");
		
	}
	
	
	
}
