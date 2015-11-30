import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * This class controls the UI for the customer
 */
public class ManagerUi {
    
    private Manager manager;
    
    public ManagerUi (Manager m) {
        manager = m;
        displayManagerUi();
    }

    // Displays UI for customer
    private void displayManagerUi() {

        JFrame window = new JFrame("Manager");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        
        
    }
    
    

}
