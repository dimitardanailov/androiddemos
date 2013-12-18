package org.varnalab.december.demo.libs;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.widget.Toast;

public class Request  
{
	static InputStream is = null;
	
	public static InputStream setHttpRequest
		(String url, 
		 String type,		
		 ArrayList<NameValuePair> nameValuePairs)
	{
		try
    	{
	    	if (type == "post") {
	    		is = methodPostRequest(url, nameValuePairs); 
	    	} else if (type == "get") {
	    		is = methodGetRequest(url);
	    	}
	        
	        return is;
    	}
    	catch(Exception e)
    	{
           Context context = null;
           Toast.makeText(context, "Invalid Request", Toast.LENGTH_LONG).show();
        }
		return is;
	}
	
	private static InputStream methodPostRequest(String url, ArrayList<NameValuePair> nameValuePairs) {
		InputStream inputStream = null;
		
		try
    	{
	    	//http post
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        inputStream = entity.getContent();
	        
	        return is;
    	}
    	catch(Exception e)
    	{
           Context context = null;
           Toast.makeText(context, "Invalid Request", Toast.LENGTH_LONG).show();
        }
    	
		return inputStream;
	}
	
	private static InputStream methodGetRequest(String url) {
		InputStream inputStream = null;
		
		try
    	{
			HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet(url);
	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();
	        inputStream = entity.getContent();
	        
	        return inputStream;
    	}
    	catch(Exception e)
    	{
           Context context = null;
           Toast.makeText(context, "Invalid Request", Toast.LENGTH_LONG).show();
        }
    	
    	return inputStream;
	}
}