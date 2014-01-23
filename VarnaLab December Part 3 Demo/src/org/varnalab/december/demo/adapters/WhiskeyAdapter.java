package org.varnalab.december.demo.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.varnalab.december.demo.interfaces.iWhiskeyListInterface;
import org.varnalab.december.demo.R;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Article: 
 * http://www.vogella.com/articles/AndroidListView/article.html#adapterown_example
 */

public class WhiskeyAdapter extends ArrayAdapter<HashMap<String, String>> 
	implements iWhiskeyListInterface {

	private static String TAG = WhiskeyAdapter.class.getName();
	
	private final Activity context;
	private final ArrayList<HashMap<String, String>> whiskeyList;
	
	static class ViewHolder {
		public RelativeLayout listWrapper;
		
		public TextView whiskeyName;
	}
	
	public WhiskeyAdapter(Activity context, 
			ArrayList<HashMap<String, String>> whiskeyList) {
		
		super(context, R.layout.whiskeylist, whiskeyList);
		this.context = context;
		this.whiskeyList = whiskeyList;
	}
	
	public int getCount() {
		return whiskeyList.size();
	}
	
	public HashMap<String, String> getItem(int position) {
		return whiskeyList.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.whiskeylist, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.listWrapper = (RelativeLayout) rowView.findViewById(R.id.listWrapper);
			viewHolder.whiskeyName = (TextView) rowView.findViewById(R.id.name);
			
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		HashMap<String, String> whiskey = whiskeyList.get(position);
		
		Log.d(TAG, whiskey.get(TAG_WHISKEYNAME));
		holder.whiskeyName.setText(whiskey.get(TAG_WHISKEYNAME));
		
		if (position == 0 && position % 2 ==0) {
			//holder.whiskeyName.
		} else {
			
		}
		
		return rowView;
	}
}


