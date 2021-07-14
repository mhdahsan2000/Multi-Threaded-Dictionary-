package Server;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.sql.ClientInfoStatus;
import javax.imageio.IIOException;
import javax.management.Query;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Functions.Functions ;

public class DictionaryHandler extends Thread{
	private Dictionaries dict;
	private Socket clientSocket;
	private DictionaryServer server;
	
	private String getState(int state) {
		String g = "UnKnown";
		switch (state) {
		case Functions.QUERIES :
			g = "QUERY";
			break;
		case Functions.ADDWORD:
			g = "ADD";
			break;
		case Functions.REMOVEWORD :
			g = "REMOVE";
			break;
		default:
			break;
		}
		return g;
	}
	
	
	/**
	 * @author Mohammed Ahsan Kollathodi
	 * @param server
	 * @param client
	 * @param dict
	 */
	
	// Constructor to Initialise the variables 
	
	public DictionaryHandler(DictionaryServer server, Socket client, Dictionaries dict) {
		this.server = server;
		this.clientSocket = client;
		this.dict = dict;
	}
	
	// To create a JSON 
	
	private JSONObject makeaJSON(int state, String meaning) {
		JSONObject requestJson = new JSONObject();
		requestJson.put("state", String.valueOf(state));
		requestJson.put("meaning", meaning);
		return requestJson;
	}
	
	// To parse the string 
	
	private JSONObject parseReqString(String res) {
		JSONObject reqJSON = null;
		try {
			JSONParser parser = new JSONParser();
			reqJSON = (JSONObject) parser.parse(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqJSON;
	}
	
	// method Override 
	
	@Override
	public void run() {
		try {
			DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
			JSONObject reqJSON = parseReqString(reader.readUTF());
			int command = Integer.parseInt(reqJSON.get("command").toString());
			String word = (String) reqJSON.get("word");
			server.displayclientserver("-- Get Request --\n  Command: " + getState(command) + "\n  word: " + word);
			int state = Functions.FAILSTATUS ;	
			String meaning = (String) reqJSON.get("meaning");
			
			switch (command) {
			case Functions.QUERIES :
				if (dict.Doeswordbelong(word)) {
					meaning = dict.query(word);
					state = Functions.SUCCESSSTATUS ;
					server.displayclientserver("QUERY SUCCESS!");
				} else {
					state = Functions.FAILSTATUS ;
					server.displayclientserver("QUERY FAIL: Word Not Exist!");
				}
				writer.writeUTF(makeaJSON(state, meaning).toJSONString());
				writer.flush();
				break;
			case Functions.ADDWORD :
				if (!dict.Doeswordbelong(word)) {
					dict.add(word, meaning);
					state = Functions.SUCCESSSTATUS ;
					server.displayclientserver("ADD SUCCESS: " + word + "\nMeaning: " + meaning);
				} else {
					server.displayclientserver("ADD FAIL: Word Exist!");
					state = Functions.FAILSTATUS ;
				}
				writer.writeUTF(makeaJSON(state, "").toJSONString());
				writer.flush();
				break;
			case Functions.REMOVEWORD :
				if (dict.Doeswordbelong(word)) {
					dict.remove(word);
					state = Functions.SUCCESSSTATUS ;
					server.displayclientserver("REMOVE SUCCESS: " + word);
				} else {
					state = Functions.FAILSTATUS ;
					server.displayclientserver("ADD FAIL: Word Exist!");
				}
				writer.writeUTF(makeaJSON(state, "").toJSONString());
				writer.flush();
				break;
			default:
				break;
			}
			reader.close();
			writer.close();
			clientSocket.close();
			this.server.clientDisconnect();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
