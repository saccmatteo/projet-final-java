package testUnitaire;

import controller.OrderController;
import controller.UserController;
import exceptions.DAOException;
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
    public void setUp() throws DAOException {
            setOrderController(new OrderController());
            setUserController(new UserController());
            users = new ArrayList<>(userController.getAllUsers());
    }
    @Test
    //le but ici c'est de creer puis modifier et enfin supprimer directement pour ne pas tacher la database
    public void createUpdateDeleteOrder() throws DAOException, OrderDiscountException, OrderCommentException {
        setUp();

        //Créer une commande de test
        Order order = new Order(
                LocalDate.now(),
                null,
                49,
                "Test unitaire TEMP",
                false,
                OrderStatus.IN_PROGRESS.getLabel(),
                PaymentMethod.NOTPAID.getLabel(),
                users.get(0)
        );
        int generatedId = orderController.createOrder(order);
        System.out.println("Création effectuée");
        Assertions.assertTrue(generatedId > 0);

        //Mettre à jour (status + paiement)
        orderController.updateClosedOrder(generatedId, PaymentMethod.CASH, LocalDate.now());
        System.out.println("Finalisation effectuée");

        // Mettre à jour complètement
        Order updatedOrder = new Order(
                generatedId,
                LocalDate.now(),
                LocalDate.now(),
                null,
                "Test modifié",
                true,
                OrderStatus.COMPLETED.getLabel(),
                PaymentMethod.CASH.getLabel(),
                users.get(1)
        );
        orderController.updateOrder(updatedOrder);
        System.out.println("Modification effectuée");

        //Supprimer l'order direct
        orderController.deleteOrder(generatedId);
        System.out.println("Suppression effectuée");
    }
}
