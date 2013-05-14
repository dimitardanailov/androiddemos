package com.varnalabdbjson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SingleMenuItemActivity extends Activity {
	// JSON Node names
	private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_CREATEDAT = "created_at";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		// getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String views = in.getStringExtra(TAG_VIEWS);
        String createdAt = in.getStringExtra(TAG_CREATEDAT);
        String description = in.getStringExtra(TAG_DESCRIPTION);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name);
        TextView lblViews = (TextView) findViewById(R.id.views);
        TextView lblCreatedAt = (TextView) findViewById(R.id.createdAt);
        TextView lblDescription = (TextView) findViewById(R.id.description);
        
        // Labels in this layouts
        TextView labelDescription = (TextView) findViewById(R.id.labelDescription);
        
        lblName.setText(name);
        lblViews.setText(views);
        lblCreatedAt.setText(createdAt);
        
        lblDescription.setText(description);
        labelDescription.setVisibility(View.VISIBLE);
        lblDescription.setVisibility(View.VISIBLE);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.varna_lab_db_json, menu);
		return true;
	}
}
