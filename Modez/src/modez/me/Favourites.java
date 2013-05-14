package modez.me;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Favourites extends Activity implements OnClickListener
{
	//keep track of camera capture intent
	final int CAMERA_CAPTURE = 1;
	
	protected RelativeLayout root;
	protected View navigationmenuportrait;
	protected View navigationmenulandscape;
	protected LinearLayout body;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites);
		
		root = (RelativeLayout)findViewById(R.id.root);
		body = (LinearLayout)findViewById(R.id.body);
		
		scrollViewFix(2);
		
		Button homeButton = (Button)findViewById(R.id.homepageButton);
		homeButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(Favourites.this, Modez.class);	
				Favourites.this.startActivity(intent);
			}
		});

		Button cameraButton = (Button)findViewById(R.id.favouritesCameraButton);
		cameraButton.setOnClickListener(this);
		
	}
	
	/**
	 * ScrollView inside ScrollView Scrolling problem
	 * Article : http://trivedihardik.wordpress.com/2011/09/19/scrollview-inside-scrollview-scrolling-problem/
	 * Stackoverflow : http://stackoverflow.com/questions/6210895/listview-inside-scrollview-is-not-scrolling-on-android
	 */
	protected void scrollViewFix(final int activityType)
	{
		final ScrollView scrollView = (ScrollView)findViewById(R.id.parentScrollView);
		
		scrollView.setOnTouchListener(new View.OnTouchListener() 
		{	
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				switch (activityType) 
				{
					case 1: //MainActivity
						scrollView.requestDisallowInterceptTouchEvent(false);
					break;
				}
				
				return false;
			}
		});
		
		ArrayList<ListView> listViews = new ArrayList<ListView>();
		switch (activityType) 
		{
			case 1: //MainActivity
				
				break;
			default:
				break;
		}
		
		for (ListView listView : listViews)
		{
			listView.setOnTouchListener(new View.OnTouchListener()
			{
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					// Disallow the touch request for parent scroll on touch of child view
					 scrollView.requestDisallowInterceptTouchEvent(true);
					return false;
				}
			});
		}
	}
	
	/**
	 * Click method to handle user pressing button to launch camera
	 */
	public void onClick(View v)
	{
		if (v.getId() == R.id.favouritesCameraButton)
		{
			try
			{
				//use standart intent to capture an image
				Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//we will handle the returned data in onActivityResult
				startActivityForResult(captureIntent, CAMERA_CAPTURE);
			}
			catch (ActivityNotFoundException e) 
			{
				////display an error message
				Toast toast = Toast.makeText(this, R.string.cameraError, Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK)
		{
			Intent intent = new Intent(Favourites.this, PhotoPreview.class);	
			Favourites.this.startActivity(intent);
		}
	}
}
