package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Functions.Functions ;


/**
 * This SCRIPT is optimized to enhance the Thread Execution Sequence. 
 * The order in which the threads would be executed.s
 * 
 */

/**
 * Libraries used in this script : 
 * 
 * @author  Mohammed Ahsan Kollathodi
 *
 */


public class Threadexecutionsequence extends Thread {
	
	
	// Class Variables
	
	private int level;
	private int cmmnd;
	private String wrd;
	private String meaning;
	private Socket socket;
	private String addr ;
	private int prt;
	private String[] resultArr = {"", ""};
	
	
	// Make the JSON 
	
	private JSONObject makeJSON() {
		JSONObject requestJson = new JSONObject();
		requestJson.put("command", String.valueOf(cmmnd));
		requestJson.put("word", wrd);
		requestJson.put("meaning", meaning);
		return requestJson;
	}
	
	
	// parse the String
	
	private JSONObject parseString(String res) {
		JSONObject resJSON = null;
		try {
			JSONParser parser = new JSONParser();
			resJSON = (JSONObject) parser.parse(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resJSON;
	}
	
	
	// getter method 
	
	public String[] getResult() {
		return resultArr;
	}
	
	
	// Constructor to initialise the variables
	
	public Threadexecutionsequence(String address, int port, int command, String word, String meaning) {
		this.addr = address;
		this.prt = port;
		this.level = Functions.FAILSTATUS ;
		this.cmmnd = command;
		this.wrd = word;
		this.meaning = meaning;
		socket = null;
	}
	
	
	// method override

	public void run() {
		try {
			socket = new Socket(addr, prt);  // Communication essentially happens through socket. 		
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
			writer.writeUTF(makeJSON().toJSONString());
			writer.flush();
			String res = reader.readUTF();
			JSONObject resJSON = parseString(res);
			level = Integer.parseInt(resJSON.get("state").toString());
			if (level == Functions.SUCCESSSTATUS) {
				meaning = (String) resJSON.get("meaning");
			}
			reader.close();
			writer.close();	
			
			
		} catch (UnknownHostException e) {
			level = Functions.UNKNOWNHOST;
			System.out.println("Error: UNKNOWN HOST!");
		} catch (ConnectException e) {
			level = Functions.CONNECTIONREFUSED;
			System.out.println("Error: COLLECTIONG REFUSED!");
		} catch (SocketTimeoutException e) {
			level = Functions.TIMEOUTERROR ;
			System.out.println("Timeoutr!");
		} catch (SocketException e) {
			level = Functions.IOERROR;
			System.out.println("Error: I/O ERROR!");
		} catch (IOException e) {
			level = Functions.IOERROR;
			System.out.println("Error: I/O ERROR!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		resultArr[0] = String.valueOf(level);
		resultArr[1] = meaning;
	}
	
}
