/**
 * This class represents a Reservation
 */
public class Reservation {
    
    private int reservationId;
    private String reservationTimestamp = "";
    private String reservationDuration = "";
    private int restaurantId;
    private int customerId;
    private int partyCount;
    
    public int getReservationId() {
        return reservationId;
    }

    public String getReservationTimestamp() {
        return reservationTimestamp;
    }

    public String getReservationDuration() {
        return reservationDuration;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPartyCount() {
        return partyCount;
    }

    public Reservation(int reservationId, String reservationTimestamp, 
            String reservationDuration, int restaurantId, int customerId,
            int partyCount) {
        this.reservationId = reservationId;
        this.reservationTimestamp = reservationTimestamp;
        this.reservationDuration = reservationDuration;
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.partyCount = partyCount;
    }

}
