
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
    
    public static DatabaseInterface reservation = new DatabaseInterface();
    
    public static void main(String[] args) {
        
        reservation.connectDB();
        
        // show login gui
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
        customerOption.setSelected(true);
        window.add(customerOption);
        window.add(managerOption);
        
        // add okay, signup, and cancel buttons
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
