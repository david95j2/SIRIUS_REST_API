package com.example.sirius_restapi.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
//    private static List<WebSocketSession> list = new ArrayList<>();
    private static Queue<WebSocketSession> list = new ConcurrentLinkedQueue<>();  // thread-safe queue
//    private Process currentProcess = null;  // 현재 실행 중인 프로세스 참조를 저장

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }

//        log.info(String.valueOf(session.getUri()));
        log.info("Received payload: {}, from session URI: {}", payload, session.getUri());

    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info("Client connected: Session ID = {}", session.getId());
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Client disconnected: Session ID = {}, Status = {}", session.getId(), status);
        list.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Error occurred for session: {}", session.getId(), exception);
    }
}
