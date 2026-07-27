package jeredev.LobbyTrack.Dtos;

import jeredev.LobbyTrack.enums.Rol;

public record UsuarioIngresoDTO(
        String idSession,
        String nombre,
        Rol rol
) {
}
