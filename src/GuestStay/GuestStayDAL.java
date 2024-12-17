package GuestStay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Room.Room;
import Utils.DbConnection;
import java.text.SimpleDateFormat;

public class GuestStayDAL {
    public static void addGuestStay(GuestStay guestStay) throws SQLException {
        String insertGuestStayQuery = "INSERT INTO gueststays (idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut, nightsSpent, amountToPay) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateRoomsQuery = "UPDATE rooms SET numberOfRooms = numberOfRooms - 1 WHERE roomType = ? AND numberOfRooms > 0";
    
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement psGuestStay = conn.prepareStatement(insertGuestStayQuery);
             PreparedStatement psUpdateRooms = conn.prepareStatement(updateRoomsQuery)) {
    
            // Insert into gueststays
            psGuestStay.setString(1, guestStay.getIdInfo());
            psGuestStay.setDate(2, toSqlDate(guestStay.getDateOfBirth())); // Convert java.util.Date to java.sql.Date
            psGuestStay.setString(3, guestStay.getFullName());
            psGuestStay.setString(4, guestStay.getContactInfo());
            psGuestStay.setString(5, guestStay.getAddress());
            psGuestStay.setInt(6, guestStay.getGuestId());
            psGuestStay.setInt(7, guestStay.getBookerId());
            psGuestStay.setString(8, guestStay.getRoomType().name());
            psGuestStay.setDate(9, toSqlDate(guestStay.getDateIn())); // Convert java.util.Date to java.sql.Date
            psGuestStay.setDate(10, toSqlDate(guestStay.getDateOut())); // Convert java.util.Date to java.sql.Date
            psGuestStay.setInt(11, guestStay.getNightsSpent());
            psGuestStay.setDouble(12, guestStay.getAmountToPay());
    
            psGuestStay.executeUpdate();
    
            // Update number of rooms
            psUpdateRooms.setString(1, guestStay.getRoomType().name());
            int rowsAffected = psUpdateRooms.executeUpdate();
    
            if (rowsAffected == 0) {
                throw new SQLException("Room booking failed: No rooms available for the selected type.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            throw e;
        }
    }
    
    private static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    

    public static List<GuestStay> getGuestStays() throws SQLException {
        List<GuestStay> guestStays = new ArrayList<>();
        String query = "SELECT * FROM guestStays";
        try (Connection conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String idInfo = rs.getString("idInfo");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String fullName = rs.getString("fullName");
                String contactInfo = rs.getString("contactInfo");
                String address = rs.getString("address");
                int guestId = rs.getInt("guestId");
                int bookerId = rs.getInt("bookerId");
                Room.RoomType roomType = Room.RoomType.valueOf(rs.getString("roomType"));
                Date dateIn = rs.getDate("dateIn");
                Date dateOut = rs.getDate("dateOut");
                int nightsSpent = rs.getInt("nightsSpent");
                double amountToPay = rs.getDouble("amountToPay");
                guestStays.add(new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId,
                        roomType, dateIn, dateOut));
            }
        }
        return guestStays;
    }
}