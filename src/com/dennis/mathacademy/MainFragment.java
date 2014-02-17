package com.dennis.mathacademy;



import com.dennis.mathacademy.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainFragment extends Fragment
{
	private WebView w;
	private View view;
	
	
	private String doubleEscapeTeX(String s) {
		String t="";
		for (int i=0; i < s.length(); i++) {
			if (s.charAt(i) == '\'') t += '\\';
			if (s.charAt(i) != '\n') t += s.charAt(i);
			if (s.charAt(i) == '\\') t += "\\";
		}
		return t;
	}
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
	}
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d("blah","blahblah");
    	view = inflater.inflate(R.layout.main, container, false);
        Log.d("blah","blahblah");
        w = (WebView) view.findViewById(R.id.webview);
		w.getSettings().setJavaScriptEnabled(true);
		w.getSettings().setBuiltInZoomControls(false);
		w.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
		                      +"MathJax.Hub.Config({ " 
							  	+"showMathMenu: false, "
							  	+"jax: ['input/TeX','output/HTML-CSS'], "
							  	+"extensions: ['tex2jax.js'], " 
							  	+"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
							  	  +"'noErrors.js','noUndefined.js'] } "
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script><span id='math'></span>","text/html","utf-8","");
		EditText e = (EditText) view.findViewById(R.id.edit);
		e.setBackgroundColor(Color.LTGRAY);
		e.setTextColor(Color.BLACK);
		e.setText("");
		Button b = (Button) view.findViewById(R.id.button2);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				WebView w = (WebView) view.findViewById(R.id.webview);
				EditText e = (EditText) view.findViewById(R.id.edit);
				w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
				           +doubleEscapeTeX(e.getText().toString())+"\\\\]';");
				w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
		        
			}
		});
        
		
		Button c = (Button) view.findViewById(R.id.newfragmentbutton);
		
		c.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				launchFragment();
			}
		});
		
        return view;
    }

	public void launchFragment(){
		MainActivity parent = (MainActivity)this.getActivity();
		parent.nextFragment();
	}
    
    
}
