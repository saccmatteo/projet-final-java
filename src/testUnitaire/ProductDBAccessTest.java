package testUnitaire;

import controller.CategoryController;
import controller.ProductController;
import controller.SupplierController;
import exceptions.*;
import jdk.jfr.Category;
import model.Product;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProductDBAccessTest {
    private ProductController productController;
    private CategoryController categoryController;
    private ArrayList<String> categories;
    private Product product;

    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    @BeforeEach
    void setUp()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException{
        setProductController(new ProductController());
        setCategoryController(new CategoryController());
        categories = new ArrayList<>(categoryController.getAllCategories());

        product = new Product("Caviar",
                100.0,
                200,
                1,
                true,
                null,
                LocalDate.now(),
                LocalDate.now(),
                "Test product create",
                "Henallux",
                "0484845549",
                categories.get(3));
    }

    @Test
    void createProduct()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException{
        setUp();

        productController.createProduct(product);
    }

    @Test
    void deleteProduct()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException{
        setUp();

        productController.deleteProduct(19);
    }

    @Test
    void updateProduct()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException {
        setUp();

        productController.updateProduct(new Product(
                1,
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
        ));
    }

    @Test
    void updateStock()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException, ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException, ProductSupplierLabelException, ProductSupplierPhoneNumberException{
        setUp();

        product.setId(19);
        productController.updateStock(product, 20);
    }
}