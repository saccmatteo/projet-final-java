package model;

public class Supplier {
    private String label;
    private String phoneNumber;

    public Supplier(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
