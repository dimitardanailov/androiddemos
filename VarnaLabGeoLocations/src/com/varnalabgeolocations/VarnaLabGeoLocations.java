package com.varnalabgeolocations;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class VarnaLabGeoLocations extends Activity {

	TextView textview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_varna_lab_geo_locations);
		
		// check if GPS enabled
		GPSTracker gpsTracker = new GPSTracker(this);
		
		if (gpsTracker.getIsGPSTrackingEnabled())
		{
			String stringLatitude = String.valueOf(gpsTracker.latitude);
			textview = (TextView)findViewById(R.id.fieldLatitude);
			textview.setText(stringLatitude);
			
			String stringLongitude = String.valueOf(gpsTracker.longitude);
			textview = (TextView)findViewById(R.id.fieldLongitude);
			textview.setText(stringLongitude);
			
			String country = gpsTracker.getCountryName(this);
			textview = (TextView)findViewById(R.id.fieldCountry);
			textview.setText(country);
			
			String city = gpsTracker.getLocality(this);
			textview = (TextView)findViewById(R.id.fieldCity);
			textview.setText(city);
			
			String postalCode = gpsTracker.getPostalCode(this);
			textview = (TextView)findViewById(R.id.fieldPostalCode);
			textview.setText(postalCode);
			
			String addressLine = gpsTracker.getAddressLine(this);
			textview = (TextView)findViewById(R.id.fieldAddressLine);
			textview.setText(addressLine);
		}
		else
		{
			// can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
			gpsTracker.showSettingsAlert();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.varna_lab_geo_locations, menu);
		return true;
	}

}
