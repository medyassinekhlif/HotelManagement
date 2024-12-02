public class Room {
    // Room types
    public enum RoomType {
        S1, S2, D1, D2, E1, E2, F1, F2, F3, F4
    }

    private String id;
    private String roomImage;
    private double pricePerNight;
    private RoomType roomType;
    private int numberOfRooms; // Number of available rooms of this type

    public Room(String id, String roomImage, double pricePerNight, RoomType roomType, int numberOfRooms) {
        this.id = id;
        this.roomImage = roomImage;
        this.pricePerNight = pricePerNight;
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
    }

    public String getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void reduceAvailableRooms() {
        if (numberOfRooms > 0) {
            numberOfRooms--;
        }
    }

    public void increaseAvailableRooms() {
        numberOfRooms++;
    }

    @Override
    public String toString() {
        return "Room ID: " + id + ", Type: " + roomType + ", Price: " + pricePerNight + ", Available Rooms: " + numberOfRooms;
    }
}
