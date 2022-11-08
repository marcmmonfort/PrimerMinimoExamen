package Managers;

import Entities.*;
import Entities.ValueObjects.Credenciales;
import Main.*;
import Managers.*;
import Services.*;

import java.util.LinkedList;
import java.util.List;

public interface ObjetoManager {

    // ----------------------------------------------------------------------------------------------------

    public int registerUser(String username, String userSurname, String birthDate, String email, String password);
    // "0" se puede, "1" ya hay un usuario con ese mail.

    public List<Usuario> usersByAlphabetOrder();

    public int userLogin(String email, String password);
    // "0" se puede, "1" el login no es correcto.

    public void addObjectToShop(String id, String name, String description, double coins);

    public List<ObjetoTienda> objectsByDescendentPrice();

    public int buyObjectByUser(String objectId, String userId);
    // "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.

    public List<ObjetoTienda> objectBoughtByUser(String userId);

    public int numObjetos();

    public int numUsuarios();

    // ----------------------------------------------------------------------------------------------------

    public int size(); // Extra para API REST.

    // ----------------------------------------------------------------------------------------------------

}
