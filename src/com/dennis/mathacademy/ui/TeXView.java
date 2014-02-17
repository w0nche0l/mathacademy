package com.dennis.mathacademy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TeXView extends WebView {

	private String data;
	long time;
	
	public TeXView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		time = System.currentTimeMillis();
		
		this.setWebChromeClient(new WebChromeClient(){
			
			public void onProgressChanged(WebView view, int progress){
				if(progress >= 100 ){
					Log.d("mathacademy", "loadedjavascriptafter" + Long.toString(System.currentTimeMillis()-time));
				}
			}
		});
		
		
		this.setWebViewClient(new WebViewClient(){
			
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("mathacademy", "Processing webview url click..." +url);
                view.loadUrl(url);
                return true;
            }
			
			@Override
			public void onPageFinished(WebView view, String url){
				Log.d("mathacademy", "loadedafter" + Long.toString(System.currentTimeMillis()-time));
				setVisibility(VISIBLE);;
			}
		});
        
		getSettings().setUseWideViewPort(true);
		getSettings().setJavaScriptEnabled(true);
		getSettings().setBuiltInZoomControls(true);
		loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
		                      +"MathJax.Hub.Config({ " 
		                      	+"displayAlign:\"left\","
		                      	+ "messageStyle: \"none\","
							  	+"showMathMenu: false, "
							  	+"jax: ['input/TeX','output/HTML-CSS'], "
							  	+"extensions: ['tex2jax.js'], " 
							  	+"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
							  	  +"'noErrors.js','noUndefined.js'] } "
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script><span id='math'></span>","text/html","utf-8","");
	}
	
	
	public void printRawTex(String raw){
		data = raw;
		loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
		           +raw+"\\\\]';");
		loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}
	
	
	public void parseTex(String in){
		data = in;
		Log.d("MathAcademyParser", in);
		loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
		           +doubleEscapeTeX(in)+"\\\\]';");
		loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}

	private String doubleEscapeTeX(String s) {
		String t="";
		for (int i=0; i < s.length(); i++) {
			if (s.charAt(i) == '\'') t += '\\';
			if (s.charAt(i) != '\n') t += s.charAt(i);
			if (s.charAt(i) == '\\') t += "\\";
		}
		return t;
	}
	
	public boolean isInEditMode(){
		return true;
	}
	
	public String getRawString(){
		return data;
	}
	
}
