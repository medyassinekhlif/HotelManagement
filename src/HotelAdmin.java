import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Scanner;

public class HotelAdmin {
    private List<Room> rooms;
    private List<Booking> bookings;

    public HotelAdmin() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room added: " + room);
    }

    public void updateRoom(String roomId, Room newRoom) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getId().equals(roomId)) {
                rooms.set(i, newRoom);
                System.out.println("Room updated: " + newRoom);
                return;
            }
        }
        System.out.println("Room not found with ID: " + roomId);
    }

    public void removeRoom(String roomId) {
        rooms.removeIf(room -> room.getId().equals(roomId));
        System.out.println("Room removed with ID: " + roomId);
    }

    public boolean bookRoom(int guestId, int bookerId, Room.RoomType roomType, Date dateIn, Date dateOut) {
        // Check room availability
        Room availableRoom = null;
        for (Room room : rooms) {
            if (room.getRoomType() == roomType && room.getNumberOfRooms() > 0) {
                availableRoom = room;
                break;
            }
        }

        if (availableRoom == null) {
            System.out.println("No available rooms of this type.");
            return false;
        }

        // Book the room
        availableRoom.reduceAvailableRooms();

        // Create GuestStay and Booking
        GuestStay guestStay = new GuestStay(guestId, bookerId, roomType, dateIn, dateOut);
        Booking booking = findOrCreateBooking(bookerId);
        booking.addGuestStay(guestStay);

        System.out.println("Booking successful. " + guestStay);
        return true;
    }

    private Booking findOrCreateBooking(int bookerId) {
        for (Booking booking : bookings) {
            if (booking.getBookerId() == bookerId) {
                return booking;
            }
        }

        Booking newBooking = new Booking(bookerId);
        bookings.add(newBooking);
        return newBooking;
    }

    public void displayRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
    }

    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }
}
