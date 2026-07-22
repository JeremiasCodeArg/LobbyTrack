package jeredev.LobbyTrack.service;

import jeredev.LobbyTrack.enums.Rol;
import jeredev.LobbyTrack.model.Lobby;
import jeredev.LobbyTrack.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyService {
    private ConcurrentHashMap<String, Lobby> lobbies = new ConcurrentHashMap<String, Lobby>();

    private String POOL_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXZ";
    private int LENGHT_PASSWORD = 4;

    public Lobby crearLobby(String nombreAnfitrion, String sessionIdAnfitrion, String contraseña){
        String codigo;
        do {
            codigo = crearCodigoAlfaNumerico();
        }while(lobbies.containsKey(codigo));

        User usuario = new User(sessionIdAnfitrion, nombreAnfitrion, Rol.ANFITRION);


        Lobby lobby;
        if(contraseña != null && !contraseña.trim().isEmpty()){
            lobby = new Lobby(codigo, usuario, contraseña);
        }else{
            lobby = new Lobby(codigo, usuario);
        }
        lobbies.put(codigo, lobby);
        return lobby;

    }

    public void unirseLobby(String nombreVisitante, String sessionIdVisitante, String codigoSala,String contraseñaSala){
        if(!lobbies.containsKey(codigoSala)){
            throw new RuntimeException("Lobby no encontrado");
        }
        Lobby lobby = lobbies.get(codigoSala);

        if(!lobby.verificarContraseña(contraseñaSala)){
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        User usuarioVisitante = new User(sessionIdVisitante, nombreVisitante, Rol.VISITANTE);
        lobby.agregarUsuarioAlaSala(usuarioVisitante);



    }


    public void salirLobby(){


    }

    public void ponerPlay(){

    }



    private String crearCodigoAlfaNumerico(){
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < LENGHT_PASSWORD; i++){
            int indice = random.nextInt(POOL_CHAR.length());
            codigo.append(POOL_CHAR.charAt(indice));
        }
        return codigo.toString();
    }






}
