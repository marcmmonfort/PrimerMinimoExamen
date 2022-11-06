package Services;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/products", description = "Endpoint to Product Service") // "products"
@Path("/products")

public class ObjetoService {

    // ----------------------------------------------------------------------------------------------------

    private ProductManager tm;

    public ProductService() {
        this.tm = ProductManagerImpl.getInstance();
        if (tm.size()==0) {
            this.tm.addProduct("B001", "Coca cola", 2);
            this.tm.addProduct("C002", "Café amb gel", 1.5);
            this.tm.addProduct("A002", "Donut", 2.25);
            this.tm.addProduct("A003", "Croissant", 1.25);
        }
    }

    // ----------------------------------------------------------------------------------------------------

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
        List<Product> prodByPrices = this.tm.productsByPrice();
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
        List<Product> prodBySales = this.tm.productsBySales();
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
        this.tm.addOrder(ord);
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
        Order ordProc = this.tm.processOrder();
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
        List<Order> ordProcUser = this.tm.ordersByUser(identificador);
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
