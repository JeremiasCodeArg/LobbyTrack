package jeredev.LobbyTrack.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-audio")
                .setAllowedOriginPatterns("*");

    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // BROKER

        //Le dice al broker quetodo que comience con /topic es resposonsabilidad suya
        config.enableSimpleBroker("/topic", "/queue");

        //le dice al broker, si llega mensaje desde un celular y su destino empieza con /app
        //no lo repartas, este mensaje es para mi. Frenalo y busca en mis clases controller.
        config.setApplicationDestinationPrefixes("/app");

        config.setUserDestinationPrefix("/user");


    }

}
