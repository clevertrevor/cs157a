
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;

/**
 * This class controls flow from the user to the program.
 */

public class Console {
    
    private static Scanner input = new Scanner(System.in);
    private static DatabaseInterface reservation = new DatabaseInterface();
    private static Manager manager;
    
    public static void main(String[] args) {
        
        boolean quit = false;
        
        
        
        if(reservation.connectDB()) {
            while (!quit) {
                printMainMenu();
                switch(input.next()) {
                case "1":
                    printLoginOptions();
                    break;
                case "2": 
                    printSignupOptions();
                    
                    break;
                case "3":
                    quit = true; 
                    break;
                default:
                    break;
                }
            }
        }
        
    } // end main
    
 // prints login options and signs up user
    private static void printSignupOptions() {
        
        System.out.print("\nEnter your username: ");
        String username = (input.hasNext())? input.next():null;
        
        System.out.print("\nEnter your name: ");
        String name = (input.hasNext())? input.next():null;
        
        System.out.print("Enter your password: ");
        String password = (input.hasNext())? input.next():null;
        
        System.out.print("");
        
        boolean done = false;
        String userType = "";
        
        // get user type
        while (!done) {
            System.out.print("Signup as:\n1. Customer\n2. Manager\nEnter number> ");
            userType = (input.hasNext())? input.next():null;
            if (userType.equals("1")) {
                userType = "customer";
                System.out.print("Enter your phone number: ");
                String phone_number = "";
                phone_number = (input.hasNext())? input.next():null;
                if (!reservation.insertCustomer(username, password, name, phone_number)) System.out.println("Customer creation failed.");
                done = true;
            } else if (userType.equals("2")) {
                userType = "manager";
                System.out.print("\nEnter your restaurantID: ");
                String restaurantId = (input.hasNext())? input.next():null;
                if (!reservation.insertManager(name, username, password, restaurantId)) System.out.println("Customer creation failed.");
                done = true;
            }
        }
        
        
        
    }
    
    // prints login options
    private static void printLoginOptions() {
        System.out.print("\nEnter your username: ");
        String username = (input.hasNext())? input.next():null;
        
        System.out.print("Enter your password: ");
        String password = (input.hasNext())? input.next():null;
        String userType = "";
        System.out.print("Login as:\n1. Customer\n2. Manager\nEnter number> ");
        userType = (input.hasNext())? input.next():null;
        boolean done;
        // TODO successful login should go to user options
        if (userType.equals("1")) {
            reservation.systemLogin(username, password, false);
            customerOptions();
        }
        else if (userType.equals("2")) {
            manager = (Manager) reservation.systemLogin(username, password, true);
            managerOptions(manager);
        }
        else{
            System.out.println("failed");
            done = true;
        }
        
    }
    
    private static void customerOptions() {
        
        boolean done = false;
        while (!done) {
            System.out.print("\nCustomer options: \n1. Create Reservation"
                    + "\n2. View Reservations \n3. Delete Account \nEnter number> ");
            String customerInput = (input.hasNext())? input.next():null;
            switch (customerInput) {
            case "1" : 
                createReservation();
                done = true;
                break;
            case "2" :
                //viewReservation();
                done = true;
                break;
            case "3" :
                //deleteAccount();
                done = true;
                break;
            default :
                System.out.println("Invalid input.");
                break;
            }
        }
    }

    private static void createReservation() {
        
        
        
    }
    
    private static void managerOptions(Manager m) {
    	boolean done = false;
        while (!done) {
        	System.out.print("\nManager options: \n1. View All Reservations"
                    + "\n2. Delete Reservation \n3. Delete Customer Account \n4. View Statistics \nEnter number> ");
        	
        	String customerInput = (input.hasNext())? input.next():null;
            switch (customerInput) {
            case "1" : 
                List<Reservation> res = reservation.getAllReservations(m.getrestaurantId());
                System.out.println("Name/Time/Duration/Party Count");
                for(Reservation r : res){
                	System.out.println(reservation.getCustomerNameByID(r.getCustomerId()) + "---" + r.getReservationTimestamp() + 
                			"---" + r.getReservationDuration() + "---" + r.getPartyCount());
                }
                done = true;
                break;
            case "2" :
                //viewReservation();
                done = true;
                break;
            case "3" :
                //deleteAccount();
                done = true;
                break;
            default :
                System.out.println("Invalid input.");
                break;
            }
        }
    }

    // prints the main menu options
    private static void printMainMenu() {
        System.out.print("\nMenu:\n1. Login\n2. Sign-Up\n3. Quit\nEnter number> ");
    }
    
}
