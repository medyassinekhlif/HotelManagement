package Room;
import java.sql.*; 
import java.util.ArrayList;
import java.util.List;

import Utils.DbConnection;

public class RoomDAL {
    public static void addRoom(Room room) throws SQLException {
        String query = "INSERT INTO rooms (id, roomType, numberOfRooms, pricePerNight, roomImage) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, room.getId());
            ps.setString(2, room.getRoomType().name());
            ps.setInt(3, room.getNumberOfRooms());
            ps.setDouble(4, room.getPricePerNight());
            ps.setString(5, room.getRoomImage());
            ps.executeUpdate();
        }
    }

    public static void updateRoom(String roomId, Room updatedRoom) throws SQLException {
        String query = "UPDATE rooms SET roomType = ?, numberOfRooms = ?, pricePerNight = ?, roomImage = ? WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, updatedRoom.getRoomType().name());
            ps.setInt(2, updatedRoom.getNumberOfRooms());
            ps.setDouble(3, updatedRoom.getPricePerNight());
            ps.setString(4, updatedRoom.getRoomImage());
            ps.setString(5, roomId);
            ps.executeUpdate();
        }
    }

    public static void deleteRoom(String roomId) throws SQLException {
        String query = "DELETE FROM rooms WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, roomId);
            ps.executeUpdate();
        }
    }
    public static List<Room> getRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT id, roomType, numberOfRooms, pricePerNight, roomImage FROM rooms";
        try (Connection conn = DbConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                String id = rs.getString("id");
                String roomTypeString = rs.getString("roomType");
    
                // Check and convert roomType safely
                Room.RoomType roomType = getValidRoomType(roomTypeString);
    
                int numberOfRooms = rs.getInt("numberOfRooms");
                double pricePerNight = rs.getDouble("pricePerNight");
                String roomImage = rs.getString("roomImage");
    
                rooms.add(new Room(id, roomImage, pricePerNight, roomType, numberOfRooms));
            }
        }
        return rooms;
    }
    
    private static Room.RoomType getValidRoomType(String roomTypeString) {
        try {
            return Room.RoomType.valueOf(roomTypeString); // Tries to get valid RoomType
        } catch (IllegalArgumentException e) {
            // Log the invalid roomType and default to S1 if invalid
            System.err.println("Invalid room type: " + roomTypeString + ". Defaulting to 'S1'.");
            return Room.RoomType.S1; // Default to S1 if roomType is invalid
        }
    }
    
    public static Room getRoomById(String roomId) throws SQLException {
        String query = "SELECT id, roomType, numberOfRooms, pricePerNight, roomImage FROM rooms WHERE id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("id");
                    Room.RoomType roomType = Room.RoomType.valueOf(rs.getString("roomType"));
                    int numberOfRooms = rs.getInt("numberOfRooms");
                    double pricePerNight = rs.getDouble("pricePerNight");
                    String roomImage = rs.getString("roomImage");
                    return new Room(id, roomImage, pricePerNight, roomType, numberOfRooms);
                }
            }
        }
        return null; // Room not found
    }
}
