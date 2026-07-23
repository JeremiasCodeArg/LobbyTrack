package jeredev.LobbyTrack.Dtos;

import jeredev.LobbyTrack.model.Music;
import jeredev.LobbyTrack.model.User;

import java.util.List;

public record LobbyEstadoDTO(
        boolean estaEnPlay,
        int posicionActualEnSegundos,
        Music cancionActual,
        List<User> usuariosConectados
) {
}
