package menu;

import com.password4j.Password;
import model.Customer;
import service.AuthService;
import java.util.Scanner;

import static service.AuthService.PASSWORD_MAX_LENGTH;
import static service.AuthService.PASSWORD_MIN_LENGTH;

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

    boolean successfulLogin = false;
    int invalidLoginCounter = 0;

    do{

        System.out.print("Please enter your username : ");
        username = scanner.nextLine();
        System.out.print("Please enter your password : ");
        password = scanner.nextLine();
        enforcePasswordMaxLength(scanner, PASSWORD_MAX_LENGTH, password);
        Customer customer = authService.loginCustomer(username, password);

        if (customer != null){
            new CustomerMenu().start(customer);
            successfulLogin = true;
        }

        //will allow for three login attempts
        else if (invalidLoginCounter <2){
            System.out.println("Invalid login. Check username and password");
            invalidLoginCounter++;
        }

        else {
            System.out.println("You have failed login three times. Please try again later");
            break;
        }
    } while (!successfulLogin);

}


    private void registerCustomer() {
        System.out.print("Choose a username : ");
        String username = scanner.nextLine();
        System.out.printf("**Password must contain a mix of capital and lowercase letters,\na number, and a special character\n" +
                "It must also have a minimum of %d characters and a maximum of %d characters**\n", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
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

    private String enforcePasswordMaxLength(Scanner scanner, int maxLenght, String password){


            if(password.isEmpty()){
                System.out.println("Password cannot be empty");
            }

            if(password.length() > maxLenght){
                System.out.println("Password too long. Maximum length is " +maxLenght);
            }

            if (!password.equals(password.trim())){
                System.out.println("Password cannot start or end with spaces");
            }

            return password;

    }

}
