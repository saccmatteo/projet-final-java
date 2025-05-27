package testUnitaire;

import controller.ProductController;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDBAccessTest {
    private ProductController productController;

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        setProductController(new ProductController());
    }

    @org.junit.jupiter.api.Test
    void deleteProduct() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    void createProduct() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    void updateProduct() {
        setUp();
    }

    @org.junit.jupiter.api.Test
    void updateStock() {
        setUp();
    }
}