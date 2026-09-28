package menu;

import dao.CustomerDAO;
import model.Customer;
import dao.UserDAO;

import java.util.Scanner;
import java.util.Set;

public class CustomerMenu {

    private Scanner scanner = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();


    public void start(Customer customer){
        System.out.printf("Welcome to the library %s\n", customer.getUsername());
        System.out.println("Here you can manage your account. You can search the library and borrow and return books");
        System.out.println("If you have any books they will appear here : ");

        displayBooks();

        nextSteps();

    }

    private void searchBooks(){
        System.out.println("Enter the name of the book or the author");
        String searchTerm = scanner.nextLine();
        System.out.println("Searching...");
        Set<Integer> displayedIDs = userDAO.searchBooks(searchTerm);

        if(displayedIDs.isEmpty()){
            System.out.println("No books found for that search");
            return;
        }

        String optionToBorrow = "";

        boolean borrowSelection = false;

        while(!borrowSelection){

            System.out.println("Would you like to borrow one of the displayed books? (Y/N) : ");
            optionToBorrow = scanner.nextLine().toLowerCase();

            if(optionToBorrow.equals("y")){
                borrowBook(displayedIDs);
                break;
            } else if(optionToBorrow.equals("n")){
                break;

            }

            System.out.println("That was not a valid input");

        }

    }

    private void borrowBook(Set<Integer> displayedIDs){

        int bookID;
        boolean validIDEntered = false;
        System.out.println("Enter the ID number of the book you wish to borrow");

        bookID = readIntOnly(scanner);

        while(!validIDEntered){
            if(displayedIDs.contains(bookID)){
                customerDAO.customerBorrowBooks(bookID);
                validIDEntered = true;
            }
            else{
                System.out.println("That is not one of the IDs listed. Review the Book IDs and try again");
                System.out.println("Enter the ID number of the book you wish to borrow");
                bookID = readIntOnly(scanner);
            }
        }



    }

    private void displayBooks(){
        System.out.println("************************");
        System.out.println("These are the books that are borrowed in your name : ");
        customerDAO.displayCustomerBooks();
        System.out.println("************************");
    }

    private void returnBooks(){
        System.out.println("************************");
        System.out.println("You have selected to return a book");
        displayBooks();

        System.out.println("Please select the ID of the book you wish to return");
        int bookID;
        bookID = readIntOnly(scanner);
        boolean returned = customerDAO.customerReturnBooks(bookID);

    }

    public void nextSteps(){

        boolean loggedIn = true;

        while(loggedIn){

            System.out.println("Please select if you would like to carry out further actions : ");
            System.out.println("1. Search Books");
            System.out.println("2. Return books assigned to you");
            System.out.println("3. Logout");

            int choice = readIntOnly(scanner);

            switch(choice) {

                case 1 -> searchBooks();
                case 2 -> returnBooks();
                case 3 -> {
                    System.out.println("Thank you for using this library system");
                    loggedIn = false;
                }
                default -> System.out.println("Not a valid option");

            }

        }





    }

    private int readIntOnly(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();

            // Check if input contains ONLY digits
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Numbers only. Try again");
                continue;
            }
            try{
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Number too large. Please enter a smaller number");
            }

        }
    }

}
