import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * This class controls the UI for the Manager
 */
public class ManagerUi {
    
    private Manager manager;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private JFrame window;
    
    public ManagerUi (Manager m) {
        manager = m;
        displayManagerUi();
    }

    // Displays UI for Manager
    private void displayManagerUi() {

        window = new JFrame("Manager");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0,0,400,400);
        window.setVisible(true);
        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Time");
        model.addColumn("Duration");
        model.addColumn("Count");
        table = new JTable(model);
        table.getTableHeader().setVisible(false);
        Box mainBox = Box.createVerticalBox();
        Box buttonBox = Box.createHorizontalBox();
        Box actionBox = Box.createVerticalBox();
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        JButton modifyDetailsButton = new JButton("Modify Details");
        JButton deleteReservationButton = new JButton("Delete Reservation");
        JButton deleteAccountButton = new JButton("Delete Customer Account");
        JButton viewStatisticsButton = new JButton("View Statistics");
        JButton createRestaurantButton = new JButton("Create Restaurant");
        buttonBox.add(modifyDetailsButton);
        buttonBox.add(deleteReservationButton);
        buttonBox.add(deleteAccountButton);
        buttonBox.add(viewStatisticsButton);
        buttonBox.add(createRestaurantButton);
        
        modifyDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyDetails();
            }
        });
        
        deleteReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReservation();
            }
        });
        
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });
        
        createRestaurantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRestaurantWindow();
            }
        });
        
        actionBox.add(scrollPane);
        mainBox.add(buttonBox);
        mainBox.add(actionBox);
        window.add(mainBox);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.pack();
        viewReservations();
    }
    
    
    protected void createRestaurantWindow() { 
        
        window = new JFrame("Create Reservation");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0,0,400,400);
        window.setVisible(true);
        
        Box verticalBox = Box.createVerticalBox();
        
        JLabel restaurantName = new JLabel("Restaurant name: ");
        JTextField restaurantNameField = new JTextField();
        Box restaurantNameBox = Box.createHorizontalBox();
        restaurantNameBox.add(restaurantName);
        restaurantNameBox.add(restaurantNameField);
        verticalBox.add(restaurantNameBox);
        
        JLabel restaurantCapacityLabel = new JLabel("Restaurant capacity: ");
        JTextField restaurantCapacityField = new JTextField();
        Box restaurantCapacityBox = Box.createHorizontalBox();
        restaurantCapacityBox.add(restaurantCapacityLabel);
        restaurantCapacityBox.add(restaurantCapacityField);
        verticalBox.add(restaurantCapacityBox);
        
        // bottom buttons
        Box buttonBox = Box.createHorizontalBox();
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        buttonBox.add(createButton);
        buttonBox.add(cancelButton);
        verticalBox.add(buttonBox);
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                return;
            }
        });
        
        createButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String restaurantName = "";
                int restaurantCapacity = 0;
                
                // check valid inputs
                try {
                    restaurantName = restaurantNameField.getText();
                    restaurantCapacity = Integer.parseInt(restaurantCapacityField.getText());
                } catch (NumberFormatException error) {
                    displayPopup("Error", "Restaurant failed to create");
                    window.dispose();
                    return;
                }
                
                if (Console.reservation.createRestaurant(
                        restaurantName, restaurantCapacity)) {
                    displayPopup("Success", "Restaurant created!");
                    window.dispose();
                    viewReservations();
                } 
                
            }
        });
        
        window.add(verticalBox);
        window.setMinimumSize(new Dimension(300, 50));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.pack();
        
    }
    
    private static void displayPopup(String title, String message){
        JFrame window = new JFrame(title);
        window.add(new JTextField(message));
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setMinimumSize(new Dimension(200, 30));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.setVisible(true);
    }

    /**
     * Refreshes the table with all of the reservations. Called everytime an update is made using GUI
     */
    private void viewReservations(){
    	model.setRowCount(0);
    	table.getTableHeader().setVisible(true);
    	List<Reservation> res = Console.reservation.getAllReservations(manager.getrestaurantId());
        int count = 0;
        String[] reservationData = new String[4];
        for(Reservation r : res){
            reservationData[0] = Console.reservation.getCustomerNameByID(r.getCustomerId());
            reservationData[1] = r.getReservationTimestamp(); 
            reservationData[2] = r.getReservationDuration();
            reservationData[3] = "" + r.getPartyCount();
            model.addRow(reservationData);
        }
        scrollPane.repaint();
    }
    
    /**
     * Delete a reservation in the database
     */
    private void deleteReservation(){
    	if(table.getSelectedRow() != -1){
    	List<Reservation> res = Console.reservation.getAllReservations(manager.getrestaurantId());
    	if(Console.reservation.deleteReservation(res.get(table.getSelectedRow()).getReservationId()) == true){
    		JOptionPane.showMessageDialog(window,
                    "Delete Successful :)");
    	}else{
    		JOptionPane.showMessageDialog(window,
                    "Reservation could not be deleted :(");
    	}
    	
    	}
    	viewReservations();
    }
    
    /**
     * Delete an account in the database
     */
    private void deleteAccount(){
    	if(table.getSelectedRow() != -1){
        	List<Reservation> res = Console.reservation.getAllReservations(manager.getrestaurantId());
        	if(Console.reservation.deleteCustomer(res.get(table.getSelectedRow()).getCustomerId()) == true){
        		JOptionPane.showMessageDialog(window,
                        "Delete Successful :)");
        	}else{
        		JOptionPane.showMessageDialog(window,
                        "Customer could not be deleted :(");
        	}
        	
        	}
        	viewReservations();
    }
    
    /**
     * Modify the details of the reservation in the database
     */
    private void modifyDetails(){
    	//Check to see if row in table is selected
    	if(table.getSelectedRow() != -1){
    		//Get list of reservations
    		List<Reservation> res = Console.reservation.getAllReservations(manager.getrestaurantId());
    		Reservation temp = res.get(table.getSelectedRow());
    		//Create custom dialog box
    	    JTextField timeField = new JTextField();
    	    JTextField durationField = new JTextField();
    	    JTextField countField = new JTextField();
    	    JPanel dialogPanel = new JPanel();
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Time:"));
    	    dialogPanel.add(timeField);
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Duration:"));
    	    dialogPanel.add(durationField);
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Party Count:"));
    	    dialogPanel.add(countField);
    	    timeField.setText(temp.getReservationTimestamp());
    	    durationField.setText(temp.getReservationDuration());
    	    countField.setText("" + temp.getPartyCount());
    	    
    	    //Show modify dialog box
    	    int result = JOptionPane.showConfirmDialog(null, dialogPanel, 
    	               "Please modify reservations for " + Console.reservation.getCustomerNameByID(temp.getCustomerId()), JOptionPane.OK_CANCEL_OPTION);
    	    
    	    //If values have changed, implement query
    	    if(!timeField.getText().equals(temp.getReservationTimestamp()) ||
    	    		!durationField.getText().equals(temp.getReservationDuration()) ||
    	    		!countField.getText().equals("" + temp.getPartyCount())){
    	    	Console.reservation.modifyReservation(timeField.getText(), durationField.getText(), Integer.parseInt(countField.getText()), temp.getReservationId());
    	    	viewReservations();
    	    }
    	}
    }
    
    
    
    

}
