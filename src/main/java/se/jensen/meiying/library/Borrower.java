package se.jensen.meiying.library;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
    private String username;
    private String password;
    private String name;
    private List<Loan> loans;

    public Borrower(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.loans = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    @Override
    public String toString() {
        return "Borrower{username='" + username + "', password='" + password + "', name='" + name + "'}";
    }
}
