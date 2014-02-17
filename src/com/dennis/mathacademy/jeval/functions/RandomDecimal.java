package com.dennis.mathacademy.jeval.functions;

import java.util.ArrayList;
import java.util.Vector;

import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

public class RandomDecimal implements Function{
	@Override
	public FunctionResult execute(Evaluator evaluator, String arguments)
			throws FunctionException {

		
		
		ArrayList<Double> numbers = FunctionHelper.getDoubles(arguments, EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);
		
		Vector<Double> exclude = new Vector<Double>();
		int decimalplace = 5;
		
		if(numbers.size()>2){
			decimalplace = (int) numbers.get(2).doubleValue();
		}
		
		double a = numbers.get(0);
		double b = numbers.get(1);
		double answer;
		while(true){
		
			answer =(int) (Math.random()*(b-a)+a);
			if(exclude != null){
				boolean cont = false;
				for(int i =0; i < exclude.size();++i){
					if(answer == i )
						cont = true;
				}
				if(cont)
					continue;
			}
			break;
		}
		
		answer = answer%Math.pow(10, -decimalplace);
		
		return new FunctionResult(Double.toString(answer), FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
	}

	@Override
	public String getName() {
		
		return "random_decimal_range";
	}

}
