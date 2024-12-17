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
}
