import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * This class controls the UI for the customer
 */
public class CustomerUi {
    
    private Customer customer;
    
    public CustomerUi (Customer c) {
        customer = c;
        displayCustomerUi();
    }

    // Displays UI for customer
    private void displayCustomerUi() {

        JFrame window = new JFrame("Customer");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        
        
    }
    
    

}
