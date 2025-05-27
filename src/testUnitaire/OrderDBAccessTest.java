package testUnitaire;

import controller.OrderController;
import controller.UserController;
import exceptions.OrderCommentException;
import exceptions.OrderDiscountException;
import model.Order;
import model.OrderStatus;
import model.PaymentMethod;
import model.User;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDBAccessTest {
    private OrderController orderController;
    private UserController userController;
    private ArrayList<User> users;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @BeforeEach
    public void setUp() {
        setOrderController(new OrderController());
        setUserController(new UserController());
        users = new ArrayList<>(userController.getAllUsers());
    }

    @Test
    public void createOrder() throws OrderDiscountException, OrderCommentException {
        setUp();

        Order order = new Order(
                LocalDate.now(),
                null,
                49,
                "Test unitaire creation",
                false,
                PaymentMethod.NOTPAID.getLabel(),
                OrderStatus.IN_PROGRESS.getLabel(),
                users.get(0)
        );
        orderController.createOrder(order);
    }

    @Test
    public void deleteOrder() {
        setUp();

        orderController.deleteOrder(9);
    }

    @Test
    public void updateClosedOrder() {
        setUp();

        orderController.updateClosedOrder(11, PaymentMethod.CASH);
    }

    @Test
    public void updateOrder() throws OrderDiscountException, OrderCommentException{
        setUp();

        orderController.updateOrder(new Order(11,
                LocalDate.now(),
                LocalDate.now(),
                null,
                "Test unitaire update",
                true,
                OrderStatus.COMPLETED.getLabel(),
                PaymentMethod.CASH.getLabel(),
                users.get(1)
        ));
    }
}