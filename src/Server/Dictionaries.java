/**
 *@author Mohammed Ahsan Kollathodi
 *Reg.no : 1048942 
 */

package Server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Dictionaries {
	
	private String loc = "dictionary.dat";
	private HashMap<String, String> dictMap;
	public Dictionaries(String s) {
		loc = s;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loc));
			dictMap = (HashMap<String, String>) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Wrong file format! Run default dictionary.");
			makerandomdict();
		} catch (FileNotFoundException e) {
			System.out.println("Error: No such file! Run default dictionary.");
			makerandomdict();
		} catch (Exception e) {
			System.out.println("Error: Unknown error, " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// getter method 
	public String getPath() {
		return loc;
	}
	
	
	// To create a random dictionary 
	
	private void makerandomdict() {
		loc = "dictionary.dat";
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loc));
			dictMap = (HashMap<String, String>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Default Dictionary not Exist, Create a new one.");
			makeanewdict(this.loc);
		} catch (Exception e) {
			System.out.println("Error: Unknown error, " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	// To create a new dictionary 
	
	private void makeanewdict(String dictPath) {
		dictMap = new HashMap<String, String>();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dictPath));
			oos.writeObject(dictMap);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Dictionaries() {
		loc = "";
		dictMap = new HashMap<String, String>();
	}
	
	// To know if the word would exist
	
	public synchronized boolean Doeswordbelong(String word) {
		return dictMap.containsKey(word);
	}
	
	// query 
	
	public synchronized String query(String word) {
		return dictMap.get(word);
	}
	
	// TO add a new word 
	
	public synchronized boolean add(String word, String meaning) {
		if (dictMap.containsKey(word)) {
			return false;
		} else {
			dictMap.put(word, meaning);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(loc));
				oos.writeObject(dictMap);
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	// TO remove an existing word
	
	public synchronized boolean remove(String word) {
		if (dictMap.containsKey(word)) {
			dictMap.remove(word);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(loc));
				oos.writeObject(dictMap);
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}












