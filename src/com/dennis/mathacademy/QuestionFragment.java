package com.dennis.mathacademy;

import java.io.IOException;

import com.dennis.mathacademy.MainActivity;








import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import net.sourceforge.jeval.EvaluationException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;


import android.widget.LinearLayout;

import com.dennis.mathacademy.ui.TeXView;
import com.dennis.mathacademy.parser.Parser;


public class QuestionFragment extends Fragment implements OnTouchListener{
	
	private TeXView questionView;
	private TeXView choice1View;
	private TeXView choice2View;
	private TeXView choice3View;
	private TeXView choice4View;
	
	private View view;
	private Parser parser;
	private boolean choicesCreated = false;
	InputStreamReader originalFile;
	String originalFileName;
	String ans; 
	String solution;
	HashMap<String,String> question;
	QuestionManager questionManager;
	int idSelected = -5;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d("blah","blahblah");
    	view = inflater.inflate(R.layout.question, container, false);
    	
    	questionView = (TeXView) view.findViewById(R.id.question_view);
    	choice1View = (TeXView) view.findViewById(R.id.choice1_view);
    	choice2View = (TeXView) view.findViewById(R.id.choice2_view);
    	choice3View = (TeXView) view.findViewById(R.id.choice3_view);
    	choice4View = (TeXView) view.findViewById(R.id.choice4_view);
    	
    	android.os.SystemClock.sleep(1000);
		
		questionView.setOnTouchListener(this);
		choice1View.setOnTouchListener(this);
		choice2View.setOnTouchListener(this);
		choice3View.setOnTouchListener(this);
		choice4View.setOnTouchListener(this);
		
		questionView.setBackgroundColor(getResources().getColor(R.color.blue));
		choice1View.setBackgroundColor(getResources().getColor(R.color.red));
		choice2View.setBackgroundColor(getResources().getColor(R.color.cream));
		choice3View.setBackgroundColor(getResources().getColor(R.color.lightblue));
		choice4View.setBackgroundColor(getResources().getColor(R.color.green));
		
		
		
		
		try {
			loadQuestion(((MainActivity)this.getActivity()).questionManager.readQuestion());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return view;
	}
	
	public QuestionManager getQuestionManager(){
		return questionManager;
	}
	
	public void setQuestionManager(QuestionManager qm){
		questionManager = qm;
	}
	
	
	public void loadQuestion(HashMap<String,String> data){	
		question = data;
		questionView.parseTex(data.get("question"));
		ans = data.get("answer");
		solution = data.get("solution");
		
		choice1View.parseTex(data.get("choice1"));
		choice2View.parseTex(data.get("choice2"));
		choice3View.parseTex(data.get("choice3"));
		choice4View.parseTex(data.get("choice4"));
		
		choicesCreated = true;
	}
	
	
	public Parser getParser(){
		return parser;
	}
	
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			Log.d("mathacademy", "DOWN");
			
			if(choicesCreated){
				int id = v.getId();
				if(id == questionView.getId()){
					Log.d("mathacademy", "question");
				}
				
				if(id == choice1View.getId() || id == choice2View.getId() || id == choice3View.getId() || id == choice4View.getId() ){
					
					if (id != idSelected){
						LinearLayout parent = (LinearLayout)v.getParent();
						parent.setBackground(this.getResources().getDrawable(R.id.selectedborder));
					}
					else {
						if(((TeXView)view.findViewById(id)).getRawString().equals(ans)){
							Log.d("mathacademy", "correct :D");
							FragmentManager m = this.getActivity().getSupportFragmentManager();
							AnsweredDialogFragment answer = AnsweredDialogFragment.newInstance("Correct!", solution, this);
							answer.show(m, "answer");
						}
						else{
							Log.d("mathacademy", "Incorrect :(");
							FragmentManager m = this.getActivity().getSupportFragmentManager();
							AnsweredDialogFragment answer = AnsweredDialogFragment.newInstance("Incorrect!",solution, this);
							answer.show(m, "answer");
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	
	
	
}
