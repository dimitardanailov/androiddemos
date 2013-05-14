package modez.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PhotoPreview extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_preview);
		
		Button closeButton = (Button)findViewById(R.id.previewdeleteButton);
		closeButton.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(PhotoPreview.this, Modez.class);	
				PhotoPreview.this.startActivity(intent);
			}
		});
		
		Button addTag = (Button)findViewById(R.id.previewAddTag);
		addTag.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(PhotoPreview.this, AddTags.class);	
				PhotoPreview.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modez, menu);
		return true;
	}
}
