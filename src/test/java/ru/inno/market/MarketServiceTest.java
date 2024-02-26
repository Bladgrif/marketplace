package ru.inno.market;

import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class MarketServiceTest {
    @Test
    void testCreateOrderFor() {
        MarketService marketService = new MarketService();
        Client client = new Client(1, "John Doe");
        int orderId = marketService.createOrderFor(client);

        assertNotNull(orderId);
    }

    @Test
    void testAddItemToOrder() {
        MarketService marketService = new MarketService();
        Client client = new Client(1, "John Doe");
        int orderId = marketService.createOrderFor(client);

        Catalog catalog = new Catalog();
        Item item = catalog.getItemById(1);
        marketService.addItemToOrder(item, orderId);
        Order order = marketService.getOrderInfo(orderId);

        assertTrue(order.getItems().containsKey(item));
        assertEquals(1, order.getItems().get(item));
    }

    @Test
    void testApplyDiscountForOrder() {
        MarketService marketService = new MarketService();
        Client client = new Client(1, "John Doe");
        int orderId = marketService.createOrderFor(client);
        Catalog catalog = new Catalog();
        Item item = catalog.getItemById(1);
        marketService.addItemToOrder(item, orderId);

        double totalPriceBeforeDiscount = marketService.getOrderInfo(orderId).getTotalPrice();
        double discount = marketService.applyDiscountForOrder(orderId, PromoCodes.HAPPY_HOUR);
        double totalPriceAfterDiscount = marketService.getOrderInfo(orderId).getTotalPrice();

        assertEquals(totalPriceBeforeDiscount * (1 - PromoCodes.HAPPY_HOUR.getDiscount()), totalPriceAfterDiscount);
        assertTrue(marketService.getOrderInfo(orderId).isDiscountApplied());
    }

    @Test
    void testGetOrderInfo() {
        MarketService marketService = new MarketService();
        Client client = new Client(1, "John Doe");
        int orderId = marketService.createOrderFor(client);
        Order order = marketService.getOrderInfo(orderId);

        assertNotNull(order);
        assertEquals(orderId, order.getId());
    }
}
