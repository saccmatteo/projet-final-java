package model;

public enum PaymentMethod {
    CARD("Carte bancaire"),
    CASH("Espèces"),
    NOTPAID("Pas payée");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
