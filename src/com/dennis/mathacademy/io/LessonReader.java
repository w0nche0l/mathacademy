package com.dennis.mathacademy.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import android.os.Environment;
import android.util.Log;

public class LessonReader {
	final static String folder = "MathAcademy";
	static File dir;
	static String home;
	
	public static void setTopDirectory(String s){
		
		
		dir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator+folder);
		dir.mkdirs();
		
		if(dir.isDirectory()){
			String[] list = dir.list();
			Log.d("MathAcademy", "File");
			for(String asdf : list){
				Log.d("MathAcademy", asdf);
			}
		}
		
		File sample = new File(dir.getPath()+File.separator+"sample.txt");
		
		try {
			Writer output = new BufferedWriter(new FileWriter(sample));
			output.write("den");
			output.close();
			Log.d("MathAcademy", "written");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(dir.isDirectory()){
			String[] list = dir.list();
			Log.d("MathAcademy", "File");
			for(String asdf : list){
				Log.d("MathAcademy", asdf);
			}
		}
		
		
		
		Log.d("MathAcademy", dir.getPath());
		
	}
	
	
}
