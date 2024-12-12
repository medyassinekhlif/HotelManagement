import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestStayDAL {
    public static void addGuestStay(GuestStay guestStay) throws SQLException {
        String query = "INSERT INTO guest_stays (idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut, nightsSpent, amountToPay) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, guestStay.getIdInfo());
            ps.setDate(2, new java.sql.Date(guestStay.getDateOfBirth().getTime()));
            ps.setString(3, guestStay.getFullName());
            ps.setString(4, guestStay.getContactInfo());
            ps.setString(5, guestStay.getAddress());
            ps.setInt(6, guestStay.getGuestId());
            ps.setInt(7, guestStay.getBookerId());
            ps.setString(8, guestStay.getRoomType().name());
            ps.setDate(9, new java.sql.Date(guestStay.getDateIn().getTime()));
            ps.setDate(10, new java.sql.Date(guestStay.getDateOut().getTime()));
            ps.setInt(11, guestStay.getNightsSpent()); // Nights spent
            ps.setDouble(12, guestStay.getAmountToPay()); // Amount to pay
            ps.executeUpdate();
        }
    }
    
    public static List<GuestStay> getGuestStays() throws SQLException {
        List<GuestStay> guestStays = new ArrayList<>();
        String query = "SELECT * FROM guestStays";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
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
                // int nightsSpent = rs.getInt("nightsSpent");
                // double amountToPay = rs.getDouble("amountToPay");
                guestStays.add(new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut));
            }
        }
        return guestStays;
    }
}
