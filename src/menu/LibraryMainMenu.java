package menu;

import model.Customer;
import service.AuthService;
import java.util.Scanner;
import service.AuthService;

public class LibraryMainMenu {

    private final AuthService authService = new AuthService();
    private final Scanner scanner = new Scanner(System.in);

    String userSelection;

        public void start(){

        System.out.println("******************************************");
        System.out.println("Welcome to the Drumcondra Library Admin platform");
        System.out.printf("*******************************************\n");
        System.out.println("Select the appropriate number for the action you wish to carry out");
        System.out.println("1. Login as a customer");
        System.out.println("2. Create a customer account");
        System.out.println("3. Login as Admin");

    int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
         case 1 -> loginCustomer();
            case 2 -> {
                registerCustomer();
                loginCustomer();
            }
            case 3 -> System.out.println("We are building this currently");
            default -> System.out.println("That is not a valid input");
    }

        scanner.close();

}

private void loginCustomer(){
    String username;
    String password;

    System.out.println("You are logging in as a customer");
    System.out.print("Please enter your username : ");
    username = scanner.nextLine();
    System.out.print("Please enter your password : ");
    password = scanner.nextLine();

    Customer customer = authService.loginCustomer(username, password);

    if (customer != null){
        new CustomerMenu().start(customer);
    }
    else{
        System.out.println("Invalid login. Check username and password");
    }
}


    private void registerCustomer() {
        System.out.print("Choose a username : ");
        String username = scanner.nextLine();
        System.out.print("**Password must contain a mix of capital and lowercase letters,\na number, and a special character**\n");
        System.out.print("Choose a password : ");
        String password = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();

        boolean userRegistered = false;

        do {
            if (authService.registerCustomer(username, password, email)) {
                System.out.printf("Account created for %s\n", username);
                userRegistered = true;
                break;
            } else {
                System.out.println("Registration failed. Review username, password, and email requirements");
                registerCustomer();
                break;
            }
        } while (!userRegistered);

        }



    private void loginAdmin(){

    }

}
