package beehiveandroid.bg;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;
import android.view.View.OnClickListener;

public class BeehiveAndroidMainActivity extends ActionBarActivity implements OnClickListener {

	// Initialize Layout
	Button buttonOne;
	Button buttonTwo;
	Button buttonFive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beehive_android_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
			
			initializeLayout();
		}
	}
	
	// Initialize All Layout Elements
	private void initializeLayout() {
		buttonOne = (Button) findViewById(R.id.button_one);
		buttonTwo = (Button) findViewById(R.id.button_two);
		buttonFive = (Button) findViewById(R.id.button_five);
		
		initializeClickListeners();
	}
	
	private void initializeClickListeners() {
		buttonOne.setOnClickListener(this);
		buttonTwo.setOnClickListener(this);
		buttonFive.setOnClickListener(this);
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
					R.layout.fragment_beehive_android_main, container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View view) {

		int id = view.getId();
		
		switch (id) {
			case R.id.button_one:
				// Load First Activity
				break;
			case R.id.button_two:
				// Load Second Activity
				break;
			case R.id.button_five:
				break;
			default:
				break;
		}
		
		
	}

}
