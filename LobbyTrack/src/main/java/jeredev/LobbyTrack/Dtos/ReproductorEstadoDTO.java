package jeredev.LobbyTrack.Dtos;

public record ReproductorEstadoDTO(
        Boolean estaEnPlay,
        int posicionEnSegundos,
        String nombreUsuario
) {
}
