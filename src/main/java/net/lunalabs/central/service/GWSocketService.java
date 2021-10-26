package net.lunalabs.central.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.central.config.SocketProperties;
import net.lunalabs.central.domain.measuredata.MeasureData;
import net.lunalabs.central.domain.patient.Patient;
import net.lunalabs.central.mapper.mysql.MeasureDataMapper;
import net.lunalabs.central.mapper.mysql.PatientMapper;
import net.lunalabs.central.utills.MParsing;

@Slf4j
@RequiredArgsConstructor
@Service
public class GWSocketService {

	private static final Logger logger = LoggerFactory.getLogger(GWSocketService.class);

	private final SocketThreadService socketThreadService;
	private final SocketProperties socketProperties;

	@Async // 비동기로 동작하는 메소드
	public void csSocketStart() {

		

		try {

			ServerSocketChannel serverSocketChannel = null; // ServerSocketChannel은 하나

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(socketProperties.port)); // socket().

			boolean bLoop = true;

			logger.info("CsServerSocketStart!!!!! " + socketProperties.port);

			while (bLoop) {
				logger.info("CsServerSocket connect 대기");

				try {
					SocketChannel schn = null;
					
					schn = serverSocketChannel.accept(); // 이 부분에서 연결이 될때까지 블로킹
					schn.configureBlocking(true); // 블록킹 방식
					//여기서 계속 한 소켓당 스레드로 분기해서 맡음.
					logger.info("CsServerSocket connected " + socketProperties.port + " port");

					//socketThreadService.serverSocketThread(schn);
					socketThreadService.readSocketData(schn);

				} catch (Exception e) {
					//logger.debug("AsynchronousCloseException 터짐");
					//socketChannel.close();
					
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


}