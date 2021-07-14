package Client;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.ws.handler.MessageContext;
import org.omg.CORBA.CTX_RESTRICT_SCOPE;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Libraries used in this script : 
 * 
 * java.net.Socket    -   For Java Socket Programming 
 * java.net.SocketException  -  To ignore exception 
 * java.net.SocketTimeoutException - To ignore exception 
 * java.net.UnknownHostException  - To ignore exception 
 * java.text.ParseException - To ignore exception 
 * java.util.concurrent.TimeoutException -  To ignore exception 
 * java.io.IOException  - File handling  
 * java.io.InputStreamReader -  File handling 
 * java.io.OutputStreamWriter  - File handling 
 * java.net.ConnectException  -  Network 
 * java.awt.EventQueue  -    File handling
 * java.io.BufferedReader -  I/O
 * java.io.BufferedWriter  - I/O
 * java.io.DataInputStream -  I/O
 * java.io.DataOutputStream  - I/O
 * javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction -  swing UI framework
 * javax.xml.ws.handler.MessageContext - xml
 * org.omg.CORBA.CTX_RESTRICT_SCOPE - Corba server 
 * org.omg.CosNaming.NamingContextExtPackage.AddressHelper - AddressHelper
 * org.json.simple.JSONObject - JSON
 * org.json.simple.parser.JSONParser - JSON
 */


// import Functions from the package Functions . 
import Functions.Functions ;


public class DictionaryClient {
	
	
	private String addr;       								    // address 
	private int portnum ;     									// port number 
	private int operationnum = 0; 								// function count - To keep track of the operation count which are 
	private ClientDictionaryGUI userinterface;    			// from the class ClientDictionaryGUI
	
	
	/**
	 * @author Mohammed Ahsan Kollathodi (1048942)
	 * To launch the APPLICATION -- following implementation  .
	 * The following operations are implemented which include : 
	 * 1. To validate the Port Format 
	 * 2. Log of operations and functions 
	 * 3. Running of Thread 
	 * 4. To add, remove and query word. 
	 * 
	 */
	
	
	public static void main(String[] args) {
		try {
			
			// To validate the Port Format such it would exist in the range of 1024 and 49151 .
			// When the port number is out of range. 
			
			if (Integer.parseInt(args[1]) <= 1024 || Integer.parseInt(args[1]) >= 49151) {
				System.out.println("Port Number is NOT VALID : The range of Port number is between 1024 and 49151. So provide a number within the range !");
				System.exit(-1);
			}
			
			System.out.println("Dictionary Client");
			DictionaryClient client = new DictionaryClient(args[0], Integer.parseInt(args[1]));  
			client.run();
			
		    } catch (ArrayIndexOutOfBoundsException e) {
			
			System.out.println("Invalid number of Parameters and format :\n The correct format is as following :  \"java -jar DictionaryClient.jar <server-adress> <server-port>");
			e.printStackTrace();
			
		   } catch (NumberFormatException e) {
			System.out.println("Invalid Port Number: Port number should be between 1024 and 49151!");
		   } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {	
		try {
			// Client Dictionary GUI Object defined in this class . 
			this.userinterface = new ClientDictionaryGUI(this);    
			userinterface.getFrame().setVisible(true);		
		}  // When array index out of bounds occurs. 
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter the correct format as follows :java -jar DictionaryClient.jar <server-adress> <server-port>");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Log of the functions available which are ADD, QUERY, and REMOVE WORDS.  
	private void CreateLogInfo(int status, String word, String result) {
		System.out.println("FUNCTIONS LOG: " + String.valueOf(operationnum));
		System.out.println("REQUEST :");
		switch (status) {
		case Functions.ADDWORD :
			System.out.println("Function = ADD");
			break;
		case Functions.QUERIES :
			System.out.println("Function =  QUERY");
			break;
		case Functions.REMOVEWORD :
			System.out.println("Function = REMOVE");
			break;
		default:
			System.out.println("Error: Unknown Function");
			break;
		}
		
		System.out.println(" Word:" + word);
		if (status == Functions.ADDWORD) System.out.println("Meaning :\n\t" + result );
		operationnum++;
	}
	
	// Constructor of the Client Dictionary class to initialize variables. 
	
	public DictionaryClient(String addr, int portnum) {
		this.addr = addr;
		this.portnum = portnum ;
		this.operationnum = 0;
		userinterface = null;
	}
	
	
	// Function that would print the output of the Operation. 
	
	private void getoutput(int status, String result) {
		System.out.println("Response:");
		switch (status) {
		
		case Functions.SUCCESSSTATUS:
			System.out.println("Operation is SUCCESS");
			break;
		case Functions.FAILSTATUS:
			
			System.out.println("Operation is FAIL");
			break;
		default:
			System.out.println("Error: Unknown Operation") ;
			break;
		}
		
		System.out.println("Meaning:\n\t" + result ) ;
	}
	
	
	// Function that would add word to the existing dictionary. 
	public int add(String word, String result) {
		
		String[] resultArr = execute(Functions.ADDWORD, word, result );
		return Integer.parseInt(resultArr[0]);
	}
	
	// Function to remove an existing word from the dictionary or list of words. 
	public int remove(String word) {
		String[] resultArr = execute(Functions.REMOVEWORD, word, "");
		return Integer.parseInt(resultArr[0]);
	}
	
	// Function related querying the meaning of a particular word. 
	
	public String[] query(String word) {
		String[] resultArr = execute(Functions.QUERIES, word, "");
		return resultArr;
	}

 
	// Function related to the failure status of a Particular query. 
	
	private String[] execute(int command, String word, String result ) {
		int state = Functions.FAILSTATUS ;
		CreateLogInfo(command, word, result);
		
		
		try {
			
			System.out.println("!!!!! Connection to the Server in PROGRESS !!!!!");
			Threadexecutionsequence eThread = new Threadexecutionsequence(addr,portnum, command, word, result); // add new class
			eThread.start();
			eThread.join(2000);
			if (eThread.isAlive()) {
				eThread.interrupt();
				throw new TimeoutException();
			}
			
			String[] eThreadResult = eThread.getResult();
			state = Integer.parseInt(eThreadResult[0]);
			result = eThreadResult[1];
			
			System.out.println("CONNECTION IS SUCCESFULL!");
			
			
		} catch (TimeoutException e) {
			state = Functions.TIMEOUTERROR ;
			result = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getoutput(state, result);
		String[] resultfinal = {String.valueOf(state), result};
		return resultfinal ;
	}
}
