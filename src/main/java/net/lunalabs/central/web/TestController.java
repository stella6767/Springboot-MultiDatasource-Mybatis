package net.lunalabs.central.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.oracle.Test;
import net.lunalabs.central.mapper.oracle.TestMapper;
import net.lunalabs.central.web.dto.CMRespDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	
	private final TestMapper testMapper;
	
	
	@GetMapping("/test")
	public CMRespDto<?> test() {
		
		logger.info("test 통신 성공");
		
		return new CMRespDto<>(1, "test 통신 성공", null);
	}
	
	
	@GetMapping("/test2")
	public CMRespDto<?> test2() {
		
		logger.info("젠킨스 script test");
		
		return new CMRespDto<>(1, "jenkis github push trigger event", null);
	}
	
	
	
	@GetMapping("/log")
	public void testLog() {
		
		log.info("lombok log");
		
	}
	
	
	
	@GetMapping("/oracle")
	public List<Test> 원격접속테스트() {
		
		log.info("oracle 원격접속");
		
		
		return testMapper.findAll();
		
	}
	
	
	
	
}
