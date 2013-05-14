package com.varnalabdbjson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.widget.Toast;

public class StringResponse {
	
	public static String convertResponseToString(InputStream is)
	{
		StringBuilder sb = null;
		
		//Convert response to string  
        try
        {
      	  BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
      	  sb = new StringBuilder();
      	  String line = null;
      	  while ((line = reader.readLine()) != null) 
      	  {
      		 sb.append(line + "\n");
      	  }

      	  is.close();
        }
        catch(Exception e)
        {
        	Context context = null;
            Toast.makeText(context, "Invalid Argument", Toast.LENGTH_LONG).show();
        }
        
        return sb.toString();
	}
}