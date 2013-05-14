package com.varnalabwebviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NextPage extends Activity 
{
	WebView webView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		webView = (WebView)findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setWebViewClient(new WebClient());
		webView.setWebChromeClient(new WebChromeClient()); 
		webView.loadUrl(getIntent().getExtras().getString("url"));
	}
	
	public class WebClient extends WebViewClient 
	{
		ProgressDialog progressDialog;
		
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        {
        	Intent i = new Intent(NextPage.this, NextPage.class);
        	i.putExtra("url", url);
            startActivity(i); 
            
            return true;
        }
        
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
        	progressDialog = ProgressDialog.show(NextPage.this, "",getString(R.string.loading), true);
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
