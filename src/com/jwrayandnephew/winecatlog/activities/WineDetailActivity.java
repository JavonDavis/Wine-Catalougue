package com.jwrayandnephew.winecatlog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Window;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.fragments.WineDetailFragment;

/**
 * An activity representing a single Wine detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link WineListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link WineDetailFragment}.
 */
public class WineDetailActivity extends FragmentActivity {

	public static final String ARG_WINE_ID = "wine_id";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_wine_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
			WineDetailFragment fragment = new WineDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.wine_detail_container, fragment).commit();
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem view;
			
		view = menu.add("View Image")
				.setOnMenuItemClickListener(
			new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(WineDetailActivity.this,ImageActivity.class);
				intent.putExtra(ARG_WINE_ID,getIntent()
						.getIntExtra(WineDetailFragment.ARG_WINE_ID,99) );
				startActivity(intent);
				return false;
			}
		});
		view.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		view.setTitle("View Image");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, WineListActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
