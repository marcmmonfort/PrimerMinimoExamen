import Entities.*;
import Entities.Exceptions.JuegoIdNoExisteException;
import Entities.Exceptions.UsuarioIdNoEstaEnPartidaException;
import Entities.Exceptions.UsuarioIdNoExisteException;
import Entities.Exceptions.UsuarioIdYaEstaEnPartidaException;
import Entities.ValueObjects.InfoPartida;
import Entities.ValueObjects.Partida;
import Main.*;
import Managers.*;
import Services.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import org.apache.log4j.Logger;

public class JuegoVirtualImplTest {

    final static Logger logger = Logger.getLogger(JuegoVirtualManagerImpl.class);

    JuegoVirtualManager jvm;

    @Before
    public void setUp() {
        this.jvm = new JuegoVirtualManagerImpl();

        this.jvm.crearJuego("FIFA", "Juego de fútbol", 2);
        this.jvm.crearJuego("GTA", "Juego de vida real", 4);
        this.jvm.crearJuego("Gran Turismo", "Juego de conducción", 5);

        this.jvm.crearUsuario("Marc");
        this.jvm.crearUsuario("Victor");
        this.jvm.crearUsuario("Eloi");
    }

    // ----------------------------------------------------------------------------------------------------

    @After
    public void tearDown() {
        this.jvm = null;
    }

    // ----------------------------------------------------------------------------------------------------

    @Test
    public void testCrearJuego() {
        // Creamos un juego.
        this.jvm.crearJuego("Fall Guys", "Juego de retos", 3);
        Assert.assertEquals(4, this.jvm.numJuegos());

        // Creamos otro juego.
        this.jvm.crearJuego("NBA", "Juego de baloncesto", 4);
        Assert.assertEquals(5, this.jvm.numJuegos());
    }

    @Test
    public void testIniciarPartida() throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Comprobamos que Marc no tiene ninguna partida iniciada.
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());

        // Iniciamos una partida.
        this.jvm.iniciarPartida("FIFA", "Marc");
        Assert.assertEquals(true, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
        Assert.assertEquals("FIFA", this.jvm.damePartidaUsuario("Marc").getJuegoId());
        Assert.assertEquals("Marc", this.jvm.damePartidaUsuario("Marc").getUsuarioId());

        // Probamos las 3 Exceptions posibles.
        Assert.assertThrows(JuegoIdNoExisteException.class, ()-> this.jvm.iniciarPartida("Plane Simulator", "Marc"));
        Assert.assertThrows(UsuarioIdNoExisteException.class, ()-> this.jvm.iniciarPartida("FIFA", "Carlos"));
        Assert.assertThrows(UsuarioIdYaEstaEnPartidaException.class, ()-> this.jvm.iniciarPartida("FIFA", "Marc"));
    }

    @Test
    public void test_PedirNiveles_PedirPuntos_PasarDeNivel() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Iniciamos la partida: entramos en el nivel 1 de los 4 que tiene GTA.
        this.jvm.iniciarPartida("GTA", "Marc");
        Assert.assertEquals(1, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(50, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 2.
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        Assert.assertEquals(2, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(100, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 3.
        this.jvm.pasarDeNivel("Marc", -50, "02/01/2022");
        Assert.assertEquals(3, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(50, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 4.
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        Assert.assertEquals(4, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(150, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos de nivel (finaliza la partida).
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
    }

    @Test
    public void testFinalizarPartida() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Iniciamos la partida: entramos en el nivel 1 de los 4 que tiene GTA.
        this.jvm.iniciarPartida("GTA", "Marc");

        // Pasamos al nivel 2.
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");

        // Pasamos al nivel 3.
        this.jvm.pasarDeNivel("Marc", -50, "02/01/2022");

        // Forzamos que se acabe la partida.
        Assert.assertEquals(0, this.jvm.dameUsuario("Marc").getPartidasJugadas().size());
        Assert.assertEquals(true, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
        this.jvm.finalizarPartida("Marc");
        Assert.assertEquals(1, this.jvm.dameUsuario("Marc").getPartidasJugadas().size());
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
    }

    @Test
    public void test_ObtenerUsuariosJuego_ObtenerPartidasUsuario() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Hacemos que 3 usuarios jueguen 3 partidas de un cierto juego y las acaben.
        this.jvm.iniciarPartida("FIFA", "Marc");
        Assert.assertEquals(0,this.jvm.obtenerPartidasUsuario("Marc").size());
        this.jvm.pasarDeNivel("Marc", 450, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 400, "02/01/2022");
        Assert.assertEquals(1,this.jvm.obtenerPartidasUsuario("Marc").size());
        this.jvm.iniciarPartida("FIFA", "Eloi");
        Assert.assertEquals(0,this.jvm.obtenerPartidasUsuario("Eloi").size());
        this.jvm.pasarDeNivel("Eloi", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Eloi", 100, "03/01/2022");
        Assert.assertEquals(1,this.jvm.obtenerPartidasUsuario("Eloi").size());

        // Comprobamos que obtenemos correctamente el historial de usuarios que han jugado a un juego (ordenados).
        Assert.assertEquals(2, this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").size());
        Assert.assertEquals("Marc",this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").get(0).getUsuarioId());
        Assert.assertEquals("Eloi", this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").get(1).getUsuarioId());

        // Hacemos que uno de los dos usuarios juegue otra partida a otro juego.
        this.jvm.iniciarPartida("GTA", "Marc");
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 150, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 200, "02/01/2022");
        Assert.assertEquals(2,this.jvm.obtenerPartidasUsuario("Marc").size());

        // Comprobamos que obtenemos las partidas que ha jugado un cierto usuario.
        Assert.assertEquals("FIFA",this.jvm.obtenerPartidasUsuario("Marc").get(0).getJuegoId());
        Assert.assertEquals("GTA",this.jvm.obtenerPartidasUsuario("Marc").get(1).getJuegoId());
    }

    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    // ESTRUCTURA: public InfoPartida obtenerInfoUsuarioJuego(String juegoId, String usuarioId);
    // EXCEPTIONS: -

    @Test
    public void testObtenerInfoUsuarioJuego() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Creamos y finalizamos una partida de un juego.
        this.jvm.iniciarPartida("GTA", "Marc");
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 150, "03/01/2022");
        this.jvm.pasarDeNivel("Marc", 200, "04/01/2022");

        // Pedimos la información de esta partida.
        Assert.assertEquals("Marc", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getUsuarioId());
        Assert.assertEquals("GTA", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getJuegoId());
        Assert.assertEquals("01/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(0).getFechaNivel());
        Assert.assertEquals("02/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(1).getFechaNivel());
        Assert.assertEquals("03/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(2).getFechaNivel());
        Assert.assertEquals("04/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(3).getFechaNivel());
    }
}
