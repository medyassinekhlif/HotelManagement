// import java.util.ArrayList;
// import java.util.List;

// public class AgencyBooking {
//     private int agencyId;
//     private List<Booking> bookings;

//     public AgencyBooking(int agencyId) {
//         this.agencyId = agencyId;
//         this.bookings = new ArrayList<>();
//     }

//     public int getAgencyId() {
//         return this.agencyId;
//     }

//     public void addBooking(Booking booking) {
//         this.bookings.add(booking);
//     }

//     public double getTotalAmount() {
//         double total = 0.0;
//         for (Booking booking : bookings) {
//             total += booking.getTotalAmount();
//         }
//         return total;
//     }

//     @Override
//     public String toString() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("Agency ID: ").append(this.agencyId).append("\n");
//         for (Booking booking : bookings) {
//             sb.append(booking).append("\n");
//         }
//         sb.append("Total Amount for Agency: ").append(this.getTotalAmount());
//         return sb.toString();
//     }
// }
