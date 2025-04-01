package model;

public class Function {
    private String label;
    private User [] users;
    private static final Integer MAX_USERS_NB = 10;

    public Function(String label) {
        this.label = label;
        users = new User[MAX_USERS_NB];
    }
}
