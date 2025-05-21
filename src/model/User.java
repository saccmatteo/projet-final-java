package model;

public class User {
    private Integer id;
    private String lastName;
    private String firstName;
    private String passwordHash; // A voir, si on change réellement
    private String functionLabel;
    private Order [] orders;
    private static final Integer MAX_ORDERS_NB = 5;

    // CONSTRUCTORS
        // Pour les selects
    public User(Integer id, String lastName, String firstName, String functionLabel){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.functionLabel = functionLabel;
        orders = new Order[MAX_ORDERS_NB];
    }

    // GETTERS
    public Integer getId() {
        return id == null ? 0 : id;
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

