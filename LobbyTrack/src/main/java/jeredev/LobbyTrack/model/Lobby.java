package jeredev.LobbyTrack.model;


import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {
    private String idSala;
    private List<User> usuarios;
    private Music musicReproduciendo;
    private boolean estaEnPlay;
    private int segundoActual ;
    private String contraseña;

    public Lobby(String idSala, User anfitrionInicial) {
        this.idSala = idSala;
        this.usuarios = new ArrayList<>();
        this.usuarios.add(anfitrionInicial); // El anfitrión entra automáticamente
        this.estaEnPlay = false;
        this.segundoActual = 0;
        this.musicReproduciendo = null;
    }
    public Lobby (String idSala, User anfitrion, String contraseña){
        this(idSala, anfitrion);
        this.contraseña = contraseña;
    }
}
