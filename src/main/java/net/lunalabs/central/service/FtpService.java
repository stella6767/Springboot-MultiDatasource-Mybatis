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



@EnableAsync
@Service
public class FtpService {

	
	private static final Logger logger = LoggerFactory.getLogger(FtpService.class);

//	@Async
//	public void ftpForward(String ip, String strFileName) {
//		
//		try {
//			
//			Map<String, Object> imgMap =new HashMap<String, Object>();
//			imgMap = userDao.selectImgSetting(imgMap);
//			
//			String saveFilePath	= (String) commonConf.get("EKG.FILE.UPLOAD.DIR");
//			
//			File file2 = new File (saveFilePath + strFileName);
//			
//			// 보낸 IP 가 설정이 있는 지 확인해서 있다면
//			// ftp 로 전송한다. 
//			// 1. file -> file.tmp 로 업로드 
//	
//			int index= ip.indexOf("/"); 
//			ip = ip.substring(index+1);
//			
//			logger.trace("testIp=="+ip);
//			//ENS-100A 파일 전송
//	
//			
//			if( commonConf.get("FUKUDAGW.FTP.FORWARD."+ip) != null && !"".equals(commonConf.get("FUKUDAGW.FTP.FORWARD."+ip)) ) {
//				logger.trace("ftp="+ip);
//				//연결 
//				FTPClient ftp = ftpInfo.ftpEcgConnetction(ip);
//	
//	
//				ftpInfo.ecgUpload(file2, strFileName, ftp);
//				//연결 끊기
//				ftpInfo.ftpDisConnetction(ftp);
//			}else {
//				logger.trace("ftp False="+ip);
//			}
//			
//		} catch (Exception e) {
//			logger.error("ftpForward Exception", e);
//		}
//	}
//	
//	@Async
//	public void parserRun(String ip, String strFileName) {
//		
//		String saveFilePath	= (String) commonConf.get("EKG.FILE.UPLOAD.DIR");
//		String parserFile	= (String) commonConf.get("EKG.FILE.PARSER");
//		
//		logger.trace(parserFile + " " + saveFilePath + " " + strFileName );
//		
//		int index= ip.indexOf("/"); 
//		ip = ip.substring(index+1);
//		
//		try {
//			
//	    	// File Decoding
//			Runtime rt = Runtime.getRuntime();
//			Process	p;
//			p	= rt.exec(parserFile + " " + saveFilePath + " " + strFileName );
//			p.waitFor();
//			
//			File file = new File (saveFilePath + File.separator + strFileName + ".log");
//			logFileParser.genAllFile(file, ip);
//			
//			// 10초 후 마지막에 파일을 삭제 한다. (FTP 전송중 일 수 있으므로) 
//			Thread.sleep(10000);
//			Files.delete(Paths.get(saveFilePath + strFileName));
//		} catch (Exception e) {
//			logger.error("parserRun Exception", e);
//		}
//	}
	
	
	
}
