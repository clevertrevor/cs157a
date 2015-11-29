/**
 * This class represents a restaurant
 */
public class Restaurant {
    
    private int restaurantId;
    private String restaurantName = "";
    private int capacity;
    
    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getCapacity() {
        return capacity;
    }

    public Restaurant(int restaurantId, String restaurantName, int capacity) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.capacity = capacity;
    }
    
}
