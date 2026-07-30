package jeredev.LobbyTrack.service;

import jeredev.LobbyTrack.Dtos.LobbyEstadoDTO;
import jeredev.LobbyTrack.Dtos.UsuarioSalioDTO;
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

    public LobbyEstadoDTO unirseLobby(String nombreVisitante, String sessionIdVisitante, String idSala, String contraseñaSala) {
        verificarSiLobbyExiste(idSala);
        Lobby lobby = lobbies.get(idSala);

        if (!lobby.verificarContraseña(contraseñaSala)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        User usuarioVisitante = new User(sessionIdVisitante, nombreVisitante, Rol.VISITANTE);
        lobby.agregarUsuarioAlaSala(usuarioVisitante);

        return new LobbyEstadoDTO(
                lobby.isEstaEnPlay(),
                lobby.obtenerPosicionActual(),
                lobby.getMusicReproduciendo(),
                lobby.getUsuarios()
        );

    }



    public UsuarioSalioDTO salirLobby(String idSessionUsuario, String idSala){
        verificarSiLobbyExiste(idSala);

        Lobby lobby = lobbies.get(idSala);
        if(!lobby.eliminarUsuario(idSessionUsuario)){
            throw new IllegalArgumentException("Usuario no encontrado");
        };

        if(lobby.salaVacia()){
            lobbies.remove(idSala);
        }

        return  new UsuarioSalioDTO(
                idSessionUsuario
        );


    }

    public Lobby ponerPlay(String idSala){
        verificarSiLobbyExiste(idSala);
        Lobby  lobby = lobbies.get(idSala);

        lobby.reproducirMusica();
        return lobby;
    }

    public Lobby ponerPausa(String idSala){
        verificarSiLobbyExiste(idSala);
        Lobby  lobby = lobbies.get(idSala);

        lobby.pausarMusica();
        return lobby;
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


    private void verificarSiLobbyExiste(String idSala){
        if(!lobbies.containsKey(idSala)){
            throw new RuntimeException("Lobby no encontrado");
        }
    }



}
