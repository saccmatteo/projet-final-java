package model;

public class User {
    private Integer id;
    private String lastName, firstName, functionLabel, passwordHash;
    private Order [] orders;
    private static final Integer MAX_ORDERS_NB = 5;

    // CONSTRUCTORS
        // Pour les selects
    public User(Integer id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        orders = new Order[MAX_ORDERS_NB];
    }

    // GETTERS
    public Integer getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    // METHODES
    public String toString() {
        return id + " - " + firstName + " " + lastName + " (" + functionLabel + ")";
    }
}

