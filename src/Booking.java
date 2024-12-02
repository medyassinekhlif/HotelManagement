import java.util.List;
import java.util.ArrayList;

public class Booking {
    private int bookerId;
    private List<GuestStay> guestStays;
    private double totalAmount;

    public Booking(int bookerId) {
        this.bookerId = bookerId;
        this.guestStays = new ArrayList<>();
        this.totalAmount = 0;
    }

    public int getBookerId() {
        return bookerId;
    }

    public void addGuestStay(GuestStay guestStay) {
        guestStays.add(guestStay);
        totalAmount += guestStay.getAmountToPay();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ID: ").append(bookerId).append("\n");
        for (GuestStay stay : guestStays) {
            sb.append(stay).append("\n");
        }
        sb.append("Total Bill: ").append(totalAmount);
        return sb.toString();
    }
}
