package com.dennis.mathacademy;

import java.io.BufferedReader;
import java.io.IOException;

import net.sourceforge.jeval.EvaluationException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.dennis.mathacademy.ui.TeXView;

public class AnsweredDialogFragment extends DialogFragment {
    	
	String title;
	String solution;
	QuestionFragment parent;
	OnContinueListener mContinueCallback; 
	
	public interface OnContinueListener{
		public void reloadQuestion();
		public void nextQuestion();
		public void back();
	}
	
	public AnsweredDialogFragment(){//empty constructor
		super();
	}

	static AnsweredDialogFragment newInstance(String title, String ans) {
    	return newInstance(title, ans, null);
	}
	
	static AnsweredDialogFragment newInstance(String title,String ans, QuestionFragment parent) {
    	AnsweredDialogFragment f = new AnsweredDialogFragment();
    	f.solution = ans;
        // Supply num input as an argument.
        f.title = title;
        f.parent = parent;
        return f;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
        try {
        	mContinueCallback = (OnContinueListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnResetListener");
        }

	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer, container);
        getDialog().setTitle(this.title);
        
        TeXView solutionView = (TeXView)view.findViewById(R.id.solution_view);
        if(solution!= null){
        	solutionView.parseTex(solution);
        }
        
        Button restart = (Button) view.findViewById(R.id.reset_button);
        restart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mContinueCallback.reloadQuestion();
				Log.d("mathacademy button", "restart");
				dismiss();
			}
        });
        
    	((Button)view.findViewById(R.id.back_button)).setOnClickListener(new OnClickListener(){
    		public void onClick(View v){
    			Log.d("mathacademy button", "back");
    			dismiss();
    		}
    	});
    	
    	
    	 Button nextButton = (Button) view.findViewById(R.id.next_button);
    	 nextButton.setOnClickListener(new OnClickListener(){
 			@Override
 			public void onClick(View v) {
 				mContinueCallback.nextQuestion();
 				Log.d("mathacademy button", "next");
 				dismiss();
 			}
         });
        
        
        
        return view;
    }


}
