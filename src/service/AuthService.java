package service;

import dao.CustomerDAO;
import model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import com.password4j.Password;
import com.password4j.Hash;
import com.password4j.types.Argon2;

public class AuthService {

    private CustomerDAO customerDAO = new CustomerDAO();


    public Customer loginCustomer(String username, String password){

        Customer customer = customerDAO.getCustomerByUsername(username);

        //verifies that the pw provided by user
        //when hashed and salted
        //matches hashed and salted pw in the db

        String pepperedPassword = password + System.getenv("PASSWORD_PEPPER");

        boolean validPassword = Password.check(pepperedPassword, customer.getPassword()).withArgon2();


        if(customer.getUsername().equals(username) && validPassword){
            return customer;
        }

        return null;

    }

    public boolean registerCustomer(String username, String password, String email){

        if(validUsername(username)){
            System.out.println("Username is valid");
        }
        else{
            System.out.println("USERNAME INVALID");
        }

        if(validPassword(password)){
            System.out.println("Password is valid");
        }
        else{
            System.out.println("PASSWORD INVALID");
        }

        if(validEmail(email)){
            System.out.println("Email is valid");
        }
        else{
            System.out.println("EMAIL IS INVALID");
        }

        if(validUsername(username) && validPassword(password) && validEmail(email)){

            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setEmail(email);

            return customerDAO.createCustomer(customer);
        }

        return false;
    }


    public boolean validPassword(String password) {

        String geOneNumberRegex = ".*\\d.*";
        String geOneSpecialCharacterRegex = ".*[^a-zA-Z0-9].*";
        String geTwoLetterRegex = ".*[A-Za-z].*[A-Za-z].*";
        String geOneUpperOneLowerLetter = "^(?=.*[A-Z])(?=.*[a-z]).+$";

        return password.length() >= 12 && password.matches(geOneNumberRegex)
                && password.matches(geOneSpecialCharacterRegex) && password.matches(geTwoLetterRegex)
                && password.matches(geOneUpperOneLowerLetter);
    }

    public boolean validUsername(String username){

        String alphaNumericUsername = "^[A-Za-z][A-Za-z0-9._-]{5,29}$";

        return username.matches(alphaNumericUsername);

    }

    public boolean validEmail(String email){

        String validEmailFormat = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email.matches(validEmailFormat);
    }



}
