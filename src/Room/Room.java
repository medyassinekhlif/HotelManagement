package Room;

public class Room {
    // Room types
    public enum RoomType {
        S1, S2, D1, D2, E1, E2, F3, F4
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

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    // Methods
    public int getRoomCapacity() {
        switch (roomType) {
            case S1:
            case D1:
            case E1:
                return 1;
            case S2:
            case D2:
            case E2:
                return 2;
            case F3:
                return 3;
            case F4:
                return 4;
            default:
                return 0;
        }
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
