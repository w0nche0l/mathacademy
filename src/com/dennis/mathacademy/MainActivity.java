package com.dennis.mathacademy;


import java.io.IOException;

import net.sourceforge.jeval.EvaluationException;

import com.dennis.mathacademy.R;
import com.dennis.mathacademy.io.LessonReader;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


//must use fragment activity!

public class MainActivity extends ActionBarActivity implements AnsweredDialogFragment.OnContinueListener{
	
	 public QuestionManager questionManager; 
	 private DrawerLayout mDrawerLayout;
	 private ListView mDrawerList;
	 private ActionBarDrawerToggle mDrawerToggle;

	 
	    
	    
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		Log.d("blah", "blah");
		
		setContentView(R.layout.activity_main);
		
		
		if(getSupportFragmentManager().findFragmentByTag("MAIN") == null){
			MainFragment main = new MainFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.content_frame, main, "MAIN");
			transaction.commit();
		}
		
		LessonReader a= new LessonReader();
		a.setTopDirectory("334");
		
		questionManager = new QuestionManager(this);
		questionManager.addQuestion("a.template");
		questionManager.addQuestion("b.template");
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerList= (ListView)findViewById(R.id.left_drawer);
		Log.d("blah", "blah");
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);  
        
        
        
	}
	
	public void nextFragment(){
		QuestionFragment newFragment = new QuestionFragment();
		newFragment.setQuestionManager(this.questionManager);
		FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack("QUESTION");
		transaction.replace(R.id.content_frame , newFragment, "QUESTION");		
		transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		transaction.commit();
	
	}

	public void reloadQuestion(){
		QuestionFragment questionFrag = (QuestionFragment) this.getSupportFragmentManager().findFragmentByTag("QUESTION");
		try {
			questionFrag.loadQuestion(questionManager.readQuestion());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void nextQuestion() {
		QuestionFragment questionFrag = (QuestionFragment) this.getSupportFragmentManager().findFragmentByTag("QUESTION");
		try {
			questionFrag.loadQuestion(questionManager.nextQuestion());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void back() {
		
	}
	
}
