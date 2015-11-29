/**
 * This class represents a Manager
 */
public class Manager {
    
    private int managerId;
    private String username = "";
    private String loginPassword = "";
    private String myName = "";
    private int restaurantId;
    
    public int getmanagerId() {
        return managerId;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public String getMyName() {
        return myName;
    }

    public int getrestaurantId() {
        return restaurantId;
    }
    
    public Manager(int managerId,String myName, String username, 
            String loginPassword, int restaurantId) {
        this.managerId = managerId;
        this.username = username;
        this.loginPassword = loginPassword;
        this.myName = myName;
        this.restaurantId = restaurantId;
    }
    
}
