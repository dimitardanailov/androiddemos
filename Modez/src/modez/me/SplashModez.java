package modez.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashModez extends Activity 
{
	private static String TAG = SplashModez.class.getName();
	private static long SLEEP_TIME = 5; //Sleep for some time
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Removes title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Removes notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		
		//Start timer and launch main activity
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}
	
	private class IntentLauncher extends Thread 
	{
		/**
		 * Sleep for some time and than start new activity.
	     */
		
		@Override 
		public void run() 
		{
			try 
	    	  {
	    		// Sleeping
	            Thread.sleep(SLEEP_TIME * 1000);
	          }
	          catch(InterruptedException e)
	          {
	        	  Log.e(TAG, e.getMessage());
	          }

	          // Run next activity
	          Intent intent = new Intent(SplashModez.this, Modez.class);
	          SplashModez.this.startActivity(intent);
	          SplashModez.this.finish();
		}
		
	}
}
