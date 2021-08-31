package net.lunalabs.central.config.ftp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


import lombok.RequiredArgsConstructor;
import net.lunalabs.central.service.FtpService;

@Configuration
@RequiredArgsConstructor
public class FtpServerMain { 


	private final FtpService ftpService;	
	private static final Logger logger = LoggerFactory.getLogger(FtpServerMain.class);

	
	@PostConstruct
	public void ftpServerStart(){
		
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory factory = new ListenerFactory();
		// set the port of the listener
		factory.setPort(21);
				
		//factory.set
		
		
		
		// replace the default listener
		serverFactory.addListener("default", factory.createListener());
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//		userManagerFactory.setFile(new File("conf/myusers.properties"));
//		serverFactory.setUserManager(userManagerFactory.createUserManager());
		
		
		
		String[] strAccountList = "FX7102DATA/FUKUDADENSHI,mediana/1234,kyu/1234".split(",");
		
		UserManager um = userManagerFactory.createUserManager();
		
		for(String AccountInfo : strAccountList) {
			
			int idx=AccountInfo.indexOf("/");
			
			String id= AccountInfo.substring(0,idx);
			String pwd= AccountInfo.substring(idx+1);
		/*	logger.trace("id=="+id+"||pw=="+pwd);*/
			
			BaseUser user = new BaseUser();
			user.setName(id);
			user.setPassword(pwd);
			user.setEnabled(true);
			logger.trace("dir==C:\\kangminkyu\\FTPfileUpload");
			
			
			user.setHomeDirectory("C:\\kangminkyu\\FTPfileUpload"); //요기가 FTP server file root경로
			
			java.util.List<Authority> authorities = new java.util.ArrayList<Authority>();
	        authorities.add(new WritePermission());
			user.setAuthorities(authorities);
			
	
			
			try {
				um.save(user);
			} catch (FtpException e1) { 
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		serverFactory.setUserManager(um);
		
		Map<String, Ftplet> m = new HashMap<String, Ftplet>();
	    m.put("miaFtplet", new Ftplet()
	    {
	        @Override
	        public void init(FtpletContext ftpletContext) throws FtpException {
	            logger.info("init");
	            logger.info("Thread #" + Thread.currentThread().getId());
	            
	           
	        }

	        @Override
	        public void destroy() {
	            logger.info("destroy");
	            logger.info("Thread #" + Thread.currentThread().getId());
	        }

	        @Override
	        public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException
	        {
	            logger.info("beforeCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine());
	            logger.info("Thread #" + Thread.currentThread().getId());

	            //do something
	            return FtpletResult.DEFAULT;//...or return accordingly
	        }

	        @Override
	        public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException
	        {
	            logger.info("afterCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine() + " | " + reply.getMessage() + " : " + reply.toString());
	            String strFileName = request.getArgument();
	            String strMsg = reply.getMessage();
	        	logger.info("afterCommand " + strFileName + "::::::" + strMsg);
	        	
	        	
	            if (strMsg.equals("Transfer complete.")) {
//	            if (strMsg.equals("Requested file action okay, file renamed.")) {
					
					String ip=	session.getClientAddress().getAddress().toString();
					
					try{
						
						ftpService.ftpFileDBSave("C:\\kangminkyu\\FTPfileUpload", strFileName); //일단은 하드코딩

					}catch(Exception e)	{
						
					}
	            }
	            
//	            logger.trace("Thread #" + Thread.currentThread().getId());

	            //do something
	            return FtpletResult.DEFAULT;//...or return accordingly
	        }

	        @Override
	        public FtpletResult onConnect(FtpSession session) throws FtpException, IOException
	        {
	            logger.info("onConnect " + session.getUserArgument() + " : " + session.toString());
	            logger.info("Thread #" + Thread.currentThread().getId());

	            //do something
	            return FtpletResult.DEFAULT;//...or return accordingly
	        }

	        @Override
	        public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException
	        {
	            logger.info("onDisconnect " + session.getUserArgument() + " : " + session.toString());
	            logger.info("Thread #" + Thread.currentThread().getId());

	            //do something
	            return FtpletResult.DEFAULT;//...or return accordingly
	        }
	        
	        
	        

	    });
	    serverFactory.setFtplets(m);
		// start the server
		FtpServer server = serverFactory.createServer(); 
		try {
			server.start();
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}
	
	

}
