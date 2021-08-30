//package net.lunalabs.central.config.ftp;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class FtpInfo {
//	
//	private static final Logger logger = LoggerFactory.getLogger(FtpInfo.class);
//
//
//
//	Map<String,Object> logMap =new HashMap<String,Object>();
//
//	
//	String dir = new String(); 
//	
//	
//	//frp 접속	
//	public FTPClient ftpConnetction() {
//		FTPClient ftp = null; 			// FTP Client 객체 
//		Map<String, Object> queryMap = new HashMap<String, Object>();
//		List<Map<String, Object>> ftpList = new ArrayList<Map<String, Object>>();
//		workListDao.selectEmrFtpList(queryMap);
//		logger.trace("#002 selectMap[" + queryMap.get("result"));
//		
//	
//		
//		ftpList = (List<Map<String, Object>>)queryMap.get("result");
//	
//		
//		
//		String url = new String(); 
//		String id = new String(); 
//		String pwd = new String(); 
//		String port = new String(); 
//		
///*		String url = commonConf.getProperty("FUKUDAGW.EMR.FTP.URL"); 
//		String id = commonConf.getProperty("FUKUDAGW.EMR.FTP.ID");
//		String pwd = commonConf.getProperty("FUKUDAGW.EMR.FTP.PW"); 
//		String port = commonConf.getProperty("FUKUDAGW.EMR.FTP.PORT");*/
//		
//
//		
//		try {
//			ftp = new FTPClient(); 		// FTP Client 객체 생성 
//			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩 
//			ftp.connect(url, Integer.parseInt(port)); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호 
//			ftp.login(id, pwd); 	// FTP 로그인 ID, PASSWORLD 입력 
//			ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
//		} catch (Exception e) {
//			logger.trace("IO Exception : " + e.getMessage()); 
//			// TODO: handle exception
//		}
//	
//		
//		return ftp;
//		
//	}
//
//	
//	//ECG frp 접속	
//	public FTPClient ftpEcgConnetction(String ip) {
//		FTPClient ftp = null; 			// FTP Client 객체 
//
////			logger.trace("FTP Forward Connecting. ip[" + ip + "]");
//			String[] forwardInfo = ((String) commonConf.get("FUKUDAGW.FTP.FORWARD."+ip)).split("/");
//		
//
//			String url = forwardInfo[0]; 
//			String id = forwardInfo[1];
//			String pwd = forwardInfo[2]; 
//			String port = forwardInfo[3];
//			
////			logger.trace("FTP Forward Server Info. forwardInfo[" + forwardInfo + "]");
//			
//
//		try {
//			ftp = new FTPClient(); 		// FTP Client 객체 생성 
//			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩 
//			ftp.connect(url, Integer.parseInt(port)); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호 
//			ftp.login(id, pwd); 	// FTP 로그인 ID, PASSWORLD 입력 
//			ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
//			
//			
//			logger.trace("FTP Forward Server Connected. ip[" + ip + "]");
//			
//		} catch (Exception e) {
//			logger.trace("IO Exception : " + e.getMessage()); 
//			// TODO: handle exception
//		}
//		
//		
//		return ftp;
//		
//	}
//	
//	
//	
//	
//	//파일 정보생성 	
//	public int upload(ExamInfoVo examInfo,String[] exam_list,FTPClient ftp, int i, HttpSession session) throws Exception
//	{ 
//		
//	/*	logger.trace("ftp 접속=="+examInfo.getExamSeq()+"||"+examInfo.getPatId());*/
//		String localImgFilePathName = examInfo.getImgFile().substring(0,examInfo.getImgFile().lastIndexOf(".")) + ".jpg";
//		String localEmrFilePathName = examInfo.getEmrFile();
//		/*
//		저장폴더 규칙 : 파일서버 URL 정보에,
//
//		/"00" + 환자등록번호 앞 3자리 / "00" + 환자등록번호 (8자리) / XML  <- XML 파일
//
//		/"00" + 환자등록번호 앞 3자리 / "00" + 환자등록번호 (8자리) / SCAN <- JPG 파일
//		
//		*/
//		
//		String remoteImgFilePath = dir+"/PATIENT/00" 
//				+ examInfo.getPatId().substring(0,3)
//				+ "/00" 
//				+ examInfo.getPatId()
//				+ "/SCAN";
//		
//		String remoteFilePathRoot =dir+"/PATIENT/";
//		
//		String remoteEmrFilePath =dir+"/PATIENT/00"
//				+ examInfo.getPatId().substring(0,3)
//				+ "/00" 
//				+ examInfo.getPatId()
//				+ "/XML";
//		int folderChrLastIndex=0;
//		String uploadImgFileName="";	
//		String uploadEmrFileName="";	
//				
//		logger.trace("#007 remote Img Path[" + remoteImgFilePath + "]");
//		logger.trace("#007 remote Emr Path[" + remoteEmrFilePath + "]");
//	if(exam_list!=null){
//				 folderChrLastIndex = localImgFilePathName.lastIndexOf(File.separator);
//				 uploadImgFileName = localEmrFilePathName.substring(folderChrLastIndex, localEmrFilePathName.length());
//				 int extChrLastIndex = uploadImgFileName.lastIndexOf(".");
//				folderChrLastIndex = localEmrFilePathName.lastIndexOf(File.separator);
//
//				uploadEmrFileName = localEmrFilePathName.substring(folderChrLastIndex + 1, localEmrFilePathName.length());
//				uploadImgFileName = uploadImgFileName.substring(1, extChrLastIndex - 3) + (i+1)*100 + ".jpg";
//		}else {
//			
//					 folderChrLastIndex = localImgFilePathName.lastIndexOf(File.separator);
//					 uploadImgFileName = localImgFilePathName.substring(folderChrLastIndex + 1, localImgFilePathName.length());
//				
//					folderChrLastIndex = localEmrFilePathName.lastIndexOf(File.separator);
//					uploadEmrFileName = localEmrFilePathName.substring(folderChrLastIndex + 1, localEmrFilePathName.length());
//					uploadImgFileName = uploadEmrFileName.substring(0, uploadEmrFileName.lastIndexOf(".")) + ".jpg";
//			
//		}
//	/*	
//		FTPClient ftp = null; 			// FTP Client 객체 
//*/		FileInputStream fis = null;		// File Input Stream 
//		File uploadImgFile = new File(localImgFilePathName); 		// File 객체 
//		File uploadEmrFile = new File(localEmrFilePathName); 		// File 객체 
////		String url = "61.72.65.29"; 
////		String id = "winix";
////		String pwd = "rmfody"; 
////		String port = "21"; 
////		String url = commonConf.getProperty("FUKUDAGW.EMR.FTP.URL"); 
////		String id = commonConf.getProperty("FUKUDAGW.EMR.FTP.ID");
////		String pwd = commonConf.getProperty("FUKUDAGW.EMR.FTP.PW"); 
////		String port = commonConf.getProperty("FUKUDAGW.EMR.FTP.PORT");
//		int result = -1; 
//		try{ 
//		
//			
//			// IMG File Upload
//			ftp.changeWorkingDirectory(remoteFilePathRoot); // 작업 디렉토리 변경 
//			ftp.makeDirectory("00" + examInfo.getPatId().substring(0,3));
//			ftp.changeWorkingDirectory("00" + examInfo.getPatId().substring(0,3)); // 작업 디렉토리 변경 
//			ftp.makeDirectory("00" + examInfo.getPatId());
//			ftp.changeWorkingDirectory("00" + examInfo.getPatId()); // 작업 디렉토리 변경 
//			ftp.makeDirectory("SCAN");
//			ftp.makeDirectory("XML");
//			ftp.changeWorkingDirectory("SCAN"); // 작업 디렉토리 변경 
//			
//			ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//			try{ 
//				fis = new FileInputStream(uploadImgFile); 		// 업로드할 File 생성
//				logger.trace("local img file path[" + localImgFilePathName 
//						+ "/" + uploadImgFileName + "] , remoteImgFilePath[" + remoteImgFilePath + "]");
//				boolean isSuccess = ftp.storeFile(remoteImgFilePath + "/" + uploadImgFileName, fis); // File 업로드
//			if (isSuccess){ 
//				result = 1; // 성공
//				logger.trace("#008 Img File Upload Success");
//				} else { 
//					logger.error("#008 Img File Upload Fail");
//				} 
//			} catch(IOException ex){ 
//				logMap.put("level","오류");
//				logMap.put("item","심전도수신");
//				logMap.put("log_info","img 파일 전송 오류");
//				logMap.put("ip",examInfo.getIp());
//				logInfo.saveLog( logMap, session);
//				
//				logger.trace("IO Exception : " + ex.getMessage()); 
//			}
//			
//			// EMR File Upload
//			
//			if(exam_list!=null){
//				//다중매칭일경우 emr 처음 만 보내기
//				if(i==0) {
//					
//					ftp.changeWorkingDirectory("../XML"); // 작업 디렉토리 변경 
//					ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//					try{ 
//						fis = new FileInputStream(uploadEmrFile); 		// 업로드할 File 생성
//						logger.trace("local emr file path[" + localEmrFilePathName 
//								+ "/" + uploadEmrFileName + "] , remoteImgFilePath[" + remoteEmrFilePath + "]");
//						boolean isSuccess = ftp.storeFile(remoteEmrFilePath + "/" + uploadEmrFileName, fis); // File 업로드
//					if (isSuccess){ 
//						result = 1; // 성공
//						logger.trace("#008 Emr File Upload Success");
//						} else { 
//							logger.error("#008 Emr File Upload Fail");
//						} 
//					} catch(IOException ex){ 
//						logger.trace("IO Exception : " + ex.getMessage()); 
//						
//					}finally{ 
//						if (fis != null){ 
//							try{ fis.close(); // Stream 닫기 
//							return result; 
//							} catch(IOException ex){ 
//								logger.trace("IO Exception : " + ex.getMessage()); 
//							} 
//						} 
//					} 
//					
//				
//				}
//			}else{			
//				
//				ftp.changeWorkingDirectory("../XML"); // 작업 디렉토리 변경 
//				ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//				try{ 
//					fis = new FileInputStream(uploadEmrFile); 		// 업로드할 File 생성
//					logger.trace("local emr file path[" + localEmrFilePathName 
//							+ "/" + uploadEmrFileName + "] , remoteImgFilePath[" + remoteEmrFilePath + "]");
//					boolean isSuccess = ftp.storeFile(remoteEmrFilePath + "/" + uploadEmrFileName, fis); // File 업로드
//				if (isSuccess){ 
//					result = 1; // 성공
//					logger.trace("#008 Emr File Upload Success");
//					} else { 
//						logger.error("#008 Emr File Upload Fail");
//					} 
//				} catch(IOException ex){ 
//					logger.trace("IO Exception : " + ex.getMessage()); 
//					
//				}finally{ 
//					if (fis != null){ 
//						try{ fis.close(); // Stream 닫기 
//						return result; 
//						} catch(IOException ex){ 
//							logger.trace("IO Exception : " + ex.getMessage()); 
//						} 
//					} 
//				} 
//				
//			
//			}
//			
//	
//			
//		} catch (IOException e)		{ 
//			
//			logger.trace("IO:"+e.getMessage()); 
//			
//		}  return result; 
//		
//	}
//	
//	
//	//ftp 연결 해제
//	public FTPClient ftpDisConnetction(FTPClient ftp) throws IOException {
//		logger.trace("ftp 끊기");
//		ftp.logout(); // FTP Log Out 		
//		
//		if (ftp != null && ftp.isConnected()){ 
//			
//				ftp.disconnect(); // 접속 끊기
//	
//			
//		} 
//		
//		return ftp;
//		
//	}
//
//	
//	
//
//	
//	//파일 정보생성 	
//	public int ecgUpload(File file,String strFileName,FTPClient ftp) throws Exception
//	{ 
//		
//		logger.trace("ftp==진입");
//
////		String dir =(String) commonConf.get("EKG.FILE.UPLOAD.ECGDIR");
//
//		String selectDir =(String) commonConf.get("EKG.FILE.UPLOAD.DIR");
//		FileInputStream fis = null;		// File Input Stream 
//
//
//		int result = -1; 
//		try{ 
//		
//			
//		
//
//			ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//			try{ 
//				
//			String mode=commonConf.getProperty("EKG.HOSPITAL.MODE"); 
//					
//
//					//String fileName=file.getName();
//					//File uploadFile = new File(fileName); 		// File 객체 
//					
//					fis = new FileInputStream(selectDir + strFileName); 		// 업로드할 File 생성
//					String tempFileName = strFileName.replace("ecg","tmp");
//				
//					logger.trace("fis=="+selectDir + strFileName);
//					logger.trace("tempFileName=="+tempFileName);
//					boolean isSuccess = ftp.storeFile(tempFileName, fis); // File 업로드
//					fis.close();
//				logger.trace("is=="+isSuccess);
//				if (isSuccess){ 
//					ftp.rename(tempFileName, strFileName);
//					result = 1; // 성공
//					logger.trace("#008 Img File Upload Success");
//					} else { 
//						logger.error("#008 Img File Upload Fail");
//					} 
//			
//				if(mode.equals("STAND_ALONE")) {
//					//String fileName=file.getName();
//					//File uploadFile = new File(fileName); 		// File 객체 
//					Map<String,Object>dirMap= userDao.selectChkCopy(strFileName);
//					if(dirMap!=null) {
//						if(dirMap!=null || dirMap.get("copy_yn").equals("Y")) {
//							
//							if(dirMap.get("copy_dir_path")!=null) {
//								selectDir =(String) dirMap.get("copy_dir_path");
//								fis = new FileInputStream(selectDir + strFileName); 		// 업로드할 File 생성
//								 tempFileName = strFileName.replace("ecg","tmp");
//								
//								logger.trace("fis=="+selectDir + strFileName);
//								logger.trace("tempFileName=="+tempFileName);
//								 isSuccess = ftp.storeFile(tempFileName, fis); // File 업로드
//								fis.close();
//								logger.trace("is=="+isSuccess);
//								if (isSuccess){ 
//									ftp.rename(tempFileName, strFileName);
//									result = 1; // 성공
//									logger.trace("#008 Img File Upload Success");
//								} else { 
//									logger.error("#008 Img File Upload Fail");
//								} 
//							}
//							
//						}
//						
//						
//					}
//				
//					
//
//				}
//		
//		
//			} catch(IOException ex){ 
//				logger.trace("IO Exception : " + ex.getMessage()); 
//			}
//			
//		
//		
//			
//		} catch (IOException e)		{ 
//			
//			logger.trace("IO:"+e.getMessage()); 
//			
//		}  return result; 
//		
//	}
//	
//	
//
//	//파일 정보생성 	
//	public Map<String,Object> readingUpload(ExamInfoVo examInfo,FTPClient ftp, int i, HttpSession session) throws Exception
//	{ 
//		
//	/*	logger.trace("ftp 접속=="+examInfo.getExamSeq()+"||"+examInfo.getPatId());*/
//		String localImgFilePathName = examInfo.getImgFile().substring(0,examInfo.getImgFile().lastIndexOf(".")) + ".jpg";
//		String localEmrFilePathName = examInfo.getEmrFile();
//		/*
//		저장폴더 규칙 : 파일서버 URL 정보에,
//
//		/"00" + 환자등록번호 앞 3자리 / "00" + 환자등록번호 (8자리) / XML  <- XML 파일
//
//		/"00" + 환자등록번호 앞 3자리 / "00" + 환자등록번호 (8자리) / SCAN <- JPG 파일
//		
//		*/
//		
//		String remoteImgFilePath = dir+"/PATIENT/00" 
//				+ examInfo.getPatId().substring(0,3)
//				+ "/00" 
//				+ examInfo.getPatId()
//				+ "/SCAN";
//		
//		String remoteFilePathRoot =dir+"/PATIENT/";
//		
//		String remoteEmrFilePath =dir+"/PATIENT/00"
//				+ examInfo.getPatId().substring(0,3)
//				+ "/00" 
//				+ examInfo.getPatId()
//				+ "/XML";
//		int folderChrLastIndex=0;
//		String uploadImgFileName="";	
//		String uploadEmrFileName="";	
//				
//		logger.trace("#007 remote Img Path[" + remoteImgFilePath + "]");
//		logger.trace("#007 remote Emr Path[" + remoteEmrFilePath + "]");
//
//				 folderChrLastIndex = localImgFilePathName.lastIndexOf(File.separator);
//				 uploadImgFileName = localEmrFilePathName.substring(folderChrLastIndex, localEmrFilePathName.length());
//				 int extChrLastIndex = uploadImgFileName.lastIndexOf(".");
//				folderChrLastIndex = localEmrFilePathName.lastIndexOf(File.separator);
//
//				uploadEmrFileName = localEmrFilePathName.substring(folderChrLastIndex + 1, localEmrFilePathName.length());
//				uploadImgFileName = uploadImgFileName.substring(1, extChrLastIndex - 3) + (i+1)*100 + ".jpg";
//
//	/*	
//		FTPClient ftp = null; 			// FTP Client 객체 
//*/		FileInputStream fis = null;		// File Input Stream 
//		File uploadImgFile = new File(localImgFilePathName); 		// File 객체 
//		File uploadEmrFile = new File(localEmrFilePathName); 		// File 객체 
////		String url = "61.72.65.29"; 
////		String id = "winix";
////		String pwd = "rmfody"; 
////		String port = "21"; 
////		String url = commonConf.getProperty("FUKUDAGW.EMR.FTP.URL"); 
////		String id = commonConf.getProperty("FUKUDAGW.EMR.FTP.ID");
////		String pwd = commonConf.getProperty("FUKUDAGW.EMR.FTP.PW"); 
////		String port = commonConf.getProperty("FUKUDAGW.EMR.FTP.PORT");
//		Map<String,Object> resultMap = new HashMap<>(); 
//		try{ 
//		
//			
//			// IMG File Upload
//			ftp.changeWorkingDirectory(remoteFilePathRoot); // 작업 디렉토리 변경 
//			ftp.makeDirectory("00" + examInfo.getPatId().substring(0,3));
//			ftp.changeWorkingDirectory("00" + examInfo.getPatId().substring(0,3)); // 작업 디렉토리 변경 
//			ftp.makeDirectory("00" + examInfo.getPatId());
//			ftp.changeWorkingDirectory("00" + examInfo.getPatId()); // 작업 디렉토리 변경 
//			ftp.makeDirectory("SCAN");
//			ftp.makeDirectory("XML");
//			ftp.changeWorkingDirectory("SCAN"); // 작업 디렉토리 변경 
//			
//			ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//			try{ 
//				fis = new FileInputStream(uploadImgFile); 		// 업로드할 File 생성
//				logger.trace("local img file path[" + localImgFilePathName 
//						+ "/" + uploadImgFileName + "] , remoteImgFilePath[" + remoteImgFilePath + "]");
//				boolean isSuccess = ftp.storeFile(remoteImgFilePath + "/" + uploadImgFileName, fis); // File 업로드
//			if (isSuccess){ 
//				resultMap.put("result", 1); // 성공
//				logger.trace("#008 Img File Upload Success");
//				} else { 
//					logger.error("#008 Img File Upload Fail");
//				} 
//			} catch(IOException ex){ 
//				logMap.put("level","오류");
//				logMap.put("item","심전도수신");
//				logMap.put("log_info","img 파일 전송 오류");
//				logMap.put("ip",examInfo.getIp());
//				logInfo.saveLog( logMap, session);
//				
//				logger.trace("IO Exception : " + ex.getMessage()); 
//			}
//			
//			// EMR File Upload
//			
//			ftp.changeWorkingDirectory("../XML"); // 작업 디렉토리 변경 
//			ftp.setFileType(FTP.BINARY_FILE_TYPE); 	// 업로드 파일 타입 셋팅 
//			try{ 
//				resultMap.put("uploadEmrFileName",uploadEmrFileName);
//				fis = new FileInputStream(uploadEmrFile); 		// 업로드할 File 생성
//				logger.trace("local emr file path[" + localEmrFilePathName 
//						+ "/" + uploadEmrFileName + "] , remoteImgFilePath[" + remoteEmrFilePath + "]");
//				boolean isSuccess = ftp.storeFile(remoteEmrFilePath + "/" + uploadEmrFileName, fis); // File 업로드
//			if (isSuccess){ 
//				resultMap.put("result", 1); // 성공
//				logger.trace("#008 Emr File Upload Success");
//				} else { 
//					logger.error("#008 Emr File Upload Fail");
//				} 
//			} catch(IOException ex){ 
//				logger.trace("IO Exception : " + ex.getMessage()); 
//				
//			}finally{ 
//				if (fis != null){ 
//					try{ fis.close(); // Stream 닫기 
//					return resultMap; 
//					} catch(IOException ex){ 
//						logger.trace("IO Exception : " + ex.getMessage()); 
//					} 
//				} 
//			} 
//			
//		
//			
//		} catch (IOException e)		{ 
//			
//			logger.trace("IO:"+e.getMessage()); 
//			
//		}  return resultMap; 
//		
//	}
//
//}
