package beehiveandroid.bg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class BeehiveAndroidSplashActivity extends ActionBarActivity {
	
	private static String TAG = BeehiveAndroidSplashActivity.class.getName();
	private static int SLEEP_TIME = 3; // Sleep for some time
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Before loading Resources
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_beehive_android_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
			// Start timer and launch in activity
			IntentLauncher launcher = new IntentLauncher();
			launcher.start();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beehive_android_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_beehive_android_splash_screen, container, false);
			return rootView;
		}
	}
	
	private class IntentLauncher extends Thread {

		/**
		 * Sleep for some time and than start new activity.
		 */
		@Override 
		public void run() 
		{
			try 
			{
				// Sleeping
				Thread.sleep(SLEEP_TIME * 5000);
			}
			catch(InterruptedException e)
			{
				// Only for Developers
				Log.e(TAG, e.getMessage());
			}

			// Run next activity
			// From Activity to Next Activity
			Intent intent = new Intent(
							BeehiveAndroidSplashActivity.this, 
							BeehiveAndroidMainActivity.class);
			
			BeehiveAndroidSplashActivity.this.startActivity(intent);
			BeehiveAndroidSplashActivity.this.finish();
		}
	}
}
