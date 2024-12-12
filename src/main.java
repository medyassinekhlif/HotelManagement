import java.sql.*;


public class main {
    public static void main(String[] args) {
        // Test the database connection
        if (testDatabaseConnection()) {
            System.out.println("Database connection successful!");
            // Proceed to manage hotel admin dashboard
            HotelAdmin admin = new HotelAdmin();
            admin.manageDashboard();
        } else {
            System.out.println("Database connection failed! Exiting...");
        }
    }

    // Method to test the database connection
    private static boolean testDatabaseConnection() {
        try (Connection conn = DbConnection.getConnection()) {
            if (conn != null) {
                return true; // Connection successful
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return false; // Connection failed
    }
}
