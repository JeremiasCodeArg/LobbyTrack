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
    private int posicionEnSegundos;
    private Long momentoDelUltimoPlay;
    private String contraseña;

    public Lobby(String idSala, User anfitrionInicial) {
        this.idSala = idSala;
        this.usuarios = new ArrayList<>();
        this.usuarios.add(anfitrionInicial); // El anfitrión entra automáticamente
        this.estaEnPlay = false;
        this.posicionEnSegundos = 0;
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

    public String devolverNombreUsuario(String idSession){
        if(this.usuarios==null || this.usuarios.isEmpty()){return null;}

        String nombreUsuario = usuarios.stream()
                .filter(usuario -> usuario.getIdSession().equals(idSession))
                .map(User::getNombre)
                .findFirst()
                .orElse("Alguien");
        return nombreUsuario;

    }

    public boolean eliminarUsuario(String idSessionUsuario) {
        return this.usuarios.removeIf(user -> user.getIdSession().equals(idSessionUsuario));
    }

    public synchronized void reproducirMusica(){
        if(musicReproduciendo==null){
            throw new RuntimeException("No hay cancion reproduciendo");
        } else if (!estaEnPlay) {
            this.estaEnPlay = true;
            this.momentoDelUltimoPlay = System.currentTimeMillis();
        }


    }

    public synchronized void pausarMusica(){
        if(musicReproduciendo==null){
            throw new RuntimeException("No hay cancion reproduciendo");
        } else if (estaEnPlay) {
            Long milisegundosTranscurridos = System.currentTimeMillis() - this.momentoDelUltimoPlay;
            this.posicionEnSegundos += (int) (milisegundosTranscurridos / 1000);
            this.estaEnPlay = false;
            this.momentoDelUltimoPlay = null;
        }
    }

    public int obtenerPosicionActual() {

        if (!this.estaEnPlay) {
            return this.posicionEnSegundos;
        }

        // Si está en Play, tenemos que hacer la deducción matemática
        long milisegundosTranscurridos = System.currentTimeMillis() - this.momentoDelUltimoPlay;
        int segundosTranscurridos = (int) (milisegundosTranscurridos / 1000);


        return this.posicionEnSegundos + segundosTranscurridos;
    }




    public boolean salaVacia(){
        return this.usuarios.isEmpty();
    }


}
