package jeredev.LobbyTrack.model;


import jeredev.LobbyTrack.enums.Rol;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String idSession;
    private String nombre;
    private Rol rol;
}
