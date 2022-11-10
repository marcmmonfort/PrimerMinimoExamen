package Managers;

import Entities.*;
import Entities.ValueObjects.*;
import Entities.Exceptions.*;
import Main.*;
import Managers.*;
import Services.*;

import javax.servlet.http.Part;
import java.util.LinkedList;
import java.util.List;

public interface JuegoVirtualManager {

    // OPERACION 1: Crear un Juego.
    public void crearJuego(String juegoId, String juegoDescripcion, int numeroNivelesJuego);

    // OPERACION 2: Iniciar una Partida (por parte de un Usuario).
    public void iniciarPartida (String juegoId, String usuarioId) throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException;

    // OPERACION 3: Pedir el Nivel Actual de la Partida en la que está el Usuario introducido.
    public int pedirNivelJuegoDePartida (String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 4: Pedir la Puntuación Actual en una Partida (por parte de un Usuario)
    public int pedirPuntosDePartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 5: Pasar de Nivel en una Partida.
    public void pasarDeNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 6: Finalizar una Partida.
    public void finalizarPartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 7: Obtener los Usuarios que han jugado un cierto Juego (ordenados por Puntos (de mayor a menor).
    public List<Usuario> obtenerHistorialUsuariosDeJuego(String juegoId) throws JuegoIdNoExisteException;

    // OPERACION 8: Obtener las Partidas en las que ha jugado un Usuario.
    public List<Partida> obtenerPartidasUsuario(String usuarioId) throws UsuarioIdNoExisteException;

    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    public String obtenerInfoUsuarioJuego(String juegoId, String usuarioId);

    public int numUsuarios();

    public int numJuegos();

    public void crearUsuario(String usuario);
}
