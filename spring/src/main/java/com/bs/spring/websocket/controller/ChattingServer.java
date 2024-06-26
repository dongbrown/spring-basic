package com.bs.spring.websocket.controller;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅을 위한 Lombok 어노테이션
public class ChattingServer extends TextWebSocketHandler{
	private Map<String, WebSocketSession> clients = new HashedMap<>(); // 클라이언트 세션을 관리하는 맵
	
	@Autowired
	private ObjectMapper mapper; // JSON 처리 객체. Spring이 자동으로 주입
	
	// WebSocket 연결이 성공적으로 이루어졌을 때 호출되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 여기에 접속한 세션을 추가할 로직을 작성할 수 있음
	
	}

	// 클라이언트가 텍스트 메시지를 보낼 때 호출되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Message msg = mapper.readValue(message.getPayload(), Message.class); 
		// JSON 형식의 메시지를 Message 객체로 변환
		
		// 메시지 타입에 따라 분기 처리
		switch(msg.getType()) {
			case "open" : addClient(session, msg); break; // 클라이언트 추가
			case "msg" : sendMessage(msg); break; // 메시지 전송
			case "close" : break; // 클로즈 처리 (현재는 로직 없음)
		}
	}

	// WebSocket 연결이 닫혔을 때 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	    // CloseStatus는 웹소켓 프로토콜에서 연결이 종료될 때 사용되는 상태 코드
	    log.info("사용자 퇴장!"); // 로그 출력
	    
	    // 여기에 접속 해제된 세션을 제거할 로직을 작성할 수 있음
	    String id = ""; // 접속 해제된 사용자의 ID를 저장할 변수
	    
	    // clients 맵의 엔트리를 순회하며 해당 세션을 찾음
	    for (Map.Entry<String, WebSocketSession> client : clients.entrySet()) {
	        WebSocketSession cSession = client.getValue(); // 현재 엔트리의 WebSocketSession을 가져옴
	        if (session.equals(cSession)) { // 현재 세션이 접속 해제된 세션과 동일한지 확인
	            id = client.getKey(); // 동일하다면 해당 사용자의 ID를 변수에 저장
	            break; // 루프를 종료
	        }
	    }
	    
	    // 접속 해제된 사용자의 세션을 clients 맵에서 제거
	    clients.remove(id);
	    
	    // 접속 해제 메시지를 작성하고 전송
	    sendMessage(Message.builder().type("close").sender(id).build());
	    
	    // 참석 메시지를 처리하는 메서드 호출
	    attendMessage();
	}

	
	// 새로운 클라이언트를 추가하는 메소드
	private void addClient(WebSocketSession session, Message msg) {
		clients.put(msg.getSender(), session); // 클라이언트 세션을 맵에 저장
		sendMessage(msg); // 메시지 전송
		attendMessage();
	}

	// 새로운 클라리언트 추가됐을 때 메세지
	private void attendMessage() {
		try {
			Message msg = Message.builder()
					.type("attend").msg(mapper.writeValueAsString(clients.keySet()))
					.build();
			sendMessage(msg);
		}catch(Exception e) {
			
		}
	}
	
	// 모든 클라이언트에게 메시지를 보내는 메소드
	private void sendMessage(Message msg) {
		for(Map.Entry<String, WebSocketSession> client:clients.entrySet()) { // 모든 클라이언트를 순회
			WebSocketSession cSession = client.getValue();
			try {
				String message = mapper.writeValueAsString(msg); // Message 객체를 JSON 문자열로 변환
				cSession.sendMessage(new TextMessage(message)); // 클라이언트에게 메시지 전송
			}catch (Exception e) {
				e.printStackTrace(); // 예외 처리
			}
		}
	}
}
