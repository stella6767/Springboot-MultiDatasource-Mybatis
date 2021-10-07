package net.lunalabs.central.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.bilabfile.BilabFile;
import net.lunalabs.central.mapper.mysql.BilabFileMapper;



@RequiredArgsConstructor
@Service
public class FtpService {

	
	private static final Logger logger = LoggerFactory.getLogger(FtpService.class);

	private final BilabFileMapper bilabFileMapper;
	
	
	@Async  //굳이 비동기 필요하진 않지만 일단 
	public void ftpFileDBSave(String filepath, String fileName) {	
		
		
		BilabFile bilabFile = BilabFile.builder()
				.fileName(fileName)
				.filePath(filepath)
				.build();
				
		
		bilabFileMapper.save(bilabFile);
	}
	
	
	
	
}
