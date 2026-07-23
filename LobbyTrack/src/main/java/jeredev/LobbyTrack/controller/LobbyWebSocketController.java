package jeredev.LobbyTrack.controller;

import jeredev.LobbyTrack.Dtos.SalirLobbyRequest;
import jeredev.LobbyTrack.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyWebSocketController {

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/lobby/salir")
    public void salirLobby(SalirLobbyRequest request, SimpMessageHeaderAccessor headerAccessor){

    }

}
