package com.dennis.mathacademy.parser;

import com.dennis.mathacademy.jeval.functions.RandomInteger;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class ParseUtilities {

	
	static public void loadFunctions(Evaluator eval){
		eval.putFunction(new RandomInteger());
	}
	
	public static void replaceVariables(StringBuilder string, Evaluator customEvaluator) throws EvaluationException{
		for(int i = 0; i < string.length(); ++i){
			if(string.charAt(i) == '#'){
				int start = i ;
				
				while(true){
					i++;
					if(string.charAt(i)== '#')break;
				}
				
				String varname = new String(string.substring(start+1, i));
				string.replace(start, i+1, customEvaluator.getVariableValue(varname));
				i = start-1;
			}
		}
	}
	
	
	static public void replaceEvaluations(StringBuilder string, Evaluator customEvaluator) throws EvaluationException{
		for(int i = 0; i < string.length(); ++i){
			if(string.charAt(i) == '!'){
				int equationstart = i ;
				boolean typespecified = false;
				
				while(true){
					i++;
					if(string.charAt(i)== '|'){
						typespecified = true;
						break;
					}
					if(string.charAt(i)== '!'){
						break;
					}
				}
				
				if(!typespecified){
					String equation = new String(string.substring(equationstart+1, i));
					string.replace(equationstart, i+1, customEvaluator.evaluate(equation));
					i = equationstart-1;
					continue;
				}
				
				String type = new String(string.substring(equationstart+1, i));
				int evaluationstart = i;
				while(true){
					i++;
					if(string.charAt(i)== '!')break;
				}
				
				String equation = new String(string.substring(evaluationstart+1, i));
				
				if(type.equalsIgnoreCase("integer")){
					string.replace(equationstart, i+1, Integer.valueOf((int)Double.parseDouble(customEvaluator.evaluate(equation))).toString());
				}
				else if(type.equalsIgnoreCase("Decimal")){
					string.replace(equationstart, i+1, customEvaluator.evaluate(equation));
				}
				i = equationstart-1;
			}
		}
	}
	
}
