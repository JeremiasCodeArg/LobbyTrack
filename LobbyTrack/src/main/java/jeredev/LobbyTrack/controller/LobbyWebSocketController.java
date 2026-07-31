package jeredev.LobbyTrack.controller;

import jeredev.LobbyTrack.Dtos.*;
import jeredev.LobbyTrack.enums.Rol;
import jeredev.LobbyTrack.model.Lobby;
import jeredev.LobbyTrack.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LobbyWebSocketController {

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/lobby/salir")
    public void salirLobby(SalirLobbyRequest request, SimpMessageHeaderAccessor headerAccessor){

        String sessionId = headerAccessor.getSessionId();

        UsuarioSalioDTO  usuarioSalioDTO = lobbyService.salirLobby(sessionId, request.idSala());

        messagingTemplate.convertAndSend("/topic/lobby/" + request.idSala(), usuarioSalioDTO);

    }

    @MessageMapping("/lobby/unirse")
    public void unirseLobby(UnirseLobbyDTO lobby, SimpMessageHeaderAccessor headerAccessor){
        String usuarioIdSession = headerAccessor.getSessionId();

        LobbyEstadoDTO lobbyActualizado = lobbyService.unirseLobby(
                lobby.nombreUsuario(),
                usuarioIdSession,
                lobby.idSala(),
                lobby.contraseña());


        UsuarioIngresoDTO usuarioNuevo = new UsuarioIngresoDTO(
                usuarioIdSession,
                lobby.nombreUsuario(),
                Rol.VISITANTE);

        messagingTemplate.convertAndSend("/topic/lobby/" + lobby.idSala(), usuarioNuevo);

        LobbyEstadoDTO estadoInicial = new LobbyEstadoDTO(
                lobbyActualizado.estaEnPlay(),
                lobbyActualizado.posicionActualEnSegundos(),
                lobbyActualizado.cancionActual(),
                lobbyActualizado.usuariosConectados()
        );



        /*
            Creamos header falsos, seria directo con spring segurity, pero para ahorrar tiempo lo hago asi
            lo hacemos asi, para poder enviar un mensaje privado al usuario.
         */

        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        accessor.setSessionId(usuarioIdSession);
        accessor.setLeaveMutable(true); //Esto es para que sea modificable, ya que creamos un sobre a mano en spring, se sella.


        messagingTemplate.convertAndSendToUser(
                usuarioIdSession,
                "queue/lobby/estado",
                estadoInicial,
                accessor.getMessageHeaders()
        );

    }


    @MessageMapping("/lobby/pausar")
    public void pausarMusica(LobbyRequestDTO lobby , SimpMessageHeaderAccessor headerAccessor){

        Lobby lobbyActual = lobbyService.ponerPausa(lobby.id());
        String nombreUsuario = lobbyActual.devolverNombreUsuario(headerAccessor.getSessionId());

        ReproductorEstadoDTO reproductor = new ReproductorEstadoDTO(
                lobbyActual.isEstaEnPlay(),
                lobbyActual.getPosicionEnSegundos(),
                nombreUsuario
        );

        messagingTemplate.convertAndSend("/topic/lobby/" + lobby.id(), reproductor);

    }

    @MessageMapping("lobby/play")
    public void ponerPlay(LobbyRequestDTO lobby, SimpMessageHeaderAccessor headerAccessor){

        Lobby lobbyActual = lobbyService.ponerPlay(lobby.id());
        String nombreUsuario = lobbyActual.devolverNombreUsuario(headerAccessor.getSessionId());

        ReproductorEstadoDTO reproductor = new ReproductorEstadoDTO(
                lobbyActual.isEstaEnPlay(),
                lobbyActual.getPosicionEnSegundos(),
                nombreUsuario
        );
        messagingTemplate.convertAndSend("/topic/lobby/" + lobby.id(), reproductor);


    }

    @MessageMapping("/lobby/ponerMusica")
    public void ponerMusica(cargarMusicaRequest musicaRequest ,SimpMessageHeaderAccessor headerAccessor){



        Lobby lobbyActualizado = lobbyService.agregarMusica(
                musicaRequest.idSala(),
                musicaRequest.titulo(),
                musicaRequest.artista(),
                musicaRequest.url(),
                musicaRequest.duracionEnsegundos()
        );

        String nombreUsuario = lobbyActualizado.devolverNombreUsuario(headerAccessor.getSessionId());

        MusicaCargadaDTO musicaCargada = new MusicaCargadaDTO(
                lobbyActualizado.getMusicReproduciendo(),
                lobbyActualizado.isEstaEnPlay(),
                lobbyActualizado.getPosicionEnSegundos(),
                nombreUsuario
        );

        messagingTemplate.convertAndSend("/topic/lobby/" + musicaRequest.idSala(), musicaCargada);

    }

}
