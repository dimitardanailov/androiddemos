package modez.me;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Modez extends Activity implements OnClickListener
{

	//keep track of camera capture intent
	final int CAMERA_CAPTURE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		View topHeader = findViewById(R.id.topheader);
		Drawable background = topHeader.getBackground();
		background.setAlpha(95);
		
		View wrapperTeamOfWeek = findViewById(R.id.wrapperTeamOfWeek);
		background = wrapperTeamOfWeek.getBackground();
		background.setAlpha(95);
		
		Button homeRateButton = (Button)findViewById(R.id.homeRateButton);
		homeRateButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(Modez.this, Favourites.class);	
				Modez.this.startActivity(intent);
			}
		});
		
		Button homeCameraButton = (Button)findViewById(R.id.homeCameraButton);
		homeCameraButton.setOnClickListener(this);
	}
	
	/**
	 * Click method to handle user pressing button to launch camera
	 */
	public void onClick(View v)
	{
		if (v.getId() == R.id.homeCameraButton)
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modez, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK)
		{
			Intent intent = new Intent(Modez.this, PhotoPreview.class);	
			Modez.this.startActivity(intent);
		}
	}
}
