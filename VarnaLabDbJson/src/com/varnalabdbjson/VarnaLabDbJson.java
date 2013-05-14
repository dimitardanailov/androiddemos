package com.varnalabdbjson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VarnaLabDbJson extends ListActivity {
	
	// JSON Node names
	private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_CREATEDAT = "created_at";
    
	// contacts JSONArray
    JSONArray categories = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_varna_lab_db_json);
		
		InputStream inputStream = null;
		String result = null;
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//Example for param : 
		//nameValuePairs.add(new BasicNameValuePair("id", 1));
		inputStream = Request.setHttpRequest("http://androidjson.itweb-projects.com/categories.php", nameValuePairs);
		
		// Hashmap for ListView
        ArrayList<HashMap<String, String>> categoriesList = new ArrayList<HashMap<String, String>>();
		
		if(inputStream != null)
    	{
    		result = StringResponse.convertResponseToString(inputStream);
    		result = result.trim();
    	}
		
		if(result.equals("null") || result == null)
    	{
			Toast.makeText(getBaseContext(), "Invalid request",Toast.LENGTH_LONG).show();
			finish();
    	}
		else 
		{
	    	
	    	try
	    	{
	    		JSONArray jArray = new JSONArray(result);
	        	JSONObject json_data = null;
	        	for(int i=0; i < jArray.length(); i++)
	        	{
	               json_data = jArray.getJSONObject(i);
	               
	               try 
	   			   {
	            	   String id = json_data.getString(TAG_ID);
	        	       String name = json_data.getString(TAG_NAME);
	        	       String views = json_data.getString(TAG_VIEWS);
	        	       String description = json_data.getString(TAG_DESCRIPTION);
	        	       String createdAt = json_data.getString(TAG_CREATEDAT);
	                   
	        	       // creating new HashMap
	                   HashMap<String, String> map = new HashMap<String, String>();
	        	       
	                   // adding each child node to HashMap key => value
	                   map.put(TAG_ID, id);
	                   map.put(TAG_NAME, name);
	                   map.put(TAG_VIEWS, views);
	                   map.put(TAG_DESCRIPTION, description);
	                   map.put(TAG_CREATEDAT, createdAt);
	                   
	                   categoriesList.add(map);
	   			    } 
	   				catch (ParseException e) 
	   				{
	   					e.printStackTrace();
	   				}
	           }
	        }
	        catch(JSONException e1)
	        {
	        	Toast.makeText(getBaseContext(),"ERROR",Toast.LENGTH_LONG).show();
	        } 
	        catch (ParseException e1) {
	        	Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_LONG).show();
	        }
	        
	        /**
	         * Updating parsed JSON data into ListView
	         * */
	        ListAdapter adapter = new SimpleAdapter(this, categoriesList,
	                R.layout.list,
	                new String[] { TAG_NAME, TAG_VIEWS, TAG_CREATEDAT, TAG_DESCRIPTION}, new int[] {
	                        R.id.name, R.id.views ,R.id.createdAt, R.id.description});
	 
	        setListAdapter(adapter);
	        
	        /**
	         * Selecting single ListView item
	         */
	        ListView listViewItem = getListView();
	        
	        listViewItem.setOnItemClickListener(new OnItemClickListener() {
	        	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
	                String views = ((TextView) view.findViewById(R.id.views)).getText().toString();
	                String createdAt = ((TextView) view.findViewById(R.id.createdAt)).getText().toString();
	                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
	                
	                // Starting new intent
	                Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
	                in.putExtra(TAG_NAME, name);
	                in.putExtra(TAG_VIEWS, views);
	                in.putExtra(TAG_CREATEDAT, createdAt);
	                in.putExtra(TAG_DESCRIPTION, description);
	                startActivity(in);
	            }
	        });
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.varna_lab_db_json, menu);
		return true;
	}
}
