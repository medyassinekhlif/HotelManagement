package GuestStay;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import Room.Room;


public class GuestStayInterface {

    public void bookGuestStay() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Guest ID Info: ");
            String idInfo = scanner.nextLine();
            System.out.print("Enter Guest Date of Birth (yyyy-MM-dd): ");
            Date dateOfBirth = parseDateInput(scanner);
            System.out.print("Enter Guest Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Guest Contact Info: ");
            String contactInfo = scanner.nextLine();
            System.out.print("Enter Guest Address: ");
            String address = scanner.nextLine();
            int guestId = parseIntInput(scanner, "Enter Guest ID: ");
            int bookerId = parseIntInput(scanner, "Enter Booker ID: ");
            System.out.print("Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4): ");
            Room.RoomType roomType = parseRoomType(scanner);
            System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
            Date dateIn = parseDateInput(scanner);
            System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
            Date dateOut = parseDateInput(scanner);

            // Create the GuestStay object
            GuestStay guestStay = new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId, roomType, dateIn, dateOut);
            guestStay.recalculateFields();
            GuestStayDAL.addGuestStay(guestStay);

            System.out.println("Guest stay booked successfully!");
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Helper methods like parseDateInput(), parseIntInput(), etc.
    private Date parseDateInput(Scanner scanner) {
        while (true) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.print("Invalid date format. Please use yyyy-MM-dd: ");
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
}
