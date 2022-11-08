package Managers;

import Entities.*;
import Entities.ValueObjects.Credenciales;
import Main.*;
import Managers.*;
import Services.*;

import java.util.LinkedList;
import java.util.List;

public interface ObjetoManager {
    public void registerUser(String userId, String username, String userSurname, String birthDate, String email, String password);

    public List<Usuario> usersByAlphabetOrder();

    public boolean userLogin(String email, String password);

    public void addObjectToShop(String id, String name, String description, double coins);

    public List<ObjetoTienda> objectsByDescendentPrice();

    public int buyObjectByUser(String objectId, String userId); // "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.

    public List<ObjetoTienda> objectBoughtByUser(String userId);

    // ----------------------------------------------------------------------------------------------------

    public int size(); // Extra para API REST.

    // ----------------------------------------------------------------------------------------------------
}
