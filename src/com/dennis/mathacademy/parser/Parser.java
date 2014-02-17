package com.dennis.mathacademy.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ArrayList;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import com.dennis.mathacademy.io.FileUtils;
import com.dennis.mathacademy.jeval.functions.RandomInteger;



/*
 * Uses JEval and JLatexMath under the GNU license
 * 
 * 
 */

public class Parser {
	private StringBuilder question = new StringBuilder();
	private StringBuilder answer = new StringBuilder();
	private StringBuilder solution = new StringBuilder(); 
	private StringBuilder vars = new StringBuilder();
	private ArrayList<String> fileLines;
	private ArrayList<String> choices = new ArrayList<String>();
	private String ans;
	private boolean choicesGiven = false;
	int linenum;
	
	final String choicesString = "choices";
	
	Evaluator evaluator = new Evaluator();
	
	Vector<Vector<String>> varstatements= new Vector<Vector<String>>();
	
	public Parser(ArrayList<String> file) throws IOException, EvaluationException{
		linenum = 0;
		fileLines = file;
		loadFunctions();
	}
	
	public void parse() throws IOException, EvaluationException{
		readVariables();
		readQuestion();
		readChoices();
		readAnswer();
		readSolution();
	}
	
	public void parse(boolean show) throws IOException, EvaluationException{
		this.parse();
		
		if(show){
			System.out.println(vars);
			System.out.println(question);
			System.out.println(answer);
			System.out.println(solution);
		}
	}
	
	public String getQuestion(){
		return question.toString();
	}
	
	public String getAnswer(){
		return answer.toString();
	}
	
	public String getSolution(){
		return solution.toString();
	}
	
	public String getVariables(){
		return vars.toString();
	}
	
	
	/*
	 * returns num number of choices for a multiple choice question 
	 */
	public ArrayList<String> getMultipleChoice(int num){
		if(choicesGiven){
			Collections.shuffle(this.choices);
			return choices;
		}
		else{
			try {
				if(evaluator.getVariableValue("answer").equals("")){
					return null;
				}
			} catch (EvaluationException e) {
				// TODO Auto-generated catch block
				return null;
			}
			
			try {
				System.out.println(evaluator.getVariableValue("answer"));
			} catch (EvaluationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
			Evaluator newevaluator = new Evaluator();
			ParseUtilities.loadFunctions(newevaluator); 
			long time = System.currentTimeMillis();
			
			ArrayList<String> choices = new ArrayList<String>(num);
			try {
				choices.add(0,evaluator.getVariableValue("answer"));
			} catch (EvaluationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String trueans =  choices.get(0);
			
			for(int i = 1; i < num; ++i){
				
				int answerloc = 0;
				
				for(int j = 0; j < varstatements.size(); ++j){
					if(varstatements.get(j).get(0).equalsIgnoreCase("answer")){
						answerloc = j;
						continue;
					}
					else{
						try {
							newevaluator.putVariable(varstatements.get(j).get(0), newevaluator.evaluate(varstatements.get(j).get(1)));
						} catch (EvaluationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				StringBuilder ans = new StringBuilder(varstatements.get(answerloc).get(1));
				try {
					ParseUtilities.replaceVariables(ans , newevaluator);
					ParseUtilities.replaceEvaluations(ans , newevaluator);
				} catch (EvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ans.toString().equals(trueans)){
					i-=1;
					newevaluator.clearVariables();
					continue;
				}
				
				choices.add(i, ans.toString());
				System.out.println(ans.toString());
				newevaluator.clearVariables();
			}
			return choices;
		}
	}
	
	
	private void readVariables() throws IOException, EvaluationException{
		vars.append("Variables:\\\\");
		boolean varstart = false;
		while(true){
			//test String line = reader.readLine();
			String line = getNextLine();
			
			if(line == null ||line.equalsIgnoreCase("Question")) return;
			if(line.equals("")) continue;
			if(line.equalsIgnoreCase("VARIABLES")){
				varstart = true;
				continue;
			}
			if(!varstart) continue;
			
			
			char[] array = line.toCharArray();			
			
			StringTokenizer parser = new StringTokenizer(line);
			
			String name = parser.nextToken(); 
			int equals  = 0;
			for(int i = 0; i < array.length; ++i){
				if(array[i] == '='){
					equals = i;
					break;
				}
			}
			
			Vector<String> thing = new Vector<String>(2); 
			thing.add(0,name);
			thing.add(1, line.substring(equals+1, line.length()));
			varstatements.add(thing);
					
			String value;
			try{
				value = evaluator.evaluate(line.substring(equals+1, line.length()));
			}
			catch(EvaluationException e){
				value = line.substring(equals+1, line.length());
			}
			evaluator.putVariable(name, value);
			
			vars.append(name);
			vars.append("=");
			vars.append(value);
			vars.append("\\\\");
			
			
		}
	}
	
	private void readQuestion() throws IOException, EvaluationException{
		question.append("Question:");
		question.append("\\\\");
		while(true){// read question
			//test String line = reader.readLine();
			
			String line = seeNextLine();
			if(line == null || line.equalsIgnoreCase(choicesString)||line.equalsIgnoreCase("Answer")) return;
			line = getNextLine();
			StringBuilder inputbuilder = new StringBuilder(line);
			
			
			replaceVariables(inputbuilder);
			replaceEvaluations(inputbuilder);
			
			question.append(inputbuilder);
			question.append("\\\\");
		}
		
	}
	
	private void readChoices(){
		while(true){
			if(seeNextLine().equalsIgnoreCase(choicesString)){
				getNextLine();
				continue;
			}
			if(seeNextLine().equalsIgnoreCase("Answer")|| seeNextLine() == null){
				return;
			}
			else{
				StringBuilder line = new StringBuilder(getNextLine());
				try {
					replaceVariables(line);
					replaceEvaluations(line);
				} catch (EvaluationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("added"+ line.toString());
				this.choices.add(line.toString());
				this.choicesGiven= true;
			}
		}
	}
	
	private void readAnswer() throws IOException, EvaluationException{
		answer.append("Answer:\\\\");
		
		while(true){			
			String line = seeNextLine();
			if(line == null ||line.equalsIgnoreCase("answer")||line.equals("")|| line.equalsIgnoreCase("Solution")||line.equalsIgnoreCase("Finish")){
				if(line.equalsIgnoreCase("Solution")||line.equalsIgnoreCase("Finish")){
					return;
				}
				getNextLine();
			}
			else{
				break;
			}			
		}
		
		//test String input = reader.readLine();
		String input = getNextLine();
		if(input == null) return;
		StringBuilder inputbuilder = new StringBuilder(input);
		

		Vector<String> thing = new Vector<String>(2); 
		thing.add(0,"answer");
		thing.add(1, input);
		varstatements.add(thing);
		
		
		replaceVariables(inputbuilder);
		replaceEvaluations(inputbuilder);
		
		ans = inputbuilder.toString();
		
		String out = inputbuilder.toString();
		evaluator.putVariable("answer", out);
		answer.append(out);
		answer.append("\\\\");
		
	}
	
	
	private void readSolution() throws IOException, EvaluationException{
		while(true){
			// test String input = reader.readLine();
			String input = getNextLine();
			if(input == null || input.equalsIgnoreCase("Solution")) break;
			if(input.equals("")) continue;
			
		}
		solution.append("\\\\Solution:\\\\");
		while(true){
			
			//test String input = reader.readLine();
			String input = getNextLine();
			
			if(input== null || input.equalsIgnoreCase("finish")) return;
			
			
				StringBuilder inputbuilder = new StringBuilder(input);
				if(input.equalsIgnoreCase("finish")) break;
				
				replaceVariables(inputbuilder);
				replaceEvaluations(inputbuilder);
				solution.append(inputbuilder);
				
			solution.append("\\\\");
		}
	}
	
	
	
	private void replaceVariables(StringBuilder string) throws EvaluationException{
		ParseUtilities.replaceVariables(string, evaluator);
	}
	
	
	private void replaceEvaluations(StringBuilder string) throws EvaluationException{
		ParseUtilities.replaceEvaluations(string, evaluator);
	}
	
	private void loadFunctions(){
		ParseUtilities.loadFunctions(evaluator);
	}
	
	
	private String seeNextLine(){
		if(linenum>=fileLines.size())
			return null;
		
		String s = fileLines.get(linenum);
		return s;
	}
	
	private String getNextLine(){
		if(linenum>=fileLines.size())
			return null;
		
		String s = fileLines.get(linenum);
		linenum++;
		return s;
	}
	
}
