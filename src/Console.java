import java.util.Scanner;
import javax.swing.*;

/**
 * This class controls flow from the user to the program.
 */

public class Console {
    
    public static void main(String[] args) {
        
        boolean quit = false;
        Scanner input = new Scanner(System.in);
        Reservation restaurant = new Reservation();
        
        if(restaurant.connectDB()) {
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
    
 // prints login options
    private static void printSignupOptions() {
        System.out.print("Signup as:\n1. Customer\n2. Manager\nEnter number> ");
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