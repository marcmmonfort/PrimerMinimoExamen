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

public class ObjetoManagerImplTest {

    final static Logger logger = Logger.getLogger(ObjetoManagerImpl.class);

    ObjetoManager om;

    // ----------------------------------------------------------------------------------------------------

    // (1) MÉTODO "setUp" QUE INICIALIZA LA ESTRUCTURA DE DATOS.

    @Before
    public void setUp() {
        this.om = new ObjetoManagerImpl();

        // public int registerUser(String username, String userSurname, String birthDate, String email, String password);
        this.om.registerUser("Marc", "Moran", "28/10/2001", "marcmoran@gmail.com", "28102001");
        this.om.registerUser("Victor", "Fernandez", "13/06/2001", "victorfernandez@gmail.com", "13062001");
        this.om.registerUser("Eloi", "Moncho", "28/08/2001", "eloimoncho@gmail.com", "28082001");

        // public void addObjectToShop(String id, String name, String description, double coins);
        this.om.addObjectToShop("A001", "FCB", "Primera Equipación Fan", 30);
        this.om.addObjectToShop("B001", "ATM", "Primera Equipación Jugador", 35);
        this.om.addObjectToShop("C001", "BRUGGE", "Primera Equipación Fan (Ferran Jutgol)", 40);
    }

    // ----------------------------------------------------------------------------------------------------

    // (2) MÉTODO "tearDown" QUE LIBERA LOS RECURSOS.

    @After
    public void tearDown() {
        this.om = null;
    }

    // ----------------------------------------------------------------------------------------------------

    // (3) MÉTODO DE TEST PARA CADA UNA DE LAS FUNCIONES DEL CÓDIGO.

    // TEST 1: Registrar un usuario.
    // public int registerUser(String username, String userSurname, String birthDate, String email, String password);
    // "0" se puede, "1" ya hay un usuario con ese email.

    @Test
    public void testRegisterUser() {
        logger.info("Se añade un usuario correctamente.");
        Assert.assertEquals(3, this.om.numUsuarios());
        int verificador = this.om.registerUser("Alba", "Serra", "23/06/2001", "albaserra@gmail.com", "23062001");
        Assert.assertEquals(4, this.om.numUsuarios());
        Assert.assertEquals(0, verificador);

        logger.info("Ya hay un usuario con ese email.");
        verificador = this.om.registerUser("El", "Oimoncho", "16/11/2001", "eloimoncho@gmail.com", "16112001");
        Assert.assertEquals(4, this.om.numUsuarios());
        Assert.assertEquals(1, verificador);
    }

    // TEST 2: Obtener una lista de usuarios registrados, ordenada por órden alfabético.
    // public List<Usuario> usersByAlphabetOrder();

    @Test
    public void testUsersByAlphabetOrder() {
        List<Usuario> usuaris = this.om.usersByAlphabetOrder();

        Assert.assertEquals("Fernandez", usuaris.get(0).getUserSurname());
        Assert.assertEquals("Victor", usuaris.get(0).getUsername());

        Assert.assertEquals("Moncho", usuaris.get(1).getUserSurname());
        Assert.assertEquals("Eloi", usuaris.get(1).getUsername());

        Assert.assertEquals("Moran", usuaris.get(2).getUserSurname());
        Assert.assertEquals("Marc", usuaris.get(2).getUsername());
    }

    // TEST 3: Hacer el login de un usuario.
    // public int userLogin(String email, String password);
    // "0" se puede, "1" el login no es correcto.

    @Test
    public void testUserLogin() {
        // Login correcto.
        int verificador = this.om.userLogin("marcmoran@gmail.com", "28102001");
        Assert.assertEquals(0, verificador);

        // Login incorrecto.
        verificador = this.om.userLogin("marcmoran@gmail.com", "12345678");
        Assert.assertEquals(1, verificador);
    }

    // TEST 4: Añadir un nuevo objeto a la tienda.
    // public void addObjectToShop(String id, String name, String description, double coins);

    @Test
    public void testAddObjectToShop() {
        Assert.assertEquals(3, this.om.numObjetos());
        this.om.addObjectToShop("A002", "FCB", "Primera Equipación Jugador", 35);
        Assert.assertEquals(4, this.om.numObjetos());
    }

    // TEST 5: Obtener una lista de objetos ordenados por precio (de mayor a menor).
    // public List<ObjetoTienda> objectsByDescendentPrice();

    @Test
    public void testObjectsByDescendentPrice() {
        List<ObjetoTienda> objectes = this.om.objectsByDescendentPrice();

        Assert.assertEquals("BRUGGE", objectes.get(0).getObjectName());
        Assert.assertEquals( 40, objectes.get(0).getObjectCoins(), 0); // ¿?

        Assert.assertEquals("ATM", objectes.get(1).getObjectName());
        Assert.assertEquals( 35, objectes.get(1).getObjectCoins(), 0); // ¿?

        Assert.assertEquals("FCB", objectes.get(2).getObjectName());
        Assert.assertEquals( 30, objectes.get(2).getObjectCoins(), 0); // ¿?
    }

    // TEST 6: Compra de un objeto por parte de un usuario.
    // public int buyObjectByUser(String objectId, String userId);
    // "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.

    @Test
    public void testBuyObjectByUser() {
        // Se hace la compra correctamente (devuelve 0).
        int verificador = this.om.buyObjectByUser("C001", "1");
        Assert.assertEquals(0, verificador);

        // No existe el usuario solicitado (devuelve 1).
        verificador = this.om.buyObjectByUser("C001", "10");
        Assert.assertEquals(1, verificador);

        // El usuario no tiene saldo suficiente para hacer la compra (devuelve 2).
        verificador = this.om.buyObjectByUser("C001", "1");
        Assert.assertEquals(2, verificador);
    }

    // TEST 7: Obtener una lista de los objetos comprados por un usuario.
    // public List<ObjetoTienda> objectBoughtByUser(String userId);

    @Test
    public void testObjectsBoughtByUser() {
        List<ObjetoTienda> comprados = this.om.objectBoughtByUser("1");
        Assert.assertEquals(0, comprados.size());
    }

    // ----------------------------------------------------------------------------------------------------

}
