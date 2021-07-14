package Server;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * 
 * @author Mohammed Ahsan Kollathodi
 *
 */

public class DictionaryServerInterface {

	private JFrame window;
	private JTextArea logArea;
	private JLabel addrLbl;
	private JLabel prtLbl;
	private JLabel pthLbl;
	
	public JFrame getFrame() {
		return window ;
	};
	
	public JTextArea getlogArea() {
		return logArea;
	}

	/**
	 * Create the application.
	 */
	public DictionaryServerInterface(String address, String port, String path) {
		initialize(address, port, path);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	// Intialise the variables 
	
	private void initialize(String address, String port, String path) {
		window = new JFrame();
		window.setMinimumSize(new Dimension(450, 300));
		window.setBounds(100, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.out.println("Server Connection Lost!");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		logArea = new JTextArea();
		logArea.setLineWrap(true);
		logArea.setEditable(false);
		scrollPane.setViewportView(logArea);
		
		addrLbl = new JLabel("Address: " + address);
		
		prtLbl = new JLabel("Port: " + port);
		
		pthLbl = new JLabel("Dictionary Path: " + path);
		
		
		// Layout in general both horizontal and vertical. 
		
		GroupLayout groupLayout = new GroupLayout(window.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(addrLbl, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(prtLbl, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(pthLbl, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(addrLbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(prtLbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addComponent(pthLbl)
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
					.addContainerGap())
		);
		window.getContentPane().setLayout(groupLayout);
		
		
	}

}
