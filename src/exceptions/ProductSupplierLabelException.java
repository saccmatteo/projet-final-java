package exceptions;

public class ProductSupplierLabelException extends Exception {
    private String wrongLabel;
    public ProductSupplierLabelException (String wrongLabel, String message) {
        super(message);
        setWrongLabel(wrongLabel);
    }

    public void setWrongLabel(String wrongLabel) {
        this.wrongLabel = wrongLabel;
    }
}
