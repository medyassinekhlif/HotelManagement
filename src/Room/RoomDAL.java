package Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DbConnection;

public class RoomDAL {
    // Add a room to the database
    public static void addRoom(Room room) throws SQLException {
        String query = "INSERT INTO rooms (roomType, numberOfRooms, pricePerNight, roomImage) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, room.getRoomType().name()); // Set enum name
            ps.setInt(2, room.getNumberOfRooms());
            ps.setDouble(3, room.getPricePerNight());
            ps.setString(4, room.getRoomImage());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            throw e;
        }
    }

    // Update room details in the database
    public static void updateRoom(Room.RoomType roomType, Room updatedRoom) throws SQLException {
        String query = "UPDATE rooms SET numberOfRooms = ?, pricePerNight = ?, roomImage = ? WHERE roomType = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, updatedRoom.getNumberOfRooms());
            ps.setDouble(2, updatedRoom.getPricePerNight());
            ps.setString(3, updatedRoom.getRoomImage());
            ps.setString(4, roomType.name()); // Use enum name for roomType

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
            throw e;
        }
    }

    // Delete a room from the database
    public static void deleteRoom(Room.RoomType roomType) throws SQLException {
        String query = "DELETE FROM rooms WHERE roomType = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, roomType.name()); // Use enum name for roomType
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
            throw e;
        }
    }

    // Retrieve all rooms from the database
    public static List<Room> getRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT roomType, numberOfRooms, pricePerNight, roomImage FROM rooms";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String roomTypeString = rs.getString("roomType");
                Room.RoomType roomType = getValidRoomType(roomTypeString);
                int numberOfRooms = rs.getInt("numberOfRooms");
                double pricePerNight = rs.getDouble("pricePerNight");
                String roomImage = rs.getString("roomImage");

                rooms.add(new Room(roomImage, pricePerNight, roomType, numberOfRooms));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving rooms: " + e.getMessage());
            throw e;
        }
        return rooms;
    }

    // Retrieve a single room by roomType
    public static Room getRoomById(Room.RoomType roomType) throws SQLException {
        String query = "SELECT roomType, numberOfRooms, pricePerNight, roomImage FROM rooms WHERE roomType = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, roomType.name()); // Use enum name for roomType
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numberOfRooms = rs.getInt("numberOfRooms");
                    double pricePerNight = rs.getDouble("pricePerNight");
                    String roomImage = rs.getString("roomImage");
                    return new Room(roomImage, pricePerNight, roomType, numberOfRooms);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving room by ID: " + e.getMessage());
            throw e;
        }
        return null; // Room not found
    }

    // Helper method to safely get a valid RoomType
    private static Room.RoomType getValidRoomType(String roomTypeString) {
        try {
            return Room.RoomType.valueOf(roomTypeString);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid room type: " + roomTypeString + ". Defaulting to 'S1'.");
            return Room.RoomType.S1; // Default room type if invalid value
        }
    }
}
