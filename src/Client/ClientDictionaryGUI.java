package Client;

/**
 * --TO SETUP THE USER INTERFACE OF THE APPLICATION--
 * @author Mohammed Ahsan Kollathodi
 * 
 */

import javax.swing.JFrame;
import javax.swing.JButton;
import Functions.Functions;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


/*
 * Libraries used in this script : 
 * 
 * javax.swing.JFrame   - 
 * javax.swing.JButton    - 
 * Functions.Functions    - 
 * java.awt.event.ActionListener   - 
 * java.awt.event.ActionEvent   - 
 * javax.swing.JScrollPane  -  
 * javax.swing.JTextField  - 
 * javax.swing.JLabel  - 
 * javax.swing.JOptionPane  - 
 * javax.swing.JTextArea  - 
 * java.awt.Dimension  - 
 * javax.swing.GroupLayout  - 
 * javax.swing.GroupLayout.Alignment  - 
 * 
 * */



public class ClientDictionaryGUI {

	private JFrame window ;
	private JTextArea windowPane ;
	private JTextField wordInputsection ;
	private DictionaryClient dictionaryClient;

	
	// returns the window frame 
	
	public JFrame getFrame() {
		return window ;
	}

	
	/**
	 * To Create the UI of the Application .
	 * 
	 */
	
	public ClientDictionaryGUI(DictionaryClient client) {
		dictionaryClient = client;
		start();
	}
	
	

	// For validation. 
	
	private Boolean Validation(String word, String meaning, int command) {
		if (word.equals("")) {
			JOptionPane.showMessageDialog(window, "Kindly Input a new word !", "This is a WARNING !", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (command == Functions.ADDWORD && meaning.equals("")) {
			JOptionPane.showMessageDialog(window, "Kindly Input the word's meaning ! ", " This is a WARNING !", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	

	/**
	 * Setup the frame . 
	 * 
	 */
	
	
	private void start() {
		
		// initialize a new JFrame. 
		window = new JFrame();
		
		// set the minimum size of the panel to be of dimensions between 450 x 340. 
		window.setMinimumSize(new Dimension(450, 340));
		
		
		window.setBounds(100, 100, 450, 300);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// declare new text field. 
		wordInputsection = new JTextField();
		
		// of size 10.
		wordInputsection.setColumns(10);
		
		// Button to add a new word. 
		JButton btnAdd = new JButton("ADD WORD") ;
		
		// Functionality of the Button. 
		
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String element = wordInputsection.getText();
				
				String inference = windowPane.getText();
				
				// Validation of user activity . 
				
				if (Validation(element, inference, Functions.ADDWORD)) {
					int confirm = JOptionPane.showConfirmDialog(window,  "Can you confrim to Add the word?", "Confirm Window", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						int state = dictionaryClient.add(element, inference);
						if(state == Functions.UNKNOWNHOST) {
							JOptionPane.showMessageDialog(window, "Unknown Host!\nPlease setup with a correct Address.", 
									"Tihs is a WARNING!", JOptionPane.ERROR_MESSAGE);
						} else if (state == Functions.FAILSTATUS) {
							JOptionPane.showMessageDialog(window, "The Word is already Present in the list!", "This is a WARNING!! ", JOptionPane.WARNING_MESSAGE);
						} else if (state == Functions.TIMEOUTERROR) {
							JOptionPane.showMessageDialog(window, "Session Timeout! \nPlease Verify the server or restart with a correct Address.", 
									"Warning", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(window, "Add Success!", "Tips", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		
		
		// To get the meaning of the Query. 
		
		
		JButton btnQuery = new JButton("QUERY MEANING OF A WORD");
		btnQuery.addActionListener(new ActionListener() {
		
		// To perform QUERY to know the meaning of a WORD. 
			
			public void actionPerformed(ActionEvent e) {
				String word = wordInputsection.getText();
				if (Validation(word, "", Functions.QUERIES)) {
					String[] resultArr = dictionaryClient.query(word);
					int state = Integer.parseInt(resultArr[0]);
					if (state == Functions.UNKNOWNHOST) {
						JOptionPane.showMessageDialog(window, "The Host is Unknown !\nPlease setup with a correct Address.", "THIS IS A WARNING !! ", JOptionPane.ERROR_MESSAGE);
					} else if (state == Functions.FAILSTATUS) {
						JOptionPane.showMessageDialog(window, "Query Fail\nWord Not Exist!", "Warning", JOptionPane.WARNING_MESSAGE);
					} else if (state == Functions.TIMEOUTERROR) {
						JOptionPane.showMessageDialog(window, "Session TimeOut, please setup with a correct Address!.", 
								"Warning", JOptionPane.ERROR_MESSAGE);
					} else {
						windowPane.setText(resultArr[1]);
					}
				}
			}
		});
		
		// To remove a word. 
		// Button to incorporate the functionality to remove a word.
		
		JButton buttonvoid = new JButton("REMOVE WORD");
		
		// To remove a word from existing list of words in the dictionary. 
		
		buttonvoid.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String element = wordInputsection.getText();
				
				// To remove a word from the dictionary. 
				
				if (Validation(element, "", Functions.REMOVEWORD)) {
					
					int confirmaction = JOptionPane.showConfirmDialog(window,  "Do you really want to remove the word?", "Confirm Window", JOptionPane.YES_NO_OPTION);
					if (confirmaction == JOptionPane.YES_OPTION) {
						
						int level = dictionaryClient.remove(element);
						
						// When the Host is Unknown.  
						if(level == Functions.UNKNOWNHOST) {
							
							JOptionPane.showMessageDialog(window, "The Host is UNKNOWN !\nPlease setup the system again with a correct Address.", "This is a WARNING!", JOptionPane.ERROR_MESSAGE);
						}
						
						// When there is a failure status. 
						else if (level == Functions.FAILSTATUS) {
							
							JOptionPane.showMessageDialog(window, "The word cannot be removed as it does not exist in the dictionary !", "This is a WARNING! ", JOptionPane.WARNING_MESSAGE);
							
					     // When there is a session timeout error. 
	
						} else if (level == Functions.TIMEOUTERROR) {
							JOptionPane.showMessageDialog(window, "Session Timeout ! Please setup the system again with a correct Address.", 
									"This is a WARNING !!", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(window, "The Word was succesfully removed from the list of words !", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		
		// The meaning of the specified word. 
		
		JLabel wordmeaning = new JLabel("The Meaning of the specified word is as follows : ");
		
		JScrollPane scrollPane = new JScrollPane();
		
		// The WordSet 
		
		JLabel Wordset = new JLabel("WORD IS GIVEN TO BE AS:");
		
		windowPane = new JTextArea();
		
		scrollPane.setViewportView(windowPane);
		
		windowPane.setLineWrap(true);
		
		// Layout of the User Interface.
		
		GroupLayout groupLayout = new GroupLayout(window.getContentPane());
		
		// Horizontal Group. 
		
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(wordmeaning, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(Wordset, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addComponent(wordInputsection, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(btnQuery, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(buttonvoid, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		// Vertical Group. 
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(wordmeaning, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(Wordset, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(wordInputsection, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnQuery, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(buttonvoid, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addGap(8))
		);
		
		
		
		// Set Layout. 
		
		window.getContentPane().setLayout(groupLayout);
	}
}
