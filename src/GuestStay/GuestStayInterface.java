package GuestStay;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import Room.Room;

public class GuestStayInterface {

    public void bookGuestStay(Scanner scanner) { // Accept scanner as a parameter
        try {
            System.out.println("--- Guest Stay Booking ---");

            String idInfo = getStringInput(scanner, "Enter Guest CIN: ");
            Date dateOfBirth = parseDateInput(scanner, "Enter Guest Date of Birth (yyyy-MM-dd): ");
            String fullName = getStringInput(scanner, "Enter Guest Full Name: ");
            String contactInfo = getStringInput(scanner, "Enter Guest Contact Info: ");
            String address = getStringInput(scanner, "Enter Guest Address: ");
            int guestId = parseIntInput(scanner, "Enter Guest ID: ");
            int bookerId = parseIntInput(scanner, "Enter Booker ID: ");
            Room.RoomType roomType = parseRoomType(scanner, "Enter Room Type (S1/S2/D1/D2/E1/E2/F3/F4): ");
            Date dateIn = parseDateInput(scanner, "Enter Check-In Date (yyyy-MM-dd): ");
            Date dateOut = parseDateInput(scanner, "Enter Check-Out Date (yyyy-MM-dd): ");

            if (!dateIn.before(dateOut)) {
                throw new IllegalArgumentException("Check-In Date must be before Check-Out Date.");
            }

            GuestStay guestStay = new GuestStay(idInfo, dateOfBirth, fullName, contactInfo, address, guestId, bookerId,
                    roomType, dateIn, dateOut);

            GuestStayDAL.addGuestStay(guestStay);

            System.out.println("Guest stay booked successfully!");
        } catch (SQLException | IllegalArgumentException | ParseException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper Methods
    private String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private Date parseDateInput(Scanner scanner, String prompt) throws ParseException {
        while (true) {
            try {
                System.out.print(prompt);
                return new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine().trim());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    private Room.RoomType parseRoomType(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Room.RoomType.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out
                        .println("Invalid room type. Please enter a valid type (S1/S2/D1/D2/E1/E2/F3/F4). Try again.");
            }
        }
    }

    private int parseIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
