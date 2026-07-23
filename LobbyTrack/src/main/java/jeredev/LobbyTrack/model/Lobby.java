package jeredev.LobbyTrack.model;


import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public boolean verificarContraseña(String contraseñaIngresada){
        if(this.contraseña!=null || this.contraseña.trim().isEmpty()){
            return true;
        }
        if(contraseñaIngresada == null){return false;}

        return contraseñaIngresada.equals(contraseña);

    }

    public void agregarUsuarioAlaSala(User visitante){
        this.usuarios.add(visitante);
    }


    public boolean eliminarUsuario(String idSessionUsuario) {
        Iterator<User> iterator = this.usuarios.iterator();

        while (iterator.hasNext()) {
            User usuario = iterator.next();

            if (usuario.getIdSession().equals(idSessionUsuario)) {
                usuario.restablecerRol();
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public synchronized void reproducirMusica(){
        if(musicReproduciendo==null){
            throw new RuntimeException("No hay cancion reproduciendo");
        } else if (!estaEnPlay) {
            this.estaEnPlay = true;
        }


    }

    public synchronized void pausarMusica(){
        if(musicReproduciendo==null){
            throw new RuntimeException("No hay cancion reproduciendo");
        } else if (estaEnPlay) {
            this.estaEnPlay = false;
        }
    }

    public boolean salaVacia(){
        return this.usuarios.isEmpty();
    }
}
