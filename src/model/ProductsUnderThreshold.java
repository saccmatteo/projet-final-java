package model;

import java.time.LocalDate;

public class ProductsUnderThreshold {
    private Integer productId;
    private String productLabel, supplierLabel, supplierPhoneNumber, categoryLabel;
    private LocalDate ProductLastRestockDate;

    public ProductsUnderThreshold(Integer productId, String productLabel, LocalDate ProductLastRestockDate, String supplierLabel, String supplierPhoneNumber, String categoryLabel) {
        this.productId = productId;
        this.productLabel = productLabel;
        this.ProductLastRestockDate = ProductLastRestockDate;
        this.supplierLabel = supplierLabel;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.categoryLabel = categoryLabel;
    }

    // GETTERS
    public Integer getProductId() {
        return productId;
    }
    public String getProductLabel() {
        return productLabel;
    }
    public String getSupplierLabel() {
        return supplierLabel;
    }
    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }
    public String getCategoryLabel() {
        return categoryLabel;
    }
    public LocalDate getProductLastRestockDate() {
        return ProductLastRestockDate;
    }
}
