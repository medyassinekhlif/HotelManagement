package GuestStay;

import java.util.Date;
import Room.Room;

public class GuestStay {

    private String idInfo;
    private Date dateOfBirth;
    private String fullName;
    private String contactInfo;
    private String address;
    private int guestId;
    private int bookerId;
    private Room.RoomType roomType;  // RoomType instead of Room
    private Date dateIn;
    private Date dateOut;
    private int nightsSpent;
    private double amountToPay;

    // Constructor
    public GuestStay(String idInfo, Date dateOfBirth, String fullName, String contactInfo, String address,
                     int guestId, int bookerId, Room.RoomType roomType, Date dateIn, Date dateOut) {
        this.idInfo = idInfo;
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.guestId = guestId;
        this.bookerId = bookerId;
        this.roomType = roomType;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        recalculateFields(); // Initialize dependent fields
    }

    // Recalculate nights spent and amount to pay
    public void recalculateFields() {
        this.nightsSpent = calculateNightsSpent();
        this.amountToPay = calculateAmountToPay();
    }

    // Calculate nights spent
    public int calculateNightsSpent() {
        if (dateIn == null || dateOut == null) {
            return 0; // Default to 0 if dates are invalid
        }
        long diff = dateOut.getTime() - dateIn.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    // Calculate amount to pay
    public double calculateAmountToPay() {
        double basePrice = nightsSpent * getRoomPrice();

        // Apply discount based on age
        if (dateOfBirth != null) {
            long ageInMs = new Date().getTime() - dateOfBirth.getTime();
            int age = (int) (ageInMs / (365.25 * 24 * 60 * 60 * 1000));
            if (age >= 65) {
                return basePrice * 0.9; // 10% discount for seniors
            }
        }
        return basePrice;
    }

    // Get room price based on type
    private double getRoomPrice() {
        switch (roomType) {
            case S1: return 100;
            case S2: return 120;
            case D1: return 150;
            case D2: return 170;
            case E1: return 200;
            case E2: return 220;
            case F3: return 300;
            case F4: return 350;
            default: return 0;
        }
    }

    // Getters and Setters

    public String getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(String idInfo) {
        this.idInfo = idInfo;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        recalculateFields(); // Update dependent fields
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getBookerId() {
        return bookerId;
    }

    public void setBookerId(int bookerId) {
        this.bookerId = bookerId;
    }

    public Room.RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(Room.RoomType roomType) {
        this.roomType = roomType;
        recalculateFields(); // Update dependent fields
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
        recalculateFields(); // Update dependent fields
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
        recalculateFields(); // Update dependent fields
    }

    public int getNightsSpent() {
        return nightsSpent;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    // Override toString for better display of information
    @Override
    public String toString() {
        return "Guest ID: " + guestId + ", Booker ID: " + bookerId + ", Room Type: " + roomType +
                ", Dates: " + dateIn + " to " + dateOut + ", Nights: " + nightsSpent + ", Total: " + amountToPay;
    }
}
