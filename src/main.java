import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelAdmin admin = new HotelAdmin();

        // Example rooms to initialize the hotel system
        Room room1 = new Room("R001", "room1.jpg", 100, Room.RoomType.S1, 10);
        Room room2 = new Room("R002", "room2.jpg", 150, Room.RoomType.D1, 5);
        admin.addRoom(room1);
        admin.addRoom(room2);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Add Room");
            System.out.println("2. Update Room");
            System.out.println("3. Delete Room");
            System.out.println("4. Book Room");
            System.out.println("5. Display Rooms");
            System.out.println("6. Display Bookings");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    // Add a room
                    System.out.println("Enter Room ID: ");
                    String id = scanner.nextLine();
                    System.out.println("Enter Room Image: ");
                    String image = scanner.nextLine();
                    System.out.println("Enter Price per Night: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline
                    System.out.println("Enter Room Type (S1, D1, E1, etc.): ");
                    Room.RoomType type = Room.RoomType.valueOf(scanner.nextLine());
                    System.out.println("Enter Number of Rooms: ");
                    int numRooms = scanner.nextInt();
                    Room newRoom = new Room(id, image, price, type, numRooms);
                    admin.addRoom(newRoom);
                    break;

                case 2:
                    // Update a room
                    System.out.println("Enter Room ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.println("Enter new Room Image: ");
                    String newImage = scanner.nextLine();
                    System.out.println("Enter new Price per Night: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline
                    System.out.println("Enter new Room Type (S1, D1, E1, etc.): ");
                    Room.RoomType newType = Room.RoomType.valueOf(scanner.nextLine());
                    System.out.println("Enter new Number of Rooms: ");
                    int newNumRooms = scanner.nextInt();
                    Room updatedRoom = new Room(updateId, newImage, newPrice, newType, newNumRooms);
                    admin.updateRoom(updateId, updatedRoom);
                    break;

                case 3:
                    // Delete a room
                    System.out.println("Enter Room ID to delete: ");
                    String deleteId = scanner.nextLine();
                    admin.removeRoom(deleteId);
                    break;

                case 4:
                    // Book a room
                    System.out.println("Enter Booker ID: ");
                    int bookerId = scanner.nextInt();
                    System.out.println("Enter Guest ID: ");
                    int guestId = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    System.out.println("Enter Room Type to book (S1, D1, E1, etc.): ");
                    Room.RoomType roomType = Room.RoomType.valueOf(scanner.nextLine());
                    System.out.println("Enter Check-in Date (yyyy-mm-dd): ");
                    String checkInDate = scanner.nextLine();
                    System.out.println("Enter Check-out Date (yyyy-mm-dd): ");
                    String checkOutDate = scanner.nextLine();

                    // Parse the date strings into Date objects
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateIn = dateFormat.parse(checkInDate);
                        Date dateOut = dateFormat.parse(checkOutDate);
                        admin.bookRoom(guestId, bookerId, roomType, dateIn, dateOut);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                    }
                    break;

                case 5:
                    // Display rooms
                    admin.displayRooms();
                    break;

                case 6:
                    // Display bookings
                    admin.displayBookings();
                    break;

                case 7:
                    // Exit
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
