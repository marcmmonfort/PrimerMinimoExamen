package Services;

import Entities.ObjetoTienda;
import Entities.Usuario;
import Managers.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Api(value = "/objeto", description = "Endpoint to Objeto Service") // "objeto"
@Path("/objeto")

public class ObjetoService {

    // ----------------------------------------------------------------------------------------------------

    private ObjetoManager om;

    public ObjetoService() {
        this.om = ObjetoManagerImpl.getInstance();
        if (om.size()==0) {
            this.om.addObjectToShop("A001", "FCB", "Primera Equipación Fan", 30);
            this.om.addObjectToShop("B001", "ATM", "Primera Equipación Jugador", 35);
            this.om.addObjectToShop("C001", "BRUGGE", "Primera Equipación Fan (Ferran Jutgol)", 40);
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
            @ApiResponse(code = 401, message = "Fallo en el registro. ¡Mail ya existente!")
    })
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario user) {
        // String username, String userSurname, String birthDate, String email, String password
        int verificador = this.om.registerUser(user.getUsername(), user.getUserSurname(), user.getBirthDate(), user.getCredentials().getEmail(), user.getCredentials().getPassword());
        if (verificador == 0){
            return Response.status(201).build();
        }
        else{
            return Response.status(401).build();
        }
    }

    // OPERACIÓN 2: Obtener una lista de usuarios registrados, ordenada por órden alfabético.
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -



    // OPERACIÓN 3: Hacer el login de un usuario.
    // MÉTODO HTTP: GET / PUT / POST / DELETE.
    // ACLARACIONES: "0" se puede, "1" el login no es correcto.



    // OPERACIÓN 4: Añadir un nuevo objeto a la tienda.
    // MÉTODO HTTP: POST.
    // ACLARACIONES: -



    // OPERACIÓN 5: Obtener una lista de objetos ordenados por precio (de mayor a menor).
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -



    // OPERACIÓN 6: Compra de un objeto por parte de un usuario.
    // MÉTODO HTTP: PUT.
    // ACLARACIONES: "0" se puede, "1" no existe el usuario, "2" no hay saldo suficiente.



    // OPERACIÓN 7: Obtener una lista de los objetos comprados por un usuario.
    // MÉTODO HTTP: GET.
    // ACLARACIONES: -



    // ----------------------------------------------------------------------------------------------------

    // OLD VERSION (PRODUCTS) ...

    // IMPLEMENTACIÓN 1: Obtener todos los productos ordenados por Price.
    // Tipo: GET.

    @GET
    @ApiOperation(value = "Get all the Products", notes = "Ordered by Price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List")
    })
    @Path("/price")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {
        List<Product> prodByPrices = this.om.productsByPrice();
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(prodByPrices) {};
        return Response.status(201).entity(entity).build(); // OK.
    }

    // ----------------------------------------------------------------------------------------------------

    // IMPLEMENTACIÓN 2: Obtener todos los productos ordenador por Sales.
    // Tipo: GET.

    @GET
    @ApiOperation(value = "Get all the Products", notes = "Ordered by Sales")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List")
    })
    @Path("/sales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {
        List<Product> prodBySales = this.om.productsBySales();
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(prodBySales) {};
        return Response.status(201).entity(entity).build(); // OK.
    }

    // ----------------------------------------------------------------------------------------------------

    // IMPLEMENTACIÓN 3: Añadir una nueva Order por parte de un cierto User.
    // Tipo: POST.

    @POST
    @ApiOperation(value = "Create a new Order", notes = "From a certain User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Order.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(Order ord) {
        if (ord.getUserId()==null || ord.getElements()==null) {
            return Response.status(500).entity(ord).build();
        }
        this.om.addOrder(ord);
        return Response.status(201).entity(ord).build(); // OK.
    }

    // ----------------------------------------------------------------------------------------------------

    // IMPLEMENTACIÓN 4: Procesar una Order.
    // Tipo: PUT.

    @PUT
    @ApiOperation(value = "Process an Order", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Order.class),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @Path("/order")
    public Response processOrder() {
        Order ordProc = this.om.processOrder();
        if (ordProc == null) {
            return Response.status(404).build();
        }
        return Response.status(201).entity(ordProc).build();
    }

    // ----------------------------------------------------------------------------------------------------

    // IMPLEMENTACIÓN 5: Obtener todas las Orders procesadas de un cierto User.
    // Tipo: GET.

    @GET
    @ApiOperation(value = "Get all the processed Orders", notes = "From a certain User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Order.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Order not found"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/order/{usedId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProcessedOrders(String identificador) {
        List<Order> ordProcUser = this.om.ordersByUser(identificador);
        if (ordProcUser == null) {
            return Response.status(404).build();
        }
        if (identificador==null) {
            return Response.status(500).entity(ordProcUser).build();
        }
        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(ordProcUser) {};
        return Response.status(201).entity(entity).build()  ;
    }

    // ----------------------------------------------------------------------------------------------------
}
