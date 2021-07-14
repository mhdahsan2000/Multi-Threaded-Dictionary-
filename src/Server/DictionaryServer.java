package Server;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ServerSocketFactory;

public class DictionaryServer {
	
	
	private int port = 4444;                  // port default value 
	private Dictionaries dict;                // Dictionaries 
	private ServerSocket server;              // server 
	private int numOfClient = 0;              // numofClients default value 
	private DictionaryServerInterface ui;
	
	/**
	 * Launch the application.
	 * @author Mohammed Ahsan Kollathodi
	 */
	
	public static void main(String[] args) {
		try {
			if (Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) >= 49151) {
				System.out.println("Invalid Port Number: Port number should be between 1024 and 49151!");
				System.exit(-1);
			}
			DictionaryServer dictServer = new DictionaryServer(args[0], args[1]);
			dictServer.run();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Lack of Parameters:\nPlease run like \"java - jar DictionaryServer.jar <port> <dictionary-file>\"!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid Port Number: Port number should be between 1024 and 49151!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Display the client and server 
	
	public void displayclientserver(String str) {
		System.out.println(str);
		if (ui != null) ui.getlogArea().append(str + '\n');
	}
	
	// Initialstats 
	
	private void displaystatsInfo() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println("Server Running...");
		System.out.println("Current IP address : " + ip.getHostAddress());
		System.out.println("Port = " + port);	
		System.out.println("Waiting for the Client Connection...\n--------------");
	}
	
	// Constructor to Initialise variables
	
	public DictionaryServer(String p, String dithPath) {
		this.port = Integer.parseInt(p);
		this.dict = new Dictionaries(dithPath);
		this.ui = null;
		this.server = null;
	}
	
	// override method 
	
	public void run() {
		try {
			this.server = new ServerSocket(this.port);
			displaystatsInfo();
			this.ui = new DictionaryServerInterface(InetAddress.getLocalHost().getHostAddress(), String.valueOf(port), dict.getPath());
			ui.getFrame().setVisible(true);
			while(true) {
				Socket client = server.accept();
				numOfClient++;
				displayclientserver("Server: A client has been connected to the server.\n Current number of clients : " + String.valueOf(numOfClient));
				DictionaryHandler dcThread = new DictionaryHandler(this, client, dict);
				dcThread.start();
			}
		} catch (BindException e) {
			System.out.println("Address already in use, try another address!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Disconnect the client 
	
	public synchronized void clientDisconnect() {
		displayclientserver("Server: A client has been disconnected from the server");
		numOfClient--;
		displayclientserver("Server: The Number of clients at the moment:" + numOfClient + "\n");
	}
	
	// set the port 
	public void setPort(String p) {
		port = Integer.parseInt(p);
	}
	
	// get the port 
	
	public int getPort() {
		return port;
	}
}
