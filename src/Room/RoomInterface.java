package Room;
import java.sql.SQLException;
import java.util.Scanner;


public class RoomInterface {
    public void manageRooms() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Manage Rooms ---");
            System.out.println("1. Add Room");
            System.out.println("2. Update Room");
            System.out.println("3. Delete Room");
            System.out.println("4. View All Rooms");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = parseIntInput(scanner, "Please enter a valid number: ");

            try {
                switch (choice) {
                    case 1 -> addRoom(scanner);
                    case 2 -> updateRoom(scanner);
                    case 3 -> deleteRoom(scanner);
                    case 4 -> viewRooms();
                    case 5 -> {
                        return; // Go back to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
            }
        }
    }

    public void addRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4): ");
        Room.RoomType roomType = parseRoomType(scanner);
        int numberOfRooms = parseIntInput(scanner, "Enter Number of Rooms: ");
        System.out.print("Enter Price Per Night: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Room Image URL: ");
        String roomImage = scanner.nextLine();

        Room room = new Room(id, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.addRoom(room);
        System.out.println("Room added successfully!");
    }

    public void updateRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID to update: ");
        String roomId = scanner.nextLine();
        System.out.print("Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4): ");
        Room.RoomType roomType = parseRoomType(scanner);
        int numberOfRooms = parseIntInput(scanner, "Enter Updated Number of Rooms: ");
        System.out.print("Enter Updated Price Per Night: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Updated Room Image URL: ");
        String roomImage = scanner.nextLine();

        Room updatedRoom = new Room(roomId, roomImage, pricePerNight, roomType, numberOfRooms);
        RoomDAL.updateRoom(roomId, updatedRoom);
        System.out.println("Room updated successfully!");
    }

    public void deleteRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter Room ID to delete: ");
        String roomId = scanner.nextLine();
        RoomDAL.deleteRoom(roomId);
        System.out.println("Room deleted successfully!");
    }

    public void viewRooms() throws SQLException {
        System.out.println("--- List of Rooms ---");
        for (Room room : RoomDAL.getRooms()) {
            System.out.println(room);
        }
    }

    private int parseIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private Room.RoomType parseRoomType(Scanner scanner) {
        while (true) {
            try {
                return Room.RoomType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid room type. Please enter a valid type (SINGLE/DOUBLE/SUITE): ");
            }
        }
    }
}
