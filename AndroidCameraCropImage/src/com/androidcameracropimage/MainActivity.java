package com.androidcameracropimage;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Application demonstrates capturing and cropping camera images
 * - user presses button to capture an image using the device camera
 * - when they return with the captured image Uri, the app launches the crop action intent
 * - on returning from the crop action, the app displays the cropped image
 */

public class MainActivity extends Activity implements OnClickListener {
	
	//keep track of camera capture intent
	final int CAMERA_CAPTURE = 1;
	//keep track of cropping intent
	final int PIC_CROP = 2;
	//captured picture uri
	//A Uniform Resource Identifier that identifies an abstract or physical resource, as specified by RFC 2396. 
	private Uri pictureUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//retrive a reference to the UI Button
		Button captureButton = (Button)findViewById(R.id.capture_button);
		//handle button clicks
		captureButton.setOnClickListener(this);
	}
	
	/**
	 * Click method to handle user pressing button to launch camera
	 */

	public void onClick(View v)
	{
		if (v.getId() == R.id.capture_button)
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
				//display an error message
				String errorMessage = "Whoops - your device doesn't support capturing camera";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	/**
	 * Handle user returning from both capturing and cropping the image
	 */
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			//user is returing from capturing an image using the camera
			if (requestCode == CAMERA_CAPTURE)
			{
				//get the uri for the captured image
				pictureUri = data.getData();
				//carry out the crop operation
				performCrop();
			}
			//user is returnin from cropping the image
			else if (requestCode == PIC_CROP)
			{
				//get the returned data
				Bundle extras = data.getExtras();
				
				//get the cropped bitmap
				Bitmap thepicture = extras.getParcelable("data");
				
				//retrieve a reference to the ImageView
				ImageView pictureView = (ImageView)findViewById(R.id.picture);
				//display the returned cropped image
				pictureView.setImageBitmap(thepicture);
			}
		}
	}
	
    /**
     * Helper method to carry out crop operation
     */
	private void performCrop()
	{
		try
		{
			//call the standart crop action intent(the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			//indicate image type and Uri
			cropIntent.setDataAndType(pictureUri, "image/*");
			//set crop properties
			cropIntent.putExtra("crop", "true");
			//indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			//indicate output X and Y
			cropIntent.putExtra("outputX", 256);
	    	cropIntent.putExtra("outputY", 256);
	    	//retrieve data on return
	    	cropIntent.putExtra("return-data", true);
	    	//start the activity - we handle returning in onActivityResult
	    	startActivityForResult(cropIntent, PIC_CROP);
		}
		catch (ActivityNotFoundException e) 
		{
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
