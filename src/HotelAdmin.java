import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class HotelAdmin {

    public void manageDashboard() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Hotel Admin Dashboard ---");
            System.out.println("1. Add Room");
            System.out.println("2. Update Room");
            System.out.println("3. Delete Room");
            System.out.println("4. View All Rooms");
            System.out.println("5. Add Guest Stay");
            System.out.println("6. View Guest Stays");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addRoom(scanner);
                    case 2 -> updateRoom(scanner);
                    case 3 -> deleteRoom(scanner);
                    case 4 -> viewRooms();
                    case 5 -> addGuestStay(scanner);
                    case 6 -> viewGuestStays();
                    case 7 -> {
                        System.out.println("Exiting Dashboard...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
            }
        }
    }

    private void addRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Room Type (SINGLE/DOUBLE/SUITE): ");
        Room.RoomType roomType = Room.RoomType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Number of Rooms: ");
        int numberOfRooms = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Price Per Night: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Room Image URL: ");
        String roomImage = scanner.nextLine();

        Room room = new Room(id, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.addRoom(room);
        System.out.println("Room added successfully!");
    }

    private void updateRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID to update: ");
        String roomId = scanner.nextLine();
        System.out.print("Enter Updated Room Type (SINGLE/DOUBLE/SUITE): ");
        Room.RoomType roomType = Room.RoomType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Updated Number of Rooms: ");
        int numberOfRooms = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Updated Price Per Night: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Updated Room Image URL: ");
        String roomImage = scanner.nextLine();

        Room updatedRoom = new Room(roomId, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.updateRoom(roomId, updatedRoom);
        System.out.println("Room updated successfully!");
    }

    private void deleteRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID to delete: ");
        String roomId = scanner.nextLine();
        RoomDAL.deleteRoom(roomId);
        System.out.println("Room deleted successfully!");
    }

    private void viewRooms() throws SQLException {
        System.out.println("--- List of Rooms ---");
        for (Room room : RoomDAL.getRooms()) {
            System.out.println(room);
        }
    }

    private void addGuestStay(Scanner scanner) throws SQLException {
        System.out.print("Enter Guest ID Info: ");
        String idInfo = scanner.nextLine();
        System.out.print("Enter Guest Date of Birth (yyyy-MM-dd): ");
        Date dateOfBirth = parseDate(scanner.nextLine());
        System.out.print("Enter Guest Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter Guest Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter Guest Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Guest ID: ");
        int guestId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Booker ID: ");
        int bookerId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Room Type (SINGLE/DOUBLE/SUITE): ");
        Room.RoomType roomType = Room.RoomType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
        Date dateIn = parseDate(scanner.nextLine());
        System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
        Date dateOut = parseDate(scanner.nextLine());

        GuestStay guestStay = new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut);
        GuestStayDAL.addGuestStay(guestStay);
        System.out.println("Guest stay added successfully!");
    }

    private void viewGuestStays() throws SQLException {
        System.out.println("--- List of Guest Stays ---");
        for (GuestStay guestStay : GuestStayDAL.getGuestStays()) {
            System.out.println(guestStay);
        }
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }
}
