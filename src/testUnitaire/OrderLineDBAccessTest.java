package testUnitaire;

import controller.OrderLineController;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLineDBAccessTest {
    private OrderLineController orderLineController;

    public void setOrderLineController(OrderLineController orderLineController) {
        this.orderLineController = orderLineController;
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        setOrderLineController(new OrderLineController());
    }

    @org.junit.jupiter.api.Test
    void createOrderLine() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    void updateOrderLine() {
        setUp();
    }
}