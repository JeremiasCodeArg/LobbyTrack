package jeredev.LobbyTrack.Dtos;

public record cargarMusicaRequest(
        String idSala,
        String titulo,
        String artista,
        String url,
        int duracionEnsegundos
) {
}
