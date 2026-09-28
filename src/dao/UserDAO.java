package dao;

import model.Customer;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class UserDAO {


    public Set<Integer> searchBooks(String searchTerm){
        String sql = "SELECT * FROM books WHERE book_name LIKE ? OR book_author LIKE ?";
        Set<Integer> displayedBookIDs = new HashSet<>();

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){

            String wildcardSearchTerm = "%" + searchTerm + "%";

            statement.setString(1, wildcardSearchTerm);
            statement.setString(2, wildcardSearchTerm);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                int bookID = rs.getInt("book_id");
                displayedBookIDs.add(bookID);

                String name = rs.getString("book_name");
                int numOfPages = rs.getInt("num_pages");
                String book_author = rs.getString("book_author");
                boolean isRare = rs.getBoolean("is_rare");
                boolean isBorrowed = rs.getBoolean("is_borrowed");

                String rare;
                String borrowed;

                if(isRare){
                     rare = "Yes";
                }
                else{
                     rare = "No";
                }

                if(isBorrowed){
                     borrowed = "Yes this is currently borrowed";
                }
                else{
                     borrowed = "This book is not currently borrowed and is available";
                }

                System.out.printf("BOOK ID : %d\n", bookID);
                System.out.printf("Book Name : %s\n", name);
                System.out.printf("Number of Pages : %d\n", numOfPages);
                System.out.printf("Author : %s\n", book_author);
                System.out.printf("Is this a rare book? : %s\n", rare);
                System.out.printf("Is this book currently borrowed : %s\n\n", borrowed);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return displayedBookIDs;

    }




}
