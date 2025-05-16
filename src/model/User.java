package model;

public class User {
    private Integer id;
    private String lastName;
    private String firstName;
    private String passwordHash; // A voir, si on change réellement
    private Order [] orders;
    private static final Integer MAX_ORDERS_NB = 5;
    private String functionLabel;

    // CONSTRUCTORS
    public User(Integer id, String lastName, String firstName, String passwordHash) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.passwordHash = passwordHash;
        orders = new Order[MAX_ORDERS_NB];
    }
        // Pour les selects
    public User(Integer id, String lastName, String firstName){
        this(id, lastName, firstName, null);
        orders = new Order[MAX_ORDERS_NB];
    }

    // GETTERS
    public Integer getId() {
        return id == null ? 0 : id;
    }

    public String toString() {
        return id + " (" + firstName + " " + lastName + ")";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

