package menu;

import dao.CustomerDAO;
import model.Customer;
import dao.UserDAO;

import java.util.Scanner;

public class CustomerMenu {

    private Scanner scanner = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();


    public void start(Customer customer){
        System.out.printf("Welcome to the library %s\n", customer.getUsername());
        System.out.println("Here you can manage your account. You can search the library and borrow and return books");
        System.out.println("If you have any books they will appear here : ");
        displayBooks();
        System.out.println("Please select if you would like to carry out further actions : ");
        System.out.println("1. Search Books");
        System.out.println("2. Return books assigned to you");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice){

            case 1 -> searchBooks();
            case 2 -> System.out.println("Building");
            case 3 -> System.out.println("Thank you for using this library system");
            default -> System.out.println("Not a valid option");



        }

        scanner.close();

    }

    private void searchBooks(){
        System.out.println("Enter the name of the book or the author");
        String searchTerm = scanner.nextLine();
        System.out.println("Searching...");
        userDAO.searchBooks(searchTerm);

        String optionToBorrow = "";

        boolean borrowSelection = false;

        while(!borrowSelection){

            System.out.println("Would you like to borrow one of the displayed books? (Y/N) : ");
            optionToBorrow = scanner.nextLine().toLowerCase();

            if(optionToBorrow.equals("y")){
                borrowBook();
                break;
            } else if(optionToBorrow.equals("n")){
                break;

            }

            System.out.println("That was not a valid input");

        }


        System.out.println("Please select if you would like to carry out further actions : ");
        System.out.println("1. Search Books");
        System.out.println("2. Return books assigned to you");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {

            case 1 -> searchBooks();
            case 2 -> System.out.println("Building");
            case 3 -> System.out.println("Thank you for using this library system");
            default -> System.out.println("Not a valid option");

        }

    }

    private void borrowBook(){

        int bookID;
        System.out.println("Enter the ID number of the book you wish to borrow");
        bookID = scanner.nextInt();
        scanner.nextLine();
        customerDAO.customerBorrowBooks(bookID);


    }

    private void displayBooks(){
        System.out.println("************************");
        customerDAO.displayCustomerBooks();
        System.out.println("************************");
    }

}
