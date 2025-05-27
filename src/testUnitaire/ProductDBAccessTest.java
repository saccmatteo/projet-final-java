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
        //le but ici c'est de creer puis modifier et enfin supprimer directement pour ne pas tacher la database
    void createUpdateDeleteProduct()
            throws DAOException, ProductLabelException, ProductPriceException, ProductNbInStockException,
            ProductMinTresholdException, ProductAlcoholPercentageException, ProductDescriptionException,
            ProductSupplierLabelException, ProductSupplierPhoneNumberException {

        setUp();

        //Création
        productController.createProduct(product);
        System.out.println("Création effectuée");

        //Récupérer l'ID du produit inséré + filtré pour sélectionné le produit créé juste avant
        ArrayList<Product> allProducts = productController.getAllProducts();
        Product inserted = allProducts.stream()
                .filter(p -> p.getLabel().equals("Caviar") && p.getDescription().equals("Test product create"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(inserted);
        int productId = inserted.getId();

        //Update
        Product updated = new Product(
                productId,
                "Caviar modifié",
                150.0,
                250,
                5,
                true,
                null,
                LocalDate.now(),
                LocalDate.now(),
                "Description modifiée",
                "Henallux",
                "0484845549",
                categories.get(3)
        );
        productController.updateProduct(updated);
        System.out.println("Modification effectuée");

        //Update stock
        productController.updateStock(updated, 300);
        System.out.println("Modification du stock effectuée");

        //Supprimer
        productController.deleteProduct(productId);
        System.out.println("Suppression effectuée");
    }
}