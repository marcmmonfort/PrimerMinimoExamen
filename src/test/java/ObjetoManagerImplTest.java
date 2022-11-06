import Entities.*;
import Main.*;
import Managers.*;
import Services.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class ObjetoManagerImplTest {

    ObjetoManager pm;

    @Before
    public void setUp() {
        pm = new ObjetoManagerImplTest();
        pm.addUser("1111111", "Juan", "lopez");
        pm.addUser("2222222",  "David", "Rincon");
        pm.addUser("3333333",  "Juan", "Hernández");

        pm.addProduct("B001", "Coca cola", 2);
        pm.addProduct("C002", "Café amb gel", 1.5);
        pm.addProduct("A002", "Donut", 2.25);
        pm.addProduct("A003", "Croissant", 1.25);

        prepareOrders();
    }

    @After
    public void tearDown() {
        this.pm = null;
    }

    private void prepareOrders() {
        Order o1 = new Order("1111111");
        o1.addLP(3, "B001");
        o1.addLP(2, "C002");

        Order o2 = new Order("1111111");
        o2.addLP(3, "A002");
        o2.addLP(1, "B001");

        Order o3 = new Order("2222222");
        o3.addLP(3, "B001");
        o3.addLP(2, "A002");

        Assert.assertEquals(0, this.pm.numOrders());
        this.pm.addOrder(o1);
        Assert.assertEquals(1, this.pm.numOrders());
        this.pm.addOrder(o2);
        Assert.assertEquals(2, this.pm.numOrders());
        this.pm.addOrder(o3);
        Assert.assertEquals(3, this.pm.numOrders());
    }

    @Test
    public void testAddOrder() {
        Assert.assertEquals(3, this.pm.numUsers());
        Assert.assertEquals(4, this.pm.numProducts());
        Assert.assertEquals(3, this.pm.numOrders());
        // ...
        Order o4 = new Order("2222222");
        o4.addLP(3, "B001");
        o4.addLP(2, "A003");
        this.pm.addOrder(o4);

        Assert.assertEquals(4, this.pm.numOrders());
    }

    @Test
    public void processOrderTest() {
        Assert.assertEquals(3, this.pm.numUsers());
        Assert.assertEquals(4, this.pm.numProducts());
        Assert.assertEquals(3, this.pm.numOrders());

        Order order1 = this.pm.processOrder();
        Assert.assertEquals(2, this.pm.numOrders());
        Assert.assertEquals(3, order1.getLP(0).getQuantity());
        Assert.assertEquals(2, order1.getLP(1).getQuantity());
        Assert.assertEquals(3, this.pm.numSales("B001"));

        Order order2 = this.pm.processOrder();
        Assert.assertEquals(1, this.pm.numOrders());
        Assert.assertEquals(4, this.pm.numSales("B001"));

        Order order3 = this.pm.processOrder();
        Assert.assertEquals(0, this.pm.numOrders());
        Assert.assertEquals(7, this.pm.numSales("B001"));
    }


    @Test
    public void productsSortByPrice() {
        List<Product> products = this.pm.productsByPrice();

        Assert.assertEquals("A003", products.get(0).getProductId());
        Assert.assertEquals(1.25, products.get(0).getPrice(), 0);

        Assert.assertEquals("C002", products.get(1).getProductId());
        Assert.assertEquals(1.5, products.get(1).getPrice(), 0);

        Assert.assertEquals("B001", products.get(2).getProductId());
        Assert.assertEquals(2, products.get(2).getPrice(), 0);

        Assert.assertEquals("A002", products.get(3).getProductId());
        Assert.assertEquals(2.25, products.get(3).getPrice(), 0);
    }

    @Test
    public void productsSortByNumSales() {
        processOrderTest();

        List<Product> products = this.pm.productsBySales();
        Assert.assertEquals("A003", products.get(0).getProductId());
        Assert.assertEquals("Croissant", products.get(0).getDescription());
        Assert.assertEquals(0, products.get(0).getNumSales());

        Assert.assertEquals("C002", products.get(1).getProductId());
        Assert.assertEquals("Café amb gel", products.get(1).getDescription());
        Assert.assertEquals(2, products.get(1).getNumSales());

        Assert.assertEquals("A002", products.get(2).getProductId());
        Assert.assertEquals("Donut", products.get(2).getDescription());
        Assert.assertEquals(5, products.get(2).getNumSales());

        Assert.assertEquals("B001", products.get(3).getProductId());
        Assert.assertEquals("Coca cola", products.get(3).getDescription());
        Assert.assertEquals(7, products.get(3).getNumSales());
    }

    @Test
    public void ordersByUserTest() {
        processOrderTest();
        List<Order> orders1 = this.pm.ordersByUser("1111111");
        Assert.assertEquals(2, orders1.size());

        List<Order> orders2 = this.pm.ordersByUser("2222222");
        Assert.assertEquals(1, orders2.size());

    }
}
