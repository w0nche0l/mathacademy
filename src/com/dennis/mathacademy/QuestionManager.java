package com.dennis.mathacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import net.sourceforge.jeval.EvaluationException;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.dennis.mathacademy.io.FileUtils;
import com.dennis.mathacademy.parser.Parser;
import com.dennis.mathacademy.ui.TeXView;

public class QuestionManager {
	

	private Parser parser;
	
	private LinkedList<String> fileQueue;
	String originalFileName;
	String ans; 
	
	Activity parent;
	
	QuestionManager(Activity parentActivity){
		parent= parentActivity ;
		fileQueue = new LinkedList<String>();
	}
	
	public void addQuestion(String name){
		fileQueue.addLast(name);
	}
	
	public HashMap<String,String> readQuestion() throws IOException, EvaluationException{
		return readQuestion(fileQueue.peek());
	}
	
	public HashMap<String,String> nextQuestion() throws IOException, EvaluationException{
		fileQueue.pop();
		if(fileQueue.isEmpty())
			return null;
		else{
			Log.d("mathacademy", "readingnextquestion");
			return readQuestion(fileQueue.peek());
		}
		
	}
	
	
	public HashMap<String,String> readQuestion(String s) throws IOException, EvaluationException{
		
		BufferedReader file = new BufferedReader(new InputStreamReader(parent.getAssets().open(s)));
		HashMap<String,String> info = this.readQuestion(file);
		file.close();
		return info;
	}
	

	public HashMap<String,String> readQuestion(BufferedReader file) throws IOException, EvaluationException{
		return readQuestion(file, 4);
	}
	
	public HashMap<String,String> readQuestion(BufferedReader file, int choices) throws IOException, EvaluationException{
		
		parser = new Parser(FileUtils.readFile(file));
		parser.parse();
		HashMap<String ,String> info = new HashMap<String,String>();
		
		ArrayList<String> answers = parser.getMultipleChoice(choices);
		
		
		info.put("answer", answers.get(0));
		info.put("question", parser.getQuestion());
		info.put("solution", parser.getSolution());
		
		
		
		Collections.shuffle(answers);
		
		for(int i = 0; i < choices; i++){
			info.put("choice"+Integer.toString(i), answers.get(i));
		}
		
		
		Log.d("MathAcademy", parser.getQuestion());
		for(int i = 0;  i <choices; ++i){
			Log.d("MathAcademyAnswers", answers.get(i));
		}
		
		return info;
	}
	
	
	
	
	/*public ArrayList<String> readQuestion(BufferedReader file) throws IOException, EvaluationException{
		
		parser = new Parser(FileUtils.readFile(file));
		parser.parse();
		ArrayList<String> answers = parser.getMultipleChoice(4);
		ans = answers.get(0);
		Collections.shuffle(answers);
		
		ArrayList<String> info = new ArrayList<String>(5);
		info.add(0,parser.getQuestion());
		for(int i = 0; i < 4; i++){
			info.add(1+i, answers.get(i));
		}
		info.add(5, ans);
		info.add(6,parser.getSolution());
		
		Log.d("MathAcademy", parser.getQuestion());
		for(int i = 0;  i <4; ++i){
			Log.d("MathAcademyAnswers", answers.get(i));
		}
		
		return info;
	}*/
	
}
