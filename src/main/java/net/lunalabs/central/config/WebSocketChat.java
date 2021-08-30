//package net.lunalabs.central.config;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//import javax.websocket.EndpointConfig;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.RemoteEndpoint.Basic;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//
//@ServerEndpoint(value = "/echo.do", configurator = GetHttpSessionConfigurator.class)
//public class WebSocketChat {
//
//	private static final List<Session> sessionList = new ArrayList<Session>();
//	private static final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);
//    private HttpSession httpSession;
//	
//	public WebSocketChat() {
//		logger.info("웹소켓(서버) 객체생성");
//	}
//
//	@OnOpen
//	public void onOpen(Session session, EndpointConfig config) {
//		logger.info("웹소켓 세션 ID:" + session.getId());
//		try {
//			final Basic basic = session.getBasicRemote();
//			this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//			Map<String, Object> resultMap = (Map<String, Object>) httpSession.getAttribute("adminLoginMap");
//			if(resultMap != null) {
//				session.getUserProperties().put("adminLoginMap", resultMap);
//				basic.sendText("연결되었습니다.");
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		sessionList.add(session);
//	}
//
//	/*
//	 * 모든 사용자에게 메시지를 전달한다.
//	 * 
//	 * @param self
//	 * 
//	 * @param message
//	 */
//	public void sendAllSessionToMessage(String message) {
//		
//		System.out.println(message);
//		
//		try {
//			//JSONParser parser = new JSONParser();
//			//Object obj = parser.parse(message);
//			//JSONObject jsonObj = (JSONObject) obj;
//			
//			//long park_seq = (long) jsonObj.get("PARK_SEQ");
//			for (Session session : WebSocketChat.sessionList) {
//				//Map<String, Object> adminLoginMap = (Map<String, Object>) session.getUserProperties().get("adminLoginMap");
//				//System.out.println("adminLoginMap===" + adminLoginMap);
//				//int sessionParkSeq = Integer.parseInt(String.valueOf(adminLoginMap.get("PARK_SEQ")));
////				if(park_seq == sessionParkSeq){
////					session.getBasicRemote().sendText(message);
////				}
//				
//				System.out.println("들어가짐?");
//				session.getBasicRemote().sendText(message);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@OnMessage
//	public void onMessage(String message, Session session) throws ParseException {
//
//		try {
//			final Basic basic = session.getBasicRemote();
//			basic.sendText("to : " + message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		sendAllSessionToMessage(message);
//	}
//
//	@OnError
//	public void onError(Throwable e, Session session) {
//
//	}
//
//	@OnClose
//	public void onClose(Session session) {
//		logger.info("세션 ID : " + session.getId() + "를 끊었습니다.");
//		sessionList.remove(session);
//	}
//
//}
