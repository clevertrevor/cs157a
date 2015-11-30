
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

/**
 * This class controls flow from the user to the program.
 */

public class Console {
    
    private static Scanner input = new Scanner(System.in);
    private static DatabaseInterface reservation = new DatabaseInterface();
    private static Customer customer;
    private static Manager manager;
    
    public static void main(String[] args) {
        
        reservation.connectDB();
        showLoginGui();
        
        /*
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
        
        */
        
    } // end main
    
    
    // Shows the GUI for users to login
    private static void showLoginGui() {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0,0,400,400);
        
        window.setLayout(new GridLayout(4, 6));
        
        // add username and password fields
        JTextField usernameField = new JTextField("username1");
        window.add("usernameFile", usernameField);
        usernameField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                usernameField.setText("");
            }
        });
        
        JPasswordField passwordField = new JPasswordField("password1");
        window.add("passwordField", passwordField);
        passwordField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                passwordField.setText("");
            }
        });
        
        // add customer and manager radio buttons
        JRadioButton customerOption = new JRadioButton("Customer");
        JRadioButton managerOption = new JRadioButton("Manager");
 
        ButtonGroup group = new ButtonGroup();
        group.add(customerOption);
        group.add(managerOption);
        window.add(customerOption);
        window.add(managerOption);
        
        // add okay and cancel buttons
        JButton okayButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        JButton cancelButton = new JButton("Cancel");
        window.add(okayButton);
        window.add(signupButton);
        window.add(cancelButton);
        
        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // login with either customer, manager, or error for none selected
                if (customerOption.isSelected()) {
                    Customer c = (Customer) reservation.systemLogin(usernameField.getText(), 
                            new String(passwordField.getPassword()), false);
                    if (c == null) { // failed logging in
                        loginErrorWindow("Customer unable to login.");
                    } else { // successful login
                        new CustomerUi(c);
                    }
                    
                } else if (managerOption.isSelected()) {
                    Manager m = (Manager) reservation.systemLogin(usernameField.getText(), 
                            new String(passwordField.getPassword()), true);
                    if (m == null) { // failed logging in
                        loginErrorWindow("Manager unable to login.");
                    } else { // successful login
                        new ManagerUi(m);
                    }
                    
                } else { // no login option selected
                    loginErrorWindow("Customer or Manager must be selected");
                }   
            }
        }); // end okayButton listener
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.pack();
        window.setVisible(true);
            
    }
    
    // method for displaying custom login messages
    private static void loginErrorWindow(String message){
        JFrame window = new JFrame("Error");
        window.add(new JTextField(message));
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

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
            customer = (Customer) reservation.systemLogin(username, password, false);
            customerOptions();
        }
        else if (userType.equals("2")) {
            manager = (Manager) reservation.systemLogin(username, password, true);
            managerOptions(manager);
            done = true;
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
                    + "\n2. View Reservations \n3. Delete Account \n4. Quit \nEnter number> ");
            String customerInput = (input.hasNext())? input.next():null;
            switch (customerInput) {
            case "1" : 
                createReservation();
                break;
            case "2" :
                viewReservations();
                break;
            case "3" :
                deleteAccount();
                done = true;
                break;
            case "4" :
                done = true;
                break;
            default :
                System.out.println("Invalid input.");
                break;
            }
        }
    }
    
    // Shows the customer's reservations
    private static void viewReservations() {
        List<Reservation> res = reservation.getCustomerReservations(customer.getCustomerId());
        System.out.println("Name/Time/Duration/Party Count");
        for(Reservation r : res){
            System.out.println(reservation.getCustomerNameByID(r.getCustomerId()) + "---" + r.getReservationTimestamp() + 
                    "---" + r.getReservationDuration() + "---" + r.getPartyCount());
        }
    }

    private static void deleteAccount() {

        if (reservation.deleteCustomer(customer.getCustomerId())) {
            System.out.println("Account deleted.");
        } else {
            System.out.println("Account failed to delete.");
        }
        
        
    }

    private static void createReservation() {
        
        System.out.print("\nEnter date(YYYY-MM-DD)> ");
        String date = (input.hasNextLine())? input.next():null;
        
        System.out.print("\nEnter time(HH:MM:SS)> ");
        String time = (input.hasNextLine())? input.next():null;
        
        String timestamp = date + " " + time;
        System.out.println("timestamp: " + timestamp);
        
        System.out.print("\nEnter duration(HH:MM:SS)> ");
        String duration = (input.hasNext())? input.nextLine():null;
        input.nextLine();
        duration = "11:11:11"; // TODO scanner bug here. 
        System.out.print("\nEnter restaurant id> ");
        int restaurantId = (input.hasNext())? input.nextInt():null;
        
        System.out.print("\nEnter party count> ");
        int partyCount = (input.hasNext())? input.nextInt():null;
        
        if (reservation.createReservation(timestamp, duration, restaurantId, customer.getCustomerId(), partyCount)) {
            System.out.println("Reservation created!");
        } else {
            System.out.println("Reservation failed.");
        }
        
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
