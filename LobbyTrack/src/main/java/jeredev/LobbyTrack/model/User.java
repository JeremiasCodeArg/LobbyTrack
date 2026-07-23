package jeredev.LobbyTrack.model;


import jeredev.LobbyTrack.enums.Rol;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private String idSession;
    private String nombre;
    private Rol rol;

    public User (String idSession, String nombre, Rol rol){
        this.idSession = idSession;
        this.nombre = nombre;
        this.rol = rol;
    }

    private void restablecerRol(){
        this.rol = null;
    }
}
