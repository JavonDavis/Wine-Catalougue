package com.jwrayandnephew.winecatlog.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.fragments.ImageFragment;
import com.jwrayandnephew.winecatlog.fragments.WineDetailFragment;

public class ImageActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_image);

		
		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(WineDetailFragment.ARG_WINE_ID, getIntent()
					.getIntExtra(WineDetailFragment.ARG_WINE_ID,99));
			ImageFragment fragment = new ImageFragment();
			
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.image_container, fragment).commit();
			
		}
	}

}
