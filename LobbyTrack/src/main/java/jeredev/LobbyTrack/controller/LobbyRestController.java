package jeredev.LobbyTrack.controller;

import jeredev.LobbyTrack.Dtos.CrearLobbyRequest;
import jeredev.LobbyTrack.Dtos.LobbyEstadoDTO;
import jeredev.LobbyTrack.model.Lobby;
import jeredev.LobbyTrack.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyRestController {

    @Autowired
    private LobbyService lobbyService;

    @PostMapping
    public ResponseEntity<LobbyEstadoDTO> crearLobby(@RequestBody CrearLobbyRequest request) {
        // Llamamos al mismo servicio de siempre
        Lobby lobby = lobbyService.crearLobby(request.nombreUsuario(), "ANFITRION_SYSTEM_SESSION", request.contraseña());

        // Transformamos a DTO (esto lo podés hacer con un mapper o manual)
        LobbyEstadoDTO response = new LobbyEstadoDTO(
                lobby.isEstaEnPlay(),
                lobby.getPosicionEnSegundos(),
                lobby.getMusicReproduciendo(),
                lobby.getUsuarios()
        );

        return ResponseEntity.ok(response);
    }
}

