package org.varnalab.december.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class VarnaLabDecemberDemoMainActivity extends Activity {

	Button simpleButtonOne;
	Button simpleButtonTwo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_varna_lab_december_demo_main);
		
		simpleButtonOne = 
			(Button) findViewById(R.id.simpleButtonOne);
		
		simpleButtonTwo = 
			(Button) findViewById(R.id.simpleButtonTwo);
		
		simpleButtonOne.setText(R.string.one);
		simpleButtonTwo.setText(R.string.two);
		
		simpleButtonOne.setBackgroundResource(R.drawable.greengradient);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.varna_lab_december_demo_main, menu);
		return true;
	}

}
