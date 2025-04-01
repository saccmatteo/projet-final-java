package model;

public class User {
    private Integer id;
    private String lastName;
    private String firstName;
    private String passwordHash; // A voir, si on change réellement
    private Order [] orders;
    private static final Integer MAX_ORDERS_NB = 5;

    public User(Integer id, String lastName, String firstName, String passwordHash) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.passwordHash = passwordHash;
        orders = new Order[MAX_ORDERS_NB];
    }
}
