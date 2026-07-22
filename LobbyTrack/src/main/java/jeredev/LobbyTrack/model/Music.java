package jeredev.LobbyTrack.model;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    private String titulo;
    private String artista;
    private int duracionEnSegundos;
    private String urlAudio;
    private String urlPortada;
}
