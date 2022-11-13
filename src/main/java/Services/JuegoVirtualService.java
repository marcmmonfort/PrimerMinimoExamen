package Services;

import Managers.*;

import io.swagger.annotations.Api;

import javax.ws.rs.*;

@Api(value = "/objeto", description = "Endpoint to Objeto Service") // "objeto"
@Path("/objeto")

public class JuegoVirtualService {

    private JuegoVirtualManager jvm;

    public JuegoVirtualService() {
        this.jvm = JuegoVirtualManagerImpl.getInstance();
        if (jvm.size()==0) {
            this.jvm.crearJuego("FIFA", "Juego de fútbol", 2);
            this.jvm.crearJuego("GTA", "Juego de vida real", 4);
            this.jvm.crearJuego("Gran Turismo", "Juego de conducción", 5);

            this.jvm.crearUsuario("Marc");
            this.jvm.crearUsuario("Victor");
            this.jvm.crearUsuario("Eloi");
        }
    }

    // OPERACION 1: Crear un Juego.
    // MÉTODO HTTP: POST.
    // ESTRUCTURA: public void crearJuego(String juegoId, String juegoDescripcion, int numeroNivelesJuego);
    // EXCEPCIONES: -



    // OPERACION 2: Iniciar una Partida (por parte de un Usuario).
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA: public void iniciarPartida (String juegoId, String usuarioId);
    // EXCEPCIONES: JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException.



    // OPERACION 3: Pedir el Nivel Actual de la Partida en la que está el Usuario introducido.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public int pedirNivelJuegoDePartida (String usuarioId);
    // EXCEPCIONES: UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException.



    // OPERACION 4: Pedir la Puntuación Actual en una Partida (por parte de un Usuario).
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public int pedirPuntosDePartida(String usuarioId);
    // EXCEPCIONES: UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException.



    // OPERACION 5: Pasar de Nivel en una Partida.
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA: public void pasarDeNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel);
    // EXCEPCIONES: UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException.



    // OPERACION 6: Finalizar una Partida.
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA: public void finalizarPartida(String usuarioId);
    // EXCEPCIONES: UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException.



    // OPERACION 7: Obtener los Usuarios que han jugado un cierto Juego ordenados por Puntos (de mayor a menor).
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public List<Usuario> obtenerHistorialUsuariosDeJuego(String juegoId);
    // EXCEPCIONES: JuegoIdNoExisteException.



    // OPERACION 8: Obtener las Partidas en las que ha jugado un Usuario.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public List<Partida> obtenerPartidasUsuario(String usuarioId);
    // EXCEPCIONES: UsuarioIdNoExisteException.



    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public InfoPartida obtenerInfoUsuarioJuego(String juegoId, String usuarioId);
    // EXCEPCIONES: -








    // ----------------------------------------------------------------------------------------------------

    // NO IMPLEMENTADO.
    // LO QUE HAY ABAJO ES EL QUE TENIA DE PLANTILLA.

    /*

    private ObjetoManager om;

    public ObjetoService() {
        this.om = ObjetoManagerImpl.getInstance();
        if (om.size()==0) {
            this.om.addObjectToShop("A001", "FCB", "Primera Equipación Fan", 30);
            this.om.addObjectToShop("B001", "ATM", "Primera Equipación Jugador", 35);
            this.om.addObjectToShop("C001", "BRUGGE", "Primera Equipación Fan (Ferran Jutgol)", 40);

            this.om.registerUser("Marc", "Moran", "28/10/2001", "marcmoran@gmail.com", "28102001");
            this.om.registerUser("Victor", "Fernandez", "13/06/2001", "victorfernandez@gmail.com", "13062001");
            this.om.registerUser("Eloi", "Moncho", "28/08/2001", "eloimoncho@gmail.com", "28082001");
        }
    }

    // ----------------------------------------------------------------------------------------------------

    // OPERACIÓN 1: Registrar un usuario.
    // MÉTODO HTTP: POST.
    // ACLARACIONES: "0" se puede, "1" ya hay un usuario con ese mail.

    @POST
    @ApiOperation(value = "Registrar un usuario", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Registrado correctamente!"),
            @ApiResponse(code = 404, message = "Fallo en el registro. ¡Mail ya existente!")
    })
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario user) {
        int verificador = this.om.registerUser(user.getUsername(), user.getUserSurname(), user.getBirthDate(), user.getCredentials().getEmail(), user.getCredentials().getPassword());
        if (verificador == 0){
            return Response.status(201).build();
        }
        else{
            return Response.status(404).build();
        }
    }

    // OPERACIÓN 2: Obtener una lista de usuarios registrados, ordenada por órden alfabético.
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -

    @GET
    @ApiOperation(value = "Obtener una lista de usuarios registrados", notes = "Por órden alfabético")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Se ha obtenido correctamente la lista!", response = Usuario.class, responseContainer="List")
    })
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response usuariosPorOrdenAlfabetico() {
        List<Usuario> usuariosOrdenAlfabetico = this.om.usersByAlphabetOrder();
        GenericEntity<List<Usuario>> usuariosOrdenados = new GenericEntity<List<Usuario>>(usuariosOrdenAlfabetico) {};
        return Response.status(201).entity(usuariosOrdenados).build(); // OK.
    }

    // OPERACIÓN 3: Hacer el login de un usuario.
    // MÉTODO HTTP: POST.
    // ACLARACIONES: "0" se puede, "1" el login no es correcto.

    @POST
    @ApiOperation(value = "Login de un usuario", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Registrado correctamente!"),
            @ApiResponse(code = 404, message = "Fallo en el login. ¡El email y/o la password son incorrectos!")
    })
    @Path("/usuario/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUsuario(Credenciales credentials) {
        int verificador = this.om.userLogin (credentials.getEmail(), credentials.getPassword());
        if (verificador == 1)  {
            return Response.status(404).build();
        }
        else{
            return Response.status(201).build();
        }
    }

    // OPERACIÓN 4: Añadir un nuevo objeto a la tienda.
    // MÉTODO HTTP: POST.
    // ACLARACIONES: -

    @POST
    @ApiOperation(value = "Añadir un nuevo objeto a la tienda", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Objeto añadido correctamente!"),
    })
    @Path("/tienda")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoObjetoParaTienda(ObjetoTienda objecte) {
        this.om.addObjectToShop(objecte.getObjectId(), objecte.getObjectName(), objecte.getObjectDescription(), objecte.getObjectCoins());
        return Response.status(201).build();
    }

    // OPERACIÓN 5: Obtener una lista de objetos ordenados por precio (de mayor a menor).
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -

    @GET
    @ApiOperation(value = "Obtener una lista de objetos ordenados por precio", notes = "Por órden decreciente (de mayor a menor)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Se ha obtenido correctamente la lista!", response = ObjetoTienda.class, responseContainer="List")
    })
    @Path("/tienda")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objetosPorPrecioDecreciente() {
        List<ObjetoTienda> objetosPrecioDecreciente = this.om.objectsByDescendentPrice();
        GenericEntity<List<ObjetoTienda>> objetosOrdenados = new GenericEntity<List<ObjetoTienda>>(objetosPrecioDecreciente) {};
        return Response.status(201).entity(objetosOrdenados).build(); // OK.
    }

    // OPERACIÓN 6: Compra de un objeto por parte de un usuario.
    // MÉTODO HTTP: PUT.
    // ACLARACIONES: "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.

    @PUT
    @ApiOperation(value = "Compra de un objeto por parte de un usuario", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Se ha realizado la compra!"),
            @ApiResponse(code = 404, message = "No se ha ralizado la compra. ¡No existe el usuario!"),
            @ApiResponse(code = 406, message = "No se ha realizado la compra. ¡El usuario no tiene saldo suficiente!")
    })
    @Path("/tienda/{objectId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON) // ESTO
    public Response compraObjetoPorUsuario(@PathParam("objectId") String idObjeto, @PathParam("userId") String idUsuario) {
        int verificador = this.om.buyObjectByUser(idObjeto, idUsuario);
        if (verificador == 0) {
            return Response.status(201).build();
        } else if (verificador == 1) {
            return Response.status(404).build();
        }
        else { // verificador == 2
            return Response.status(406).build();
        }
    }

    // OPERACIÓN 7: Obtener una lista de los objetos comprados por un usuario.
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -

    @GET
    @ApiOperation(value = "Obtener una lista de objetos comprados por un usuario", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Se ha obtenido correctamente la lista!", response = ObjetoTienda.class, responseContainer="List")
    })
    @Path("/tienda/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response objetosCompradosPorUsuario(@PathParam("userId") String idUsuario) {
        List<ObjetoTienda> objetosComprados = this.om.objectBoughtByUser(idUsuario);
        GenericEntity<List<ObjetoTienda>> adquisiciones = new GenericEntity<List<ObjetoTienda>>(objetosComprados) {};
        return Response.status(201).entity(adquisiciones).build(); // OK.
    }

    // ----------------------------------------------------------------------------------------------------


     */
}

