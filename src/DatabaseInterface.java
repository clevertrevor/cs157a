import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class interfaces with the UI to provide DB functionality 
 */
public class DatabaseInterface {
    
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private Scanner inputScanner = new Scanner(System.in);
    
    public DatabaseInterface() {}
    
    /**
     * Connect to the database
     * @return boolean success or failure of connection
     */
    public boolean connectDB() {   
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/RReservation","root", "1234");
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        
        if (this.connection != null) return true;
        else return false;
    }

    /**
     * Inserts a customer into the database
     * @param username
     * @param password
     * @param object
     * @param name
     * @param phone_number
     * @return success/failure of inserting a customer
     */
    public boolean insertCustomer(String username, String password,
            String name, String phone_number) {
        try {
            preparedStatement = this.connection.prepareStatement("SELECT my_name FROM Customer WHERE my_name=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                System.out.println("The username has already existed!");
                return false;
            } else {
                
                preparedStatement = this.connection.prepareStatement("INSERT INTO Customer (username, login_password, my_name, phone_number) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, phone_number);
                preparedStatement.executeUpdate();
                System.out.println("Account created.");
                return true;
                    
            }
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean insertManager(String name, String username, String password,
            String restaurantId) {
        try {
            preparedStatement = this.connection.prepareStatement("SELECT my_name FROM Manager WHERE my_name=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                System.out.println("The username already exists!");
                return false;
            } else {
                
                preparedStatement = this.connection.prepareStatement("INSERT INTO Manager (my_name, username, login_password, restaurant_id) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, restaurantId);
                preparedStatement.executeUpdate();
                System.out.println("Account created.");
                return true;
                    
            }
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public Object systemLogin(String username, String password, boolean isManager){
        try{
            if(isManager == true)
                preparedStatement = this.connection.prepareStatement("SELECT * FROM Manager WHERE username = ? AND login_password = ?");
            else
                preparedStatement = this.connection.prepareStatement("SELECT * FROM Customer WHERE username = ? AND login_password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("my_name");
                if(isManager == true){
                return new Manager(resultSet.getInt("manager_id"), resultSet.getString("my_name"),
                        resultSet.getString("username"), resultSet.getString("login_password"),
                        resultSet.getInt("restaurant_id"));
                }else{
                return new Customer(resultSet.getInt("customer_id"), resultSet.getString("username"), 
                        resultSet.getString("login_password"), resultSet.getString("my_name"),
                            resultSet.getString("phone_number"));
                }
            }
            
        }
        catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    // Returns a list of the customer's reservations
    public List<Reservation> getCustomerReservations(int customerId){
        
        List<Reservation> reservations = new ArrayList<Reservation>();
        
        try {
            preparedStatement = this.connection.prepareStatement(
                    "SELECT * FROM Reservation "
                    + "WHERE customer_id = ? ORDER BY reservation_timestamp");
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String reservationTime = resultSet.getTimestamp("reservation_timestamp").toString();
                String reservationDuration = resultSet.getTime("reservation_duration").toString();
                int partyCount = resultSet.getInt("party_count");
                int customerID = resultSet.getInt("customer_id");
                int reservationID = resultSet.getInt("reservation_id");
                int restaurantId = resultSet.getInt("restaurant_id");
                reservations.add(new Reservation(reservationID, reservationTime, reservationDuration, restaurantId, customerID, partyCount));
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return reservations;
        
    }
    
    // Gets all reservations for a specific restaurant
    public List<Reservation> getAllReservations(int restaurantID){
        List<Reservation> reservations = new ArrayList<Reservation>();
        
        try {
            preparedStatement = this.connection.prepareStatement(
                    "SELECT * FROM Reservation left join Customer on Reservation.customer_id = Customer.customer_id "
                    + "WHERE restaurant_id = ? ORDER BY reservation_timestamp");
            preparedStatement.setInt(1, restaurantID);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String reservationTime = resultSet.getTimestamp("reservation_timestamp").toString();
                String reservationDuration = resultSet.getTime("reservation_duration").toString();
                int partyCount = resultSet.getInt("party_count");
                int customerID = resultSet.getInt("customer_id");
                int reservationID = resultSet.getInt("reservation_id");
                reservations.add(new Reservation(reservationID, reservationTime, reservationDuration, restaurantID, customerID, partyCount));
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return reservations;
    }
    
    public String getCustomerNameByID(int customerID){
        try {
            preparedStatement = this.connection.prepareStatement("SELECT my_name FROM Customer WHERE customer_id=?");
            preparedStatement.setInt(1, customerID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("my_name");
            }
    }catch (SQLException e) {
        System.out.println(e.toString());
        System.out.println(e.getMessage());
    }
        return "No Name Found";
    }
    
        /**
     * Deletes this customer from the database
     * @param customerId
     * @return
     */
    public boolean deleteCustomer(int customerId) {

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE customer_id = " + customerId);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean deleteReservation(int reservationId) {

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Reservation WHERE reservation_id = " + reservationId);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    /**
     * Creates a reservation in the database
     * @return true/false the status of reservation creation
     */
    public boolean createReservation(String timestamp, String duration,
            int restaurantId, int customerId, int partyCount) {
        
        try {
            preparedStatement = this.connection.prepareStatement("INSERT INTO Reservation (reservation_timestamp, "
                    + "reservation_duration, restaurant_id, customer_id, party_count) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, timestamp);
            preparedStatement.setString(2, duration);
            preparedStatement.setInt(3, restaurantId);
            preparedStatement.setInt(4, customerId);
            preparedStatement.setInt(5, partyCount);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Modifies a reservation in the database
     * @return true/false the status of reservation creation
     */
    
    public boolean modifyReservation(String timestamp, String duration, int partyCount, int reservationID) {
        
        try {
            preparedStatement = this.connection.prepareStatement("UPDATE Reservation SET reservation_timestamp = ?, reservation_duration = ?, party_count = ? WHERE reservation_id = ?");
            preparedStatement.setString(1, timestamp);
            preparedStatement.setString(2, duration);
            preparedStatement.setInt(3, partyCount);
            preparedStatement.setInt(4, reservationID);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    // Create a restaurant
    public boolean createRestaurant(String restaurantName, int capacity) {
        try {
            preparedStatement = this.connection.prepareStatement("INSERT INTO Restaurant(restaurant_name, capacity)"
                    + " VALUES(?, ?)");
            preparedStatement.setString(1, restaurantName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    // Return number of reservations
    public int countReservations(int restaurantId) {
        try {
            int count = 0;
            preparedStatement = this.connection.prepareStatement("SELECT count(*) FROM Reservation WHERE restaurant_id = ?");
            preparedStatement.setInt(1, restaurantId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) count = resultSet.getInt("count(*)");
            return count;
        } catch(Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            return 0;
        }
        
    }
    
    // Most popular reservation time
    public String mostPopularReservationTime(int restaurantId) {
        try {
            String result = "";
            preparedStatement = this.connection.prepareStatement(
                      "  SELECT tm "
                      + "FROM ( "
                      + "  SELECT DATE_FORMAT(STR_TO_DATE(reservation_timestamp, '%Y-%m-%d %H:%i:%s'), '%H:%i:%s') as tm, count(reservation_timestamp) AS cnt "
                      + "  FROM Reservation  "
                      + "  WHERE restaurant_id = ? "
                      + "  GROUP BY reservation_timestamp "
                      + "  ORDER BY COUNT(reservation_timestamp) DESC LIMIT 1) T");
            preparedStatement.setInt(1, restaurantId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
             result = resultSet.getString(1);
            return result;
        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        
    }
    
}
