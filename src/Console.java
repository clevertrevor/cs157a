
import java.util.Scanner;
import javax.swing.*;

/**
 * This class controls flow from the user to the program.
 */

public class Console {
    
    private static Scanner input = new Scanner(System.in);
    private static Reservation reservation = new Reservation();
    
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
                if (!reservation.insertCustomer(username, password, null, name, phone_number)) System.out.println("Customer creation failed.");
                done = true;
            } else if (userType.equals("2")) {
                userType = "manager";
                System.out.print("\nEnter your restaurantID: ");
                String restaurantId = input.next();
                done = true;
            }
        }
        
        
        
    }
    
    // prints login options
    private static void printLoginOptions() {
        System.out.print("Login as:\n1. Customer\n2. Manager\nEnter number> ");
    }
    
    // prints the main menu options
    private static void printMainMenu() {
        System.out.print("\nMenu:\n1. Login\n2. Sign-Up\n3. Quit\nEnter number> ");
    }
    
}
