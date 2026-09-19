package dao;


import model.Customer;
import util.DBConnection;
import java.sql.*;
import com.password4j.Password;
import com.password4j.Hash;
import com.password4j.types.Argon2;
import java.util.regex.Pattern;



public class CustomerDAO {

    public Customer getCustomerByUsername(String username){
          String sql = "SELECT * FROM customer WHERE customer_username = ?";

          try(Connection conn = DBConnection.getConnection();
          PreparedStatement statement = conn.prepareStatement(sql)){

              statement.setString(1, username);
              ResultSet rs = statement.executeQuery();

              if(rs.next()){
                  Customer customer = new Customer();
                  customer.setUsername(rs.getString("customer_username"));
                  customer.setPassword(rs.getString("customer_password"));
                  return customer;
              }

          } catch (Exception e) {
              e.printStackTrace();
          }

          return null;
    }

    public boolean createCustomer(Customer customer){

          String sql = "INSERT INTO customer (customer_username, customer_password, customer_email)" +
                    "VALUES (?, ?, ?)";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){

            String pepper = System.getenv("PASSWORD_PEPPER");

            if (pepper == null || pepper.isBlank()) {
                throw new IllegalStateException("PASSWORD_PEPPER is not configured");
            }

            //Adds secret pepper to unhashed password
            //pepper is saved as an environment variable
            String pepperedPassword = customer.getPassword() + pepper;

            //Argon2 adds a salt by default so
            //no need to do it manually
            Hash hash = Password.hash(pepperedPassword).withArgon2();

            String hashedPassword = hash.getResult();
            String salt = hash.getSalt();


            statement.setString(1, customer.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, customer.getEmail());

            return statement.executeUpdate() > 0;



        } catch (Exception e){
            e.printStackTrace();
            return false;

        }

    }
}
