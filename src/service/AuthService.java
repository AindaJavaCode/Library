package service;

import dao.CustomerDAO;
import model.Customer;
import util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;
import com.password4j.Password;
import com.password4j.Hash;
import com.password4j.types.Argon2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AuthService {

    private CustomerDAO customerDAO = new CustomerDAO();


    public Customer loginCustomer(String username, String password){

        Customer customer = customerDAO.getCustomerByUsername(username);

        //verifies that the pw provided by user
        //when hashed and salted
        //matches hashed and salted pw in the db+

        String pepperedPassword = password + System.getenv("PASSWORD_PEPPER");

        boolean validPassword = Password.check(pepperedPassword, customer.getPassword()).withArgon2();


        if(customer.getUsername().equals(username) && validPassword){
            return customer;
        }

        return null;

    }

    public boolean registerCustomer(String username, String password, String email){

        validateRegistrationInputFields(validUsername(username), "Username");
        validateRegistrationInputFields(validPassword(password), "Password");
        validateRegistrationInputFields(validEmail(email), "Email");

        if(validUsername(username) && validPassword(password) && validEmail(email) && isNewUsername(username)
            && isNewEmailAddress(email)){

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

    public void validateRegistrationInputFields(boolean isValid, String fieldName){

        if(isValid){
            System.out.println(fieldName + " is valid");
        }
        else{
            System.out.println(fieldName + " IS INVALID");
        }

    }

    public boolean isNewUsername(String username){

        Set<String> listOfUsername = new HashSet<>();

        String sql = "SELECT customer_username FROM customer";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery()){

            while (result.next()){
                listOfUsername.add(result.getString("customer_username"));
            }

            if(!listOfUsername.contains(username)){
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("USERNAME IS ALREADY TAKEN");
        return false;
    }

    public boolean isNewEmailAddress(String email){

        Set<String> listOfEmails = new HashSet<>();

        String sql = "SELECT customer_email FROM customer";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery()){

            while (result.next()){
                listOfEmails.add(result.getString("customer_email"));
            }

            if(!listOfEmails.contains(email)){
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return
        System.out.println("EMAIL IS ASSOCIATED WITH ANOTHER USER");
        return false;
    }



}
