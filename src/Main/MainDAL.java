package Main;

import java.sql.*;
import Utils.DbConnection;

public class MainDAL {

    // Method to get the number of rooms
    public static int getNumberOfRooms() throws SQLException {
        String query = "SELECT SUM(numberOfRooms) AS totalRooms FROM rooms";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalRooms"); // Return the total number of rooms
            }
        }
        return 0; // Return 0 if no rooms are found
    }

    // Method to get the number of guests
    public static int getNumberOfGuests() throws SQLException {
        String query = "SELECT COUNT(*) AS totalGuests FROM gueststays";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalGuests"); // Return the total number of guests
            }
        }
        return 0; // Return 0 if no guests are found
    }

    // Method to get the number of customer service records
    public static int getNumberOfCustomerServiceRecords() throws SQLException {
        String query = "SELECT COUNT(*) AS totalCustomerService FROM customerservice";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalCustomerService"); // Return the total number of customer service records
            }
        }
        return 0; // Return 0 if no customer service records are found
    }

    // Method to get the number of operational records
    public static int getNumberOfOperationalRecords() throws SQLException {
        String query = "SELECT COUNT(*) AS totalOperational FROM operational";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalOperational"); // Return the total number of operational records
            }
        }
        return 0; // Return 0 if no operational records are found
    }

    // Method to get the number of managerial records
    public static int getNumberOfManagerialRecords() throws SQLException {
        String query = "SELECT COUNT(*) AS totalManagerial FROM managerial";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalManagerial"); // Return the total number of managerial records
            }
        }
        return 0; // Return 0 if no managerial records are found
    }

    // Method to get the number of technical records
    public static int getNumberOfTechnicalRecords() throws SQLException {
        String query = "SELECT COUNT(*) AS totalTechnical FROM technical";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalTechnical"); // Return the total number of technical records
            }
        }
        return 0; // Return 0 if no technical records are found
    }
}