package model;


public enum OrderStatus {
    IN_PROGRESS("En cours"),
    COMPLETED("Terminé");

    private final String label;

    OrderStatus(String label) {
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

