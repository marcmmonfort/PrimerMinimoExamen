package Managers;

import Entities.*;
import Entities.Exceptions.*;
import Entities.ValueObjects.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.*;
import org.apache.log4j.Logger;

public class JuegoVirtualManagerImpl implements JuegoVirtualManager{

    HashMap<String,Juego> juegos; // Key = juegoId
    HashMap<String,Usuario> usuarios; // Key = usuarioId
    HashMap<String, Partida> partidasUsuarios; // Key = usuarioId

    private static JuegoVirtualManager instance;

    final static Logger logger = Logger.getLogger(JuegoVirtualManagerImpl.class);

    public static JuegoVirtualManager getInstance(){
        if (instance==null) instance = new JuegoVirtualManagerImpl();
        return instance;
    }

    public JuegoVirtualManagerImpl(){
        this.juegos = new HashMap<>();
        this.usuarios = new HashMap<>();
    }

    public int numUsuarios(){
        return this.usuarios.size();
    }

    public int numJuegos(){
        return this.juegos.size();
    }

    // OPERACION 1: Crear un Juego.
    public void crearJuego(String juegoId, String juegoDescripcion, int numeroNivelesJuego){
        logger.info("Se quiere crear un juego con ID "+juegoId+", Descripcion "+juegoDescripcion+" y "+numeroNivelesJuego+" Niveles.");
        Juego nuevoJuego = new Juego(juegoId, juegoDescripcion, numeroNivelesJuego);
        juegos.put(juegoId, nuevoJuego);
        logger.info("Juego creado con ID "+juegoId+", Descripcion "+juegoDescripcion+" y "+numeroNivelesJuego+" Niveles.");
    }

    // OPERACION 2: Iniciar una Partida (por parte de un Usuario).
    public void iniciarPartida (String juegoId, String usuarioId) throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        logger.info("Se quiere crear una partida del Usuario "+usuarioId+" en el Juego "+juegoId+".");
        Partida nuevaPartida = new Partida (juegoId, usuarioId);
        if (!this.juegos.containsKey(juegoId)){
            logger.warn("El juego "+juegoId+" no existe.");
            throw new JuegoIdNoExisteException();
        } else if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (partidasUsuarios.get(usuarioId).isPartidaEnCurso()) {
            logger.warn("El usuario "+usuarioId+" ya está en una partida.");
            throw new UsuarioIdYaEstaEnPartidaException();
        } else {
            partidasUsuarios.put(usuarioId, nuevaPartida);
            logger.info("Partida creada para el "+usuarioId+" en el Juego "+juegoId+".");
        }
    }

    // OPERACION 3: Pedir el Nivel Actual de la Partida en la que está el Usuario introducido.
    public int pedirNivelJuegoDePartida (String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException {
        logger.info("Se quiere obtener el nivel de la partida en la que está "+usuarioId+".");
        if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else {
            int nivelActual = partidasUsuarios.get(usuarioId).getNivelActual();
            logger.info("El usuario "+usuarioId+" está en el nivel "+nivelActual+".");
            return nivelActual;
        }
    }

    // OPERACION 4: Pedir la Puntuación Actual en una Partida (por parte de un Usuario)
    public int pedirPuntosDePartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException{
        logger.info("Se quiere obtener la puntuación de la partida en la que está "+usuarioId+".");
        if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else {
            int puntos = partidasUsuarios.get(usuarioId).getPuntosTotales();
            logger.info("El usuario "+usuarioId+" de momento tiene "+puntos+" puntos en la partida.");
            return puntos;
        }
    }

    // OPERACION 5: Pasar de Nivel en una Partida.
    public void pasarDeNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException {
        logger.info("Se quiere que el usuario "+usuarioId+" pase de nivel con "+puntosLogrados+" puntos a fecha de "+fechaCambioNivel+".");
        if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else {
            if (partidasUsuarios.get(usuarioId).getNivelActual() < juegos.get(partidasUsuarios.get(usuarioId).getJuegoId()).getNumeroNivelesJuego()){
                partidasUsuarios.get(usuarioId).aumentarNivel(fechaCambioNivel, puntosLogrados);
                logger.info("El usuario "+usuarioId+" ha pasado de nivel.");
            } else { // Si está en el último nivel, hay que acabar el juego.
                partidasUsuarios.get(usuarioId).finalizarPartida();
                Partida partidaGuardada = partidasUsuarios.get(usuarioId);
                usuarios.get(usuarioId).nuevaPartidaAcabada(partidaGuardada);
                logger.info("El usuario "+usuarioId+" ha finalizado la partida porque ha pasado todos los niveles.");
            }
        }
    }

    // OPERACION 6: Finalizar una Partida.
    public void finalizarPartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException{
        logger.info("Se quiere que el usuario "+usuarioId+" acabe una partida.");
        if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else {
            partidasUsuarios.get(usuarioId).finalizarPartida();
            Partida partidaGuardada = partidasUsuarios.get(usuarioId);
            usuarios.get(usuarioId).nuevaPartidaAcabada(partidaGuardada);
            logger.info("El usuario "+usuarioId+" ha finalizado la partida porque ha querido.");
        }
    }

    // OPERACION 7: Obtener los Usuarios que han jugado un cierto Juego (ordenados por Puntos (de mayor a menor).
    public List<Usuario> obtenerHistorialUsuariosDeJuego(String juegoId) throws JuegoIdNoExisteException {
        return null;
    }

    // OPERACION 8: Obtener las Partidas en las que ha jugado un Usuario.
    public List<Partida> obtenerPartidasUsuario(String usuarioId) throws UsuarioIdNoExisteException {
        return null;
    }

    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    public String obtenerInfoUsuarioJuego(String juegoId, String usuarioId) {
        return "Incompleto";
    }

    public void crearUsuario(String usuario){
        logger.info("Se quiere crear un usuario con ID "+usuario+".");
        Usuario nuevoUsuario = new Usuario(usuario);
        usuarios.put(usuario, nuevoUsuario);
        logger.info("Usuario creado con ID "+usuario+".");
    }
}
