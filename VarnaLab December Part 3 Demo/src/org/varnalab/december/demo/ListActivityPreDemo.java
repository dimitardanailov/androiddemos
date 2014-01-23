package org.varnalab.december.demo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.varnalab.december.demo.adapters.WhiskeyAdapter;
import org.varnalab.december.demo.interfaces.iWhiskeyListInterface;
import org.varnalab.december.demo.libs.Request;
import org.varnalab.december.demo.libs.StringResponse;

import android.app.Activity;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivityPreDemo extends Activity
	implements iWhiskeyListInterface{

	private static String TAG = ListActivityPreDemo.class.getName();
	
	String[] tags = { TAG_WHISKEYNAME };
	
	WhiskeyAdapter adapter;
	
	private Boolean sendRequest = true;
	
	private ListView whiskeyListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_list_activity);
		
		initializeLayoutElements();
		
		sendDatabaseRequest();
	}
	
	private void initializeLayoutElements() {
		whiskeyListView = (ListView) findViewById(R.id.list);
	}
	
	/**
	 * Try to send request to database
	 */
	private void sendDatabaseRequest() {
		if (sendRequest) {
			//Async task

			Toast.makeText(getBaseContext(), "Loading", Toast.LENGTH_LONG).show();

	        JSONTask jsonTask = new JSONTask();
	    		jsonTask.execute(LISTWHISKEYURI);
		}
	}
	
	private void updateListView(ArrayList<HashMap<String, String>> whiskeyList) {
		
		if (!whiskeyList.isEmpty()) {
			adapter = new WhiskeyAdapter(this, whiskeyList);
			adapter.setNotifyOnChange(true);
			
			whiskeyListView.setAdapter(adapter);
			
 		} else {
 			sendRequest = false;
 		}
	}
	
	public class JSONTask extends AsyncTask<String, Integer, String> {
		
		@Override
		protected String doInBackground(String... pages) {
			
			int count = pages.length;
			String result = null;
			StringBuilder stringBuilder = new StringBuilder();
			InputStream inputStream = null;
			ArrayList<NameValuePair> nameValuePairs = null;
			
			for (int i = 0; i < count; i++) {
				String temPage = pages[i];
				inputStream = Request.
						setHttpRequest(temPage, "get", nameValuePairs);
				if (inputStream != null) {
					result = StringResponse.convertResponseToString(inputStream);
					result = result.trim();
					stringBuilder.append(result);
				}
			}
			
			return stringBuilder.toString();
		}
		
		protected void onPostExecute(String result) {
			if (result.equals("null") || result == null) {
				Toast.makeText(getBaseContext(), "Invalid Result", 
						Toast.LENGTH_LONG).show();
			} else {
				try {
					
					// Hashmap for ListView
					ArrayList<HashMap<String, String>> whiskeyList 
						= new ArrayList<HashMap<String, String>>();
					
					JSONObject items = new JSONObject(result);
					JSONArray arrayItems = items.getJSONArray("items");
					
					JSONObject json_data = null;
					for (int i = 0; i < arrayItems.length(); i++) {
						json_data = arrayItems.getJSONObject(i);
						
						try {
		        			
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
					
					updateListView(whiskeyList);
					
				} catch (JSONException e) {
					Toast.makeText(getBaseContext(), "Invalid JSON", Toast.LENGTH_LONG).show();
					Log.e(TAG, e.toString(), e);
				}
				
			} // END else (result.equals("null") || result == null) {
		}
	}
}
