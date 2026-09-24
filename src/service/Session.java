package service;

import model.Customer;

public class Session {

    private static Customer loggedInCustomer;

    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}

