package Managers;

import Entities.*;
import Entities.ValueObjects.Credenciales;
import Main.*;
import Managers.*;
import Services.*;

import java.util.*;
import org.apache.log4j.Logger;

public class ObjetoManagerImpl implements ObjetoManager{

    // ----------------------------------------------------------------------------------------------------

    List<ObjetoTienda> objetos;
    HashMap<String,Usuario> usuarios;

    // ----------------------------------------------------------------------------------------------------

    // CONSTRUCTOR DEL XManagerImplementation.

    public ObjetoManagerImpl(){
        this.objetos = new ArrayList<>();
        this.usuarios = new HashMap<>();
    }

    // ----------------------------------------------------------------------------------------------------

    // FUNCIONES BÁSICAS DE LA IMPLEMENTACIÓN.

    public int numObjetos(){
        return this.objetos.size();
    }

    public int numUsuarios(){
        return this.usuarios.size();
    }

    // ----------------------------------------------------------------------------------------------------

    // FUNCIONES DEL ObjectManager IMPLEMENTADAS.

    @Override
    public int registerUser(String username, String userSurname, String birthDate, String email, String password) {
        logger.info("Register user with info: "+username+", "+userSurname);
        String identificador = Integer.toString(this.usuarios.size());
        Usuario X = new Usuario(identificador, username, userSurname, birthDate, new Credenciales(email, password));

        // Búsqueda en el Hashmap de "usuarios" para encontrar si ya hay algún usuario con el mismo email.
        // "0" se puede, "1" ya hay un usuario con ese mail.

        int verificador = 0;
        int numUsers = this.usuarios.size();

        if (numUsers == 0){ // Si no hay ningún usuario, se puede añadir directamente sin hacer la comprobación.
            this.usuarios.put(identificador, X);
        }
        else{
            for (int i=0; i<numUsers; i++){
                String idHashmap = Integer.toString(i);
                if (Objects.equals(this.usuarios.get(idHashmap).getCredentials().getEmail(), email)){
                    verificador = 1;
                }
            }
            if (verificador == 0){ // Si durante la búsqueda no se ha encontrado ningún email igual, se introduce.
                this.usuarios.put(identificador, X);
                logger.info("User was added");
            }
        }

        return verificador;
    }

    @Override
    public List<Usuario> usersByAlphabetOrder() {
        List<Usuario> aux = new ArrayList<>(this.usuarios.values());
        aux.sort((Usuario p1,Usuario p2)->{
            int aux1 = String.CASE_INSENSITIVE_ORDER.compare(p1.getUserSurname(), p2.getUserSurname());
            if (aux1==0) {
                aux1 = String.CASE_INSENSITIVE_ORDER.compare(p1.getUsername(), p2.getUsername());
            }
            return aux1;
        });
        return aux;
    }

    @Override
    public int userLogin(String email, String password) {
        int loginPossible = 1;

        // Búsqueda en el Hashmap de credenciales por si hay alguna que coincide con las nuestras.
        // "0" existe, "1" no existe.

        int numUsers = this.usuarios.size();
        for (int i=0; i<numUsers; i++){
            String idHashmap = Integer.toString(i);
            if ((Objects.equals(this.usuarios.get(idHashmap).getCredentials().getEmail(), email))&&(Objects.equals(this.usuarios.get(idHashmap).getCredentials().getPassword(), password))) {
                loginPossible = 0;
            }
        }
        return loginPossible;
    }

    @Override
    public void addObjectToShop(String id, String name, String description, double coins) {
        ObjetoTienda objeto = new ObjetoTienda(id, name, description, coins);
        this.objetos.add(objeto);
    }

    @Override
    public List<ObjetoTienda> objectsByDescendentPrice() {
        this.objetos.sort((ObjetoTienda p2,ObjetoTienda p1)->Double.compare(p1.getObjectCoins(),p2.getObjectCoins()));
        return this.objetos;
    }

    @Override
    public int buyObjectByUser(String objectId, String usuarioId) { // "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.
        int verificador = 0;
        // Verificamos que exista el usuario.
        int numeroUsuarios = this.usuarios.size(); // Size = 3 es que hay ID = 0,1,2.
        if ((0 <= Integer.parseInt(usuarioId))&&(Integer.parseInt(usuarioId) < numeroUsuarios)) {  // Existe.
            // Quiere decir que el usuario existe. Seguimos.
            // Localizamos el objeto y verificamos que el usuario tiene saldo suficiente para comprarlo.
            int numObj = this.objetos.size();
            for (int i=0; i<numObj; i++) {
                // Localizamos nuestro objeto.
                if (Objects.equals(this.objetos.get(i).getObjectId(), objectId)) {
                    if (this.objetos.get(i).getObjectCoins() <= this.usuarios.get(usuarioId).getUserCoins()) {
                        // Quiere decir que el usuario lo puede comprar.
                        // Pasamos ya a hacer la compra (es decir, descontar el precio del sueldo del usuario y añadir el objeto a su lista).
                        this.usuarios.get(usuarioId).addObjetoComprado(this.objetos.get(i));
                        this.usuarios.get(usuarioId).descontarDinero(this.objetos.get(i).getObjectCoins());
                    }
                    else {
                        // Quiere decir que el saldo del usuario no es suficiente, por lo tanto no se puede comprar.
                        verificador = 2;
                    }
                }
            }
        }
        else {
            // Quiere decir que el usuario no existe. Paramos.
            verificador = 1;
        }
        return verificador;
    }

    @Override
    public List<ObjetoTienda> objectBoughtByUser(String userId) {
        return usuarios.get(userId).getObjectsBought();
    }

    // ----------------------------------------------------------------------------------------------------

    // EXTRA PARA EL API REST.

    private static ObjetoManager instance; // Creamos la interfaz de product manager.

    final static Logger logger = Logger.getLogger(ObjetoManagerImpl.class);

    public static ObjetoManager getInstance(){ // Si no existe, creamos una implementación (fachada).
        if (instance==null) instance = new ObjetoManagerImpl();
        return instance;
    }

    public int size(){
        int ret = this.objetos.size();
        logger.info("size " + ret);
        return ret;
    }

    // ----------------------------------------------------------------------------------------------------
}
