/**
 * This class represents a customer
 */
public class Customer {
    
    private int customerId;
    private String username = "";
    private String loginPassword = "";
    private String myName = "";
    private String phoneNumber = "";
    
    public int getCustomerId() {
        return customerId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public Customer(int customerId, String username, 
            String loginPassword, String myName, String phoneNumber) {
        this.customerId = customerId;
        this.username = username;
        this.loginPassword = loginPassword;
        this.myName = myName;
        this.phoneNumber = phoneNumber;
    }
    
}
