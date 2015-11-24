import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class interfaces with the UI to provide DB functionality 
 */
public class Reservation {
    
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private Scanner inputScanner = new Scanner(System.in);
    
    public Reservation() {}
    
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
    
}