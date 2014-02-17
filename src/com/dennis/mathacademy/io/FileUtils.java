package com.dennis.mathacademy.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.FileReader; 
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileUtils {

	static public HashMap<String, String> loadSerliazedHashMap(File data){
		try {
			return (HashMap<String, String>) (new ObjectInputStream(new FileInputStream(data)).readObject());
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	static public boolean saveSerializedHashMap(HashMap<String, String> data, String location){
		File thing = new File(location);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(thing, false));
			out.writeObject(data);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	static public ArrayList<String> loadLesson(BufferedReader file){
		ArrayList<String> names = new ArrayList<String>();
		while(true){
			String temp = null;
			try {
				temp = file.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(temp== null || temp.equalsIgnoreCase("end lesson") ||  temp.equalsIgnoreCase("endlesson")){
				break;
			}
			else{
				if(temp.contains(".problem")){
					names.add(temp);
				}
			}
		}
		return names;
	}
	
	static public ArrayList<String> readFile(String file){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return readFile(reader);
	}
	

	static public ArrayList<String> readFile(BufferedReader thing){
		ArrayList<String> fileLines = new ArrayList<String>();
		BufferedReader reader = thing;
		
		while(true){
			String temp = null;
			try {
				temp = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(temp== null){
				break;
			}
			
			fileLines.add(temp);
		}
		if(reader!= null)
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileLines;
	}
	
	
}
