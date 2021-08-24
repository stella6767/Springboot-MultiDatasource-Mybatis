package net.lunalabs.central.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.domain.mysql.MeasureData;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.utills.MParsing;

@Slf4j
@RequiredArgsConstructor
@EnableAsync
@Service
public class AsyncService {

	private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

	
	@Qualifier("MysqlMeasureDataMapper")
	private final MeasureDataMapper mapper;
	
	
	@Async // 비동기로 동작하는 메소드
	public void csSocketStart() {

		logger.info("CsSocketStart!!!!!");

		try {

			ServerSocketChannel serverSocketChannel = null;
			SocketChannel socketChannel = null;

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(5051)); // socket().

			boolean bLoop = true;

			logger.info("CsSocketStart2!!!!!");

			while (bLoop) {
				logger.info("CsSocketStart3!!!!!");

				try {
					logger.info("CsSocketStart4!!!!!");

					socketChannel = serverSocketChannel.accept(); // 이 부분에서 연결이 될때까지 블로킹

					socketChannel.configureBlocking(true); // 블록킹 방식

					logger.info("[ESMLC Listen[" + "] Socket Accept EsmlcIfWorkThread Start");
					serverSocketThread(socketChannel);

				} catch (Exception e) {
					logger.debug("AsynchronousCloseException 터짐");
					socketChannel.close();

					e.printStackTrace();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// Socket으로 단순히 HL7 데이터 읽기
	public void serverSocketThread(SocketChannel schn) throws IOException {

		logger.info("read socket data");

		Charset charset = Charset.forName("UTF-8");

		String result = "";

		byte[] readByteArr;

		// Client로부터 글자 받기
		ByteBuffer readBuf = ByteBuffer.allocate(10240);
		schn.read(readBuf); // 클라이언트로부터 데이터 읽기
		readBuf.flip();

		readByteArr = new byte[readBuf.remaining()];
		readBuf.get(readByteArr); // 데이터 읽기
		result = result + new String(readByteArr, Charset.forName("UTF-8")); // 어차피 여기서 계속 더하니까.

		HL7DataToJson(result);
//         log.info("Received Data : " + charset.decode(readBuf).toString());
		log.info("Received Data : " + result);

		//schn.close();

	}

	public void HL7DataToJson(String HL7Data) {

		String[] splitEnterArray = HL7Data.split("[\\r\\n]+"); //개행문자 기준으로 1차 파싱
		
		String[] headerArray = splitEnterArray[0].split("[|]");
		String trigger = headerArray[8];
		log.info("trigger: " + trigger);

		switch (trigger) {
		case "ORU^R01":
			
			measureDataParsing(splitEnterArray);
			
			break;
			
		case "RQI^I02":
			
			
			break;	

		default:
			break;
		}
		

	}
	
	
	public void measureDataParsing(String[] array) {  //5개의 parame이 오면 5번 insert
		
		
		String[] obrArray = array[2].split("[|]");				
		String sid = obrArray[2];
		
		String startTime = obrArray[7];
		String endTime = obrArray[8];
		
		
		log.info("session id: "  +sid);
		log.info("startTime: " + startTime);
		log.info("endTime: " + endTime);
		
		
		//이걸 한번에 저장해야 되나
		for (int i = 3; i < array.length; i++) { //3부터 OBX param 시작
			log.info("개행문자 기준으로 1차 파싱: " + array[i]);
			
			String[] splitSecondArray = array[i].split("[|]");
			
			MeasureData measureData = null;
			
			for(int j=0; j<splitSecondArray.length; j++) {
				log.info("| 기준으로 2차 파싱: " + splitSecondArray[j]);	
					
				 measureData = MeasureData.builder()
					.parame(splitSecondArray[3])
					.value(splitSecondArray[5])
					.endTime(MParsing.stringToDate(startTime))
					.startTime(MParsing.stringToDate(endTime))
					.sid(sid)
					.build();
				
			}
			mapper.save(measureData);				

															
		}	
		
	}
	
	
	
	
	
	

//	@Async
//	public void FTPServiece() {
//		
//		boolean isFTPRunning = true;
//		
//		while(isFTPRunning) {
//			
//			
//			
//			
//		}
//		
//	}

}
