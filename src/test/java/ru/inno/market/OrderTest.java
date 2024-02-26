package ru.inno.market;

import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    @Test
    void testAddItem() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);
        Catalog catalog = new Catalog();
        Item item = catalog.getItemById(1);

        order.addItem(item);
        assertTrue(order.getItems().containsKey(item));
        assertEquals(1, order.getItems().get(item));
    }

    @Test
    void testApplyDiscount() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);
        Catalog catalog = new Catalog();
        Item item = catalog.getItemById(1);

        order.addItem(item);
        double totalPriceBeforeDiscount = order.getTotalPrice();

        order.applyDiscount(0.1); // applying a 10% discount
        double totalPriceAfterDiscount = order.getTotalPrice();

        assertEquals(totalPriceBeforeDiscount * 0.9, totalPriceAfterDiscount);
        assertTrue(order.isDiscountApplied());
    }

    @Test
    void testGetClient() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);

        assertEquals(client, order.getClient());
    }

    @Test
    void testGetCart() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);
        Catalog catalog = new Catalog();
        Item item1 = catalog.getItemById(1);
        Item item2 = catalog.getItemById(2);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(2, order.getCart().size());
    }
    @Test
    void testGetTotalPrice() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);
        Catalog catalog = new Catalog();
        Item item1 = catalog.getItemById(1);
        Item item2 = catalog.getItemById(2);

        order.addItem(item1);
        order.addItem(item2);

        double expectedTotalPrice = item1.getPrice() + item2.getPrice();
        assertEquals(expectedTotalPrice, order.getTotalPrice());
    }


    @Test
    void testIsDiscountApplied() {
        Client client = new Client(1, "John Doe");
        Order order = new Order(1, client);

        assertFalse(order.isDiscountApplied());
        order.applyDiscount(0.1);
        assertTrue(order.isDiscountApplied());
    }


}
