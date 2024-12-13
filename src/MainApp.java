import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Utils.DbConnection;
import Room.RoomInterface;
import GuestStay.GuestStayInterface;

public class MainApp {

    public static void main(String[] args) {
        // Test the database connection
        if (testDatabaseConnection()) {
            System.out.println("Connected to MySQL DB");

            // Create a single scanner instance
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Hotel Management System ---");
                System.out.println("1. Manage Rooms");
                System.out.println("2. Manage Guest Stays");
                System.out.println("3. Exit");

                int choice = parseIntInput(scanner, "Enter your choice: ");

                switch (choice) {
                    case 1 -> manageRooms(scanner);
                    case 2 -> manageGuestStays(scanner);
                    case 3 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();  // Close scanner only at the end of the program
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Database connection failed! Exiting...");
        }
    }

    // Method to test the database connection
    private static boolean testDatabaseConnection() {
        try (Connection conn = DbConnection.getConnection()) {
            return conn != null; // Connection successful
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return false; // Connection failed
    }

    // Helper method to parse integer input
    private static int parseIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method to manage rooms with the option to go back to the main menu
    private static void manageRooms(Scanner scanner) {
        RoomInterface roomInterface = new RoomInterface();
        while (true) {
            System.out.println("\n--- Manage Rooms ---");
            roomInterface.manageRooms();  // Assuming this method allows adding, updating rooms, etc.
            System.out.println("\nEnter 'b' to go back to the main menu or any other key to continue managing rooms.");
            String backOption = scanner.nextLine();
            if (backOption.equalsIgnoreCase("b")) {
                break;
            }
        }
    }

    // Method to manage guest stays with the option to go back to the main menu
    private static void manageGuestStays(Scanner scanner) {
        GuestStayInterface guestStayInterface = new GuestStayInterface();
        while (true) {
            System.out.println("\n--- Manage Guest Stays ---");
            guestStayInterface.bookGuestStay(scanner);  // Pass the scanner to this method
            System.out.println("\nEnter 'b' to go back to the main menu or any other key to continue managing guest stays.");
            String backOption = scanner.nextLine();
            if (backOption.equalsIgnoreCase("b")) {
                break;
            }
        }
    }
}
