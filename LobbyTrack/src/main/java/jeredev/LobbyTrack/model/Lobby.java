package jeredev.LobbyTrack.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {
    private int idSala;
    private List<User> usuarios;
    private Music musicReproduciendo;
    private boolean estaEnPlay;
    private int segundoActual;
}
