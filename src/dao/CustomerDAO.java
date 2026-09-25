package dao;


import model.Customer;
import util.DBConnection;
import java.sql.*;
import com.password4j.Password;
import com.password4j.Hash;
import service.Session;


public class CustomerDAO {

    private static final String GET_CUSTOMER_USERNAME = "SELECT * FROM customer WHERE customer_username = ?";

    private static final String CREATE_CUSTOMER = "INSERT INTO customer (customer_username, customer_password, customer_email) VALUES (?, ?, ?)";

    private static final String SEARCH_BOOKS_BY_ID = "SELECT * FROM books WHERE book_ID = ?";

    private static final String UPDATE_BORROW_BOOK = "UPDATE books SET isborrowed_by_customer_id = ?, is_borrowed = ? WHERE book_id = ?";

    private static final String DISPLAY_BOOKS = "SELECT book_name, book_author FROM books WHERE isborrowed_by_customer_id = ?";

    private static final String RETURN_BOOK = "UPDATE books SET is_borrowed = false, isborrowed_by_customer_id = null WHERE book_id = ?";


    public Customer getCustomerByUsername(String username) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_USERNAME)) {

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();


            if (rs.next()) {
                Customer customer = new Customer();
                customer.setUsername(rs.getString("customer_username"));
                customer.setPassword(rs.getString("customer_password"));
                customer.setId(rs.getInt("customer_id"));
                return customer;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean createCustomer(Customer customer) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(CREATE_CUSTOMER)) {

            //pepper is saved as an environment variable
            String pepper = System.getenv("PASSWORD_PEPPER");

            if (pepper == null || pepper.isBlank()) {
                throw new IllegalStateException("PASSWORD_PEPPER is not configured");
            }

            //Adds secret pepper to unhashed password
            String pepperedPassword = customer.getPassword() + pepper;

            //Argon2 adds a salt by default so
            //no need to do it manually
            Hash hash = Password.hash(pepperedPassword).withArgon2();

            String hashedPassword = hash.getResult();

            statement.setString(1, customer.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, customer.getEmail());

            return statement.executeUpdate() > 0;


        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    public void customerBorrowBooks(int bookID) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(SEARCH_BOOKS_BY_ID)) {

            statement.setInt(1, bookID);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                String name = rs.getString("book_name");
                int numOfPages = rs.getInt("num_pages");
                String book_author = rs.getString("book_author");
                boolean isRare = rs.getBoolean("is_rare");
                boolean isBorrowed = rs.getBoolean("is_borrowed");

                String rare;
                String borrowed;

                if (isRare) {
                    rare = "Yes";
                } else {
                    rare = "No";
                }

                if (isBorrowed) {
                    borrowed = "Yes this is currently borrowed";
                } else {
                    borrowed = "This book is not currently borrowed and is available";
                }

                System.out.printf("Book Name : %s\n", name);
                System.out.printf("Number of Pages : %d\n", numOfPages);
                System.out.printf("Author : %s\n", book_author);
                System.out.printf("Is this a rare book? : %s\n", rare);
                System.out.printf("Is this book currently borrowed : %s\n\n", borrowed);

                if (isBorrowed) {
                    System.out.println("Sorry you cannot borrow that books as it is on loan to someone else");

                } else {
                    try (Connection conn2 = DBConnection.getConnection();
                         PreparedStatement statement2 = conn2.prepareStatement(UPDATE_BORROW_BOOK)) {


                        statement2.setInt(1, Session.getLoggedInCustomer().getId());
                        statement2.setBoolean(2, true);
                        statement2.setInt(3, bookID);

                        statement2.executeUpdate();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public void displayCustomerBooks() {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(DISPLAY_BOOKS)) {


            statement.setInt(1, Session.getLoggedInCustomer().getId());

            ResultSet rs = statement.executeQuery();


            while (rs.next()) {

                String name = rs.getString("book_name");
                String author = rs.getString("book_author");

                System.out.println(name + " -> " + author);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void customerReturnBooks(int bookID) {

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(RETURN_BOOK)) {

            statement.setInt(1, bookID);

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
