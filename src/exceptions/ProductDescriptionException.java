package exceptions;

public class ProductDescriptionException extends Exception {
    private String wrongDescription;
    public ProductDescriptionException (String wrongDescription, String message) {
        super(message);
        setWrongDescription(wrongDescription);
    }

    public void setWrongDescription(String wrongDescription) {
        this.wrongDescription = wrongDescription;
    }
}
