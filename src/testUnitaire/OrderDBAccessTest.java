package testUnitaire;

import controller.OrderController;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDBAccessTest {
    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        setOrderController(new OrderController());
    }

    @org.junit.jupiter.api.Test
    public void createOrder() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    public void deleteOrder() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    public void updateClosedOrder() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    public void updateOrder() {
        setUp();
    }
}