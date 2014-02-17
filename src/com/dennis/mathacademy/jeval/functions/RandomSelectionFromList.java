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

public class RandomSelectionFromList implements Function{
	@Override
	public FunctionResult execute(Evaluator evaluator, String arguments)
			throws FunctionException {

		ArrayList<String> choices  = FunctionHelper.getStrings(arguments, EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);
		
		return new FunctionResult(choices.get((int)Math.random()*choices.size()), FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
	}

	@Override
	public String getName() {
		
		return "random_from_list";
	}
}
