package menu;

import model.Customer;
import dao.UserDAO;

import java.awt.print.Book;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {

    private Scanner scanner = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();


    public void start(Customer customer){
        System.out.printf("Welcome to the library %s\n", customer.getUsername());
        System.out.println("Here you can manage your account. You can search the library and borrow and return books");
        System.out.println("If you have any books they will appear here : ");
        System.out.println("Please select if you would like to carry out further actions : ");
        System.out.println("1. Search Books");
        System.out.println("2. Return books assigned to you");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice){

            case 1 -> searchBooks();
            case 2 -> System.out.println("Building");
            case 3 -> System.out.println("Building");
            default -> System.out.println("Not a valid option");



        }

        scanner.close();

    }

    private void searchBooks(){
        System.out.println("Enter the name of the book or the author");
        String searchTerm = scanner.nextLine();
        System.out.println("Searching...");
        userDAO.searchBooks(searchTerm);


    }


}
