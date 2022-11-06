package Managers;

import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import java.util.List;

public interface ObjetoManager {

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH PRODUCTS.

    // > Function 1.
    public List<Product> productsByPrice();

    // > Function 2.
    public List<Product> productsBySales();

    // > Function 3.
    public void addProduct(String productId, String name, double price);

    // > Function 4.
    public Product getProduct(String productId);

    // > Function 5.
    public int numProducts();

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH ORDERS.

    // > Function 6.
    public void addOrder(Order order);

    // > Function 7.
    public Order processOrder();

    // > Function 8.
    public List<Order> ordersByUser(String userId);

    // > Function 9.
    public int numOrders();

    // > Function 10.
    public int numSales(String b001);

    // ----------------------------------------------------------------------------------------------------

    // FUNCTIONS RELATED WITH USERS.

    // > Function 11.
    public void addUser(String s, String name, String surname);

    // > Function 12.
    public int numUsers();

    // ----------------------------------------------------------------------------------------------------

    // EXTRAS API REST.

    public int size();

    // ----------------------------------------------------------------------------------------------------
}
