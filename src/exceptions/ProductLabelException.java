package exceptions;

public class ProductLabelException extends Exception {
    private String wrongLabel;
    public ProductLabelException (String wrongLabel, String message) {
        super(message);
        setWrongLabel(wrongLabel);
    }
    public void setWrongLabel(String wrongLabel) {
        this.wrongLabel = wrongLabel;
    }
}
