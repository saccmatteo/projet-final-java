package testUnitaire;

import controller.CategoryController;
import controller.OrderLineController;
import exceptions.*;
import model.OrderLine;
import model.Product;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;


public class OrderLineDBAccessTest {
    private OrderLineController orderLineController;
    private CategoryController categoryController;
    private ArrayList<String> categories;

    public void setOrderLineController(OrderLineController orderLineController) {
        this.orderLineController = orderLineController;
    }
    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
    @BeforeEach
    void setUp() {
        setOrderLineController(new OrderLineController());
        setCategoryController(new CategoryController());
        categories = new ArrayList<>(categoryController.getAllCategories());
    }

    @Test
    void createOrderLine()
            throws OrderLineQuantityException , ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException{
        setUp();

        orderLineController.createOrderLine(new OrderLine(
                19,
                100.0,
                new Product(
                        19,
                        "Caviar de riche",
                        100.0,
                        200,
                        1,
                        true,
                        null,
                        LocalDate.now(),
                        LocalDate.now(),
                        "Test product update",
                        "Henallux",
                        "0484845549",
                        categories.get(3)
                )), 11);
    }

}