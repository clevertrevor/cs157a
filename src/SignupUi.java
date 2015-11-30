import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class SignupUi {
    
    // sign up user in a window
    public SignupUi() {
        
        JFrame window = new JFrame("Signup");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0,0,400,400);
        
        Box mainBox = Box.createVerticalBox();
        
        // radio options for customer and manager
        Box radioBox = Box.createHorizontalBox();
        JRadioButton customerOption = new JRadioButton("Customer");
        customerOption.setSelected(true);
        JRadioButton managerOption = new JRadioButton("Manager");
        ButtonGroup group = new ButtonGroup();
        group.add(customerOption);
        group.add(managerOption);
        radioBox.add(customerOption);
        radioBox.add(managerOption);
         
        
        mainBox.add(radioBox);
        
        // fields for name, username, password
        Box userFieldsBox = Box.createVerticalBox();
        
        JTextField nameField = new JTextField("Your name");
        nameField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                nameField.setText("");
            }
        });
        
        JTextField usernameField = new JTextField("username1");
        usernameField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                usernameField.setText("");
            }
        });
        
        JPasswordField passwordField = new JPasswordField("password1");
        passwordField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                passwordField.setText("");
            }
        });
        
        userFieldsBox.add(nameField);
        userFieldsBox.add(usernameField);
        userFieldsBox.add(passwordField);
        
        
        // customer then manager specific fields
        JTextField phoneField = new JTextField("phone number...");
        phoneField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                phoneField.setText("");
            }
        });
        
        JTextField restaurantIdField = new JTextField("restaurant ID...");
        restaurantIdField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                restaurantIdField.setText("");
            }
        });
        restaurantIdField.setEnabled(false);
        
        userFieldsBox.add(phoneField);
        userFieldsBox.add(restaurantIdField);
        mainBox.add(userFieldsBox);
        
        // radiobox action listeners
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                // if true, set customer options available and manager options unavailable
                // else set manager options available and customer options unavailable
                if (customerOption.isSelected()) {
                    phoneField.setEnabled(true);
                    restaurantIdField.setEnabled(false);
                } else {
                    phoneField.setEnabled(false);
                    restaurantIdField.setEnabled(true);
                }
                
            }
        };
        
        customerOption.addActionListener(radioListener);
        managerOption.addActionListener(radioListener);
        
        // signup and cancel buttons
        Box buttonBox = Box.createHorizontalBox();
        JButton signupButton = new JButton("Signup");
        JButton cancelButton = new JButton("Cancel");
        buttonBox.add(signupButton);
        buttonBox.add(cancelButton);
        mainBox.add(buttonBox);
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = false;
                if (customerOption.isSelected())
                    result = Console.reservation.insertCustomer(
                            usernameField.getText(), new String(passwordField.getPassword()), 
                            nameField.getText(), phoneField.getText());
                else 
                    result = Console.reservation.insertManager(
                            nameField.getText(), usernameField.getText(), 
                            new String(passwordField.getPassword()), restaurantIdField.getText());
                if (result == true) {
                    signupResultWindow("Successfully signed up");
                } else {
                    signupResultWindow("Unable to signup");
                }
                window.dispose();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        
        
        // final window setup
        window.add(mainBox);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.setMinimumSize(new Dimension(200, 50));
        window.pack();
        window.setVisible(true);
    }
    
 // method for displaying custom login messages
    private static void signupResultWindow(String message){
        JFrame window = new JFrame("Error");
        window.add(new JTextField(message));
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.setVisible(true);
    }

}
