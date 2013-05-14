package com.varnalabwebviews;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		webviewLoad(webview);
	}
	
	public void webviewLoad(WebView webview)
	{
		webview = (WebView)findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebClient());	
        webview.setWebChromeClient(new WebChromeClient()); 
        webview.loadUrl("http://en.m.wikipedia.org/wiki/Main_Page");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class WebClient extends WebViewClient
	{
		ProgressDialog progressDialog;
		
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	Intent i = new Intent(MainActivity.this, NextPage.class);
	    	i.putExtra("url", url);
	        startActivity(i);
	        return true;
	    }
		
		public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
        	progressDialog = ProgressDialog.show(MainActivity.this, "",getString(R.string.loading), true);
        	progressDialog.setCancelable(true);
    	}
        
    	public void onPageFinished(WebView view, String url) 
    	{
            if (progressDialog.isShowing()) 
            {
            	progressDialog.dismiss();
            }
    	}
	}

}
