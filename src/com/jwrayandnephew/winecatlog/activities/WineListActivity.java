package com.jwrayandnephew.winecatlog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.LinearLayout;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.fragments.WineDetailFragment;
import com.jwrayandnephew.winecatlog.fragments.WineListFragment;

/**
 * An activity representing a list of Wines. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link WineDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link WineListFragment} and the item details (if present) is a
 * {@link WineDetailFragment}.
 * <p>
 * This activity also implements the required {@link WineListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class WineListActivity extends FragmentActivity implements
		WineListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	public LinearLayout topLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_wine_list);

		topLevel = (LinearLayout) findViewById(R.id.topLevel);
		
		if (findViewById(R.id.wine_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((WineListFragment) getSupportFragmentManager().findFragmentById(
					R.id.wine_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link WineListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(int id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(WineDetailFragment.ARG_WINE_ID, id);
			WineDetailFragment fragment = new WineDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.wine_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, WineDetailActivity.class);
			detailIntent.putExtra(WineDetailFragment.ARG_WINE_ID, id);
			startActivity(detailIntent);
		}
	}
}
