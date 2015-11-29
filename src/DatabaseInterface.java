import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

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
    
    
}
