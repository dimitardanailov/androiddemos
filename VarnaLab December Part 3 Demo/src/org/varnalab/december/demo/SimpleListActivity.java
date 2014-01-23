package org.varnalab.december.demo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.varnalab.december.demo.interfaces.iWhiskeyListInterface;

import org.varnalab.december.demo.libs.Request;
import org.varnalab.december.demo.libs.StringResponse;

import android.app.ListActivity;
import android.net.ParseException;
import android.os.Bundle;
import android.webkit.WebHistoryItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SimpleListActivity extends ListActivity 
	implements iWhiskeyListInterface {
	
	String[] tags = { TAG_WHISKEYNAME };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_list_activity);
		
		Toast.makeText(getBaseContext(), "Loading", Toast.LENGTH_LONG).show();
		
		InputStream inputStream = null;
		String result = null;
		ArrayList<NameValuePair> nameValuePairs = null;
		
		inputStream = Request.
			setHttpRequest(LISTWHISKEYURI, "get", nameValuePairs);
		
		if (inputStream != null) {
        	result = StringResponse.convertResponseToString(inputStream);
        	result = result.trim();
        }
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> whiskeyList 
			= new ArrayList<HashMap<String, String>>();
		
		if (!result.equals("null") && result != null) {
			
			try {
				//Toast.makeText(getBaseContext(), result ,Toast.LENGTH_LONG).show(); 
				JSONObject items = new JSONObject(result);
				JSONArray arrayItems = items.getJSONArray("items");
				
				//Toast.makeText(getBaseContext(), arrayItems.length() + "",Toast.LENGTH_LONG).show();
				JSONObject json_data = null;
				for(int i=0; i < arrayItems.length(); i++) {
					json_data = arrayItems.getJSONObject(i);
					
					try {
	        			//Toast.makeText(getBaseContext(),"Items",Toast.LENGTH_LONG).show();

	        			// creating new HashMap
	        			HashMap<String, String> map = new HashMap<String, String>();

	        			for (String tag : tags) {
	        				if (json_data.has(tag)) {
	        					//Log.d("Test", json_data.getString(tag));
	        					map.put(tag, json_data.getString(tag));
	        				} else {
	        					map.put(tag, "");
	        				}
	        			}

	        			whiskeyList.add(map);
	        			
	        		} catch (ParseException e) {
	        			e.printStackTrace();
					}
				}
				
			} catch(JSONException e1) {
	        	Toast.makeText(getBaseContext(),"ERROR",Toast.LENGTH_LONG).show();
	        } catch (ParseException e1) {
	        	Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_LONG).show();
	        }
	        
	        /**
	         * Updating parsed JSON data into ListView
	         * */
	        ListAdapter adapter = new SimpleAdapter(this, whiskeyList,
	                R.layout.whiskeylist,
	                new String[] { 
	        			TAG_WHISKEYNAME
	        		}, 
	                new int[] {
	        			R.id.name
	        		});
	        
	        setListAdapter(adapter);
			
		} else {
        	Toast.makeText(getBaseContext(), 
        			"Invalid request",Toast.LENGTH_LONG).show();
			finish();
        }
	}
}
