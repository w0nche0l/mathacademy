package com.dennis.mathacademy.parser;

import java.util.ArrayList;

public class MathGenerator {
	
	
	/*
	 * Generates a random prime int between start and end, inclusive.
	 */
	public static int generatePrime(int start, int end){ 
		while(true){
			int answer =(int) (Math.random()*(end-start)+start);
			if(MathChecks.isPrime(answer));
			return answer;
		}
	}
	
	/*
	 * Generates a random prime int between start and end, inclusive.
	 */
	public static int generateInt(int start, int end){
		
		int answer = (int) (Math.random()*(end-start)+start);
		return answer;
	}
	
	
	/*
	 * Generates a random square int between start^2 and end^2, inclusive.
	 */	
	public static int generateSquare(int start, int end){
		return ((int) Math.pow(Math.random()*(end-start)+start , 2));
	}
	
	/*
	 * Generates a random power of pow, from start^pow to end^pow
	 */
	public static int generatePower(int start, int end, int pow){
		return ((int) Math.pow(Math.random()*(end-start)+start , pow));
	}
	
	public static int generateList(ArrayList list){
		return ((int) Math.random()*(list.size()));
	} 
	
	
	
	
}
