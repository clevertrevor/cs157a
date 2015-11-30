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
 * This class controls the UI for the customer
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

    // Displays UI for customer
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
        buttonBox.add(modifyDetailsButton);
        buttonBox.add(deleteReservationButton);
        buttonBox.add(deleteAccountButton);
        buttonBox.add(viewStatisticsButton);
        
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
        
        actionBox.add(scrollPane);
        mainBox.add(buttonBox);
        mainBox.add(actionBox);
        window.add(mainBox);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
        window.pack();
        viewReservations();
    }
    
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
    
    private void modifyDetails(){
    	if(table.getSelectedRow() != -1){
    		List<Reservation> res = Console.reservation.getAllReservations(manager.getrestaurantId());
    		Reservation temp = res.get(table.getSelectedRow());
    		JTextField nameField = new JTextField();
    	    JTextField timeField = new JTextField();
    	    JTextField durationField = new JTextField();
    	    JTextField countField = new JTextField();
    	    JPanel dialogPanel = new JPanel();
    	    dialogPanel.add(new JLabel("Name:"));
    	    dialogPanel.add(nameField);
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Time:"));
    	    dialogPanel.add(timeField);
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Duration:"));
    	    dialogPanel.add(durationField);
    	    dialogPanel.add(Box.createHorizontalStrut(10));
    	    dialogPanel.add(new JLabel("Party Count:"));
    	    dialogPanel.add(countField);
    	    nameField.setText(Console.reservation.getCustomerNameByID(temp.getCustomerId()));
    	    timeField.setText(temp.getReservationTimestamp());
    	    durationField.setText(temp.getReservationDuration());
    	    countField.setText("" + temp.getPartyCount());
    	    
    	    int result = JOptionPane.showConfirmDialog(null, dialogPanel, 
    	               "Please modify desired options", JOptionPane.OK_CANCEL_OPTION);
    	}
    }
    
    
    
    

}
