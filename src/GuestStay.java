import java.util.Date;

public class GuestStay {
    private int guestId;
    private int bookerId; // ID of the person who booked the room
    private Room.RoomType roomType; // Type of the room booked
    private Date dateIn;
    private Date dateOut;
    private int nightsSpent;
    private double amountToPay;

    public GuestStay(int guestId, int bookerId, Room.RoomType roomType, Date dateIn, Date dateOut) {
        this.guestId = guestId;
        this.bookerId = bookerId;
        this.roomType = roomType;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.nightsSpent = calculateNightsSpent();
        this.amountToPay = calculateAmountToPay();
    }

    private int calculateNightsSpent() {
        long diff = dateOut.getTime() - dateIn.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    private double calculateAmountToPay() {
        return nightsSpent * getRoomPrice();
    }

    private double getRoomPrice() {
        // Price per night for a specific room type can be set based on RoomType.
        switch (roomType) {
            case S1: return 100;
            case S2: return 120;
            case D1: return 150;
            case D2: return 170;
            case E1: return 200;
            case E2: return 220;
            case F1: return 250;
            case F2: return 270;
            case F3: return 300;
            case F4: return 350;
            default: return 0;
        }
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    @Override
    public String toString() {
        return "Guest ID: " + guestId + ", Booker ID: " + bookerId + ", Room Type: " + roomType +
                ", Dates: " + dateIn + " to " + dateOut + ", Nights: " + nightsSpent + ", Total: " + amountToPay;
    }
}
