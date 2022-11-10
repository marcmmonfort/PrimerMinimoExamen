import Entities.*;
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
        this.jvm.crearJuego("GTA", "Juego de vida real", 10);
        this.jvm.crearJuego("GT", "Juego de conducción", 5);

        this.jvm.crearUsuario("Marc");
        this.jvm.crearUsuario("Victor");
        this.jvm.crearUsuario("Eloi");
    }

    // ----------------------------------------------------------------------------------------------------

    // (2) MÉTODO "tearDown" QUE LIBERA LOS RECURSOS.

    @After
    public void tearDown() {
        this.jvm = null;
    }

    // ----------------------------------------------------------------------------------------------------

    // (3) MÉTODO DE TEST PARA CADA UNA DE LAS FUNCIONES DEL CÓDIGO.

    // NO IMPLEMENTADO.

}
