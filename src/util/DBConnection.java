package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String jdbcURL = "jdbc:postgresql://localhost:5432/Library";
    private static final String username = "postgres";
    private static final String password = "papillon";


    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(jdbcURL, username, password);

        } catch (SQLException e){
            throw new RuntimeException("Database connection failed", e);
        }
    }





}
