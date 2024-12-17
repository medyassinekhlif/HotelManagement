package Utils;

import java.sql.*;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/zentara";  
    private static final String USER = "root";  
    private static final String PASSWORD = "0000"; 
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
        return null;
    }
}
