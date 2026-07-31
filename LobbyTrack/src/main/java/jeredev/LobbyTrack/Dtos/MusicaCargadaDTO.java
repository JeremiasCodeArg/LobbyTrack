package jeredev.LobbyTrack.Dtos;

import jeredev.LobbyTrack.model.Music;

public record MusicaCargadaDTO(
        Music musicaReproduciendo,
        boolean estaEnPlay,
        int posicionEnSegundos,
        String nombreUsuario //Quien cargo la musica
) {
}
