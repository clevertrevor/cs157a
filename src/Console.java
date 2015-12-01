
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * This class controls flow from the user to the program.
 */

public class Console {
    
    public static DatabaseInterface reservation = new DatabaseInterface();
    
    public static void main(String[] args) {
        
        reservation.connectDB();
        
        JFrame window = new JFrame("RReservation v0.1");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0,0,400,400);
        
        Box mainBox = Box.createVerticalBox();
        
        // add username and password fields
        JTextField usernameField = new JTextField("username1");
        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(usernameField);
        mainBox.add(usernameBox);
        usernameField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                usernameField.setText("");
            }
        });
        
        
        JPasswordField passwordField = new JPasswordField("password1");
        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(passwordField);
        mainBox.add(passwordBox);
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
        customerOption.setSelected(true);
        
        Box radioBox = Box.createHorizontalBox();
        radioBox.add(customerOption);
        radioBox.add(managerOption);
        
        mainBox.add(radioBox);
        
        // add okay, signup, and cancel buttons
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        JButton cancelButton = new JButton("Cancel");
        
        Box okayBox = Box.createHorizontalBox();
        okayBox.add(loginButton);
        mainBox.add(okayBox);
        
        Box signupCancelBox = Box.createHorizontalBox();
        signupCancelBox.add(signupButton);
        signupCancelBox.add(cancelButton);
        mainBox.add(signupCancelBox);
        
        loginButton.addActionListener(new ActionListener() {
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
                    
                } else {
                    Manager m = (Manager) reservation.systemLogin(usernameField.getText(), 
                            new String(passwordField.getPassword()), true);
                    if (m == null) { // failed logging in
                        loginErrorWindow("Manager unable to login.");
                    } else { // successful login
                        new ManagerUi(m);
                    }
                    
                }    
            }
        }); // end okayButton listener
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignupUi();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        
        window.add(mainBox);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.setMinimumSize(new Dimension(300, 50));
        window.pack();
        window.setVisible(true);
      
    } 
    
    // displays custom error messages
    private static void loginErrorWindow(String message){
        JFrame window = new JFrame("Error");
        window.add(new JTextField(message));
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
    
}
