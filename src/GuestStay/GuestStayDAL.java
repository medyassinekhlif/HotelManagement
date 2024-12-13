package GuestStay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Room.Room;
import Utils.DbConnection;
import java.text.SimpleDateFormat; // Add this import

public class GuestStayDAL {
    public static void addGuestStay(GuestStay guestStay) throws SQLException {
        String query = "INSERT INTO gueststays (idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut, nightsSpent, amountToPay) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, guestStay.getIdInfo());
            ps.setDate(2, toSqlDate(guestStay.getDateOfBirth())); // Convert java.util.Date to java.sql.Date
            ps.setString(3, guestStay.getFullName());
            ps.setString(4, guestStay.getContactInfo());
            ps.setString(5, guestStay.getAddress());
            ps.setInt(6, guestStay.getGuestId());
            ps.setInt(7, guestStay.getBookerId());
            ps.setString(8, guestStay.getRoomType().name());
            ps.setDate(9, toSqlDate(guestStay.getDateIn())); // Convert java.util.Date to java.sql.Date
            ps.setDate(10, toSqlDate(guestStay.getDateOut())); // Convert java.util.Date to java.sql.Date
            ps.setInt(11, guestStay.getNightsSpent());
            ps.setDouble(12, guestStay.getAmountToPay());

            ps.executeUpdate();
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