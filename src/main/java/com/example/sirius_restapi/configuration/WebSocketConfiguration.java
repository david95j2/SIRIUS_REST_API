package com.example.sirius_restapi.configuration;

import com.example.sirius_restapi.websocket.UserIdHandshakeInterceptor;
import com.example.sirius_restapi.websocket.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final WebSocketHandler chatHandler;
    private final WebSocketHandler  broadcastHandler;
    private final WebSocketHandler airSDKHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/test")
                .addInterceptors(new UserIdHandshakeInterceptor()) // 인터셉터 추가
                .setAllowedOrigins("*");
        registry.addHandler(broadcastHandler, "test/monitoring")
                .addInterceptors(new UserIdHandshakeInterceptor()) // 인터셉터 추가
                .setAllowedOrigins("*");
        registry.addHandler(airSDKHandler, "{id}/{drone_id}/airsdk/monitor")
                .addInterceptors(new UserIdHandshakeInterceptor()) // 인터셉터 추가
                .setAllowedOrigins("*");
    }
}
