package com.dennis.mathacademy.parser;

public class MathChecks {
		
	
	public static boolean isPerfectSquare(int x){
		if(Math.sqrt(x)%1 == 0)
			return true;
		return false;
	}
	
	public static boolean isPrime(int x){
		int upto  = (int)Math.sqrt(x);
		
		if(x%2 == 0 ) return false;
		for(int i =  3; i < upto; i+=2){
			if(x%i == 0) return false;
		}
		
		return true;
	}
	
}
