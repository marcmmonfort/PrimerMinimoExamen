package Managers;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.*;
import org.apache.log4j.Logger;

public class ObjetoManagerImpl implements ObjetoManager{

    // ----------------------------------------------------------------------------------------------------

    // ATRIBUTOS.

    List<Product> products;
    Queue<Order> orders;
    HashMap<String,User> users;

    // ----------------------------------------------------------------------------------------------------

    // EXTRAS API REST.

    private static ObjetoManager instance; // Creamos la interfaz de product manager.

    final static Logger logger = Logger.getLogger(ObjetoManagerImpl.class);

    public static ObjetoManager getInstance(){ // Si no existe, creamos una implementación (fachada).
        if (instance==null) instance = new ObjetoManagerImpl();
        return instance;
    }

    public int size(){
        int ret = this.products.size();
        logger.info("size " + ret);
        return ret;
    }

    // ----------------------------------------------------------------------------------------------------

    public ObjetoManagerImpl(){
        this.products = new ArrayList<>();
        this.orders = new LinkedList<>();
        this.users = new HashMap<>();
    }

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH PRODUCTS.

    // > Function 1.
    @Override
    public List<Product> productsByPrice() {
        this.products.sort((Product p1,Product p2)->Double.compare(p1.getPrice(),p2.getPrice()));
        return this.products;
    }

    // > Function 2.
    @Override
    public List<Product> productsBySales() {
        this.products.sort((Product p1,Product p2)->(p1.getNumSales() - p2.getNumSales())); // ESTO.
        return this.products;
    }

    // > Function 3.
    @Override
    public void addProduct(String productId, String name, double price) {
        Product p = new Product(productId, name, price);
        this.products.add(p); // HERE.
    }

    // > Function 4.
    @Override
    public Product getProduct(String productId) {
        Product X = new Product();
        for (int i=0; i<this.products.size(); i++){
            if (Objects.equals(this.products.get(i).getProductId(), productId)){
                X = this.products.get(i);
            }
        }
        return X;
    }

    // > Function 5.
    @Override
    public int numProducts() {
        return this.products.size(); // HERE.
    }

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH ORDERS.

    // > Function 6.
    @Override
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    // > Function 7.
    @Override
    public Order processOrder() {
        Order ord = orders.poll(); // Treiem una ordre de la cua.
        this.users.get(ord.getUserId()).getProcessedOrders().add(ord); // Añadimos la órden al usuario que la hace.
        for (int i=0; i<ord.getElements().size(); i++){ // Recorremos todos los elementos/productos de la órden.
            for (int j=0; j<this.products.size(); j++){
                if (Objects.equals(this.products.get(j).getProductId(), ord.getElements().get(i).getProduct())){
                    this.products.get(j).setNumSales(this.products.get(j).getNumSales() + ord.getElements().get(i).getQuantity());
                }
            }
        }
        return ord;
    }

    // > Function 8.
    @Override
    public List<Order> ordersByUser(String userId) {
        return this.users.get(userId).getProcessedOrders(); // HERE.
    }

    // > Function 9.
    @Override
    public int numOrders() {
        return this.orders.size();
    }

    // > Function 10.
    @Override
    public int numSales(String b001) {
        int number = 0;
        for (int i=0; i<this.products.size(); i++){
            if (Objects.equals(this.products.get(i).getProductId(), b001)){
                number = this.products.get(i).getNumSales();
            }
        }
        return number;
    }

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH USERS.

    // > Function 11.
    @Override
    public void addUser(String s, String name, String surname) {
        this.users.put(s, new User(s, name, surname));
    }

    // > Function 12.
    @Override
    public int numUsers() {
        return this.users.size();
    }

    // ----------------------------------------------------------------------------------------------------

}
