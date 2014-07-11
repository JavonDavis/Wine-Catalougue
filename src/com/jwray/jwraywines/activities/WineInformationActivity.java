package com.jwray.jwraywines.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.DatabaseHandler;
import com.jwray.jwraywines.classes.FavoriteManager;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.fragments.NotesFragment;
import com.jwray.jwraywines.fragments.WineDetailFragment;
import com.jwray.jwraywines.fragments.WineDrawerFragment;
import com.jwray.jwraywines.fragments.WinePhotoFragment;

/**
 * 
 * @author Javon Davis
 *
 */
public class WineInformationActivity extends ActionBarActivity implements
		WineDrawerFragment.NavigationDrawerCallbacks {
	

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private WineDrawerFragment mWineDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	private static String WINE_IDENTIFIER = "id";
	private DatabaseHandler obj;
	private Wine wine;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_information);

		mWineDrawerFragment = (WineDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		
		obj = new DatabaseHandler(this);
		
		wine = obj.getWine(getIntent().getIntExtra(WINE_IDENTIFIER,-1));
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE );
		
		mTitle = wine.getName();

		// Set up the drawer.
		mWineDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment=null;
		switch(position)
		{
			case 0:
				fragment = WineDetailFragment.newInstance();
				break;
			case 1:
				fragment = WinePhotoFragment.newInstance();
				break;
			case 2:
				fragment = NotesFragment.newInstance();
				break;
		}
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						fragment).commit();
	}

	/*
	public void onSectionAttached(int number) {
		
		switch (number) {
		case 1:
			mTitle = getString(R.string.wine_title1);
			break;
		case 2:
			mTitle = getString(R.string.wine_title2);
			break;
		case 3:
			mTitle = getString(R.string.wine_title3);
			break;
		}
	}*/

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if(menu.findItem(R.id.fav)!=null && menu.findItem(R.id.unfav)!=null)
			try
			{
				
				Log.d("deh", "ya");
					menu.findItem(R.id.fav).setVisible(!wine.isFavorite());
					menu.findItem(R.id.unfav).setVisible(wine.isFavorite());
		
			}
			catch(java.lang.NullPointerException e)
			{
				Log.e("error", e+""+wine);
			}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mWineDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.wine_information, menu);
			
			final MenuItem fav = menu.findItem(R.id.fav);
			final MenuItem unfav = menu.findItem(R.id.unfav);
			
			OnMenuItemClickListener iconListen = new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					switch(item.getItemId())
					{
						case R.id.fav:
							wine.setFavorite(true);
							fav.setVisible(false);
							unfav.setVisible(true);
							Toast.makeText(WineInformationActivity.this, "Added to favorites", Toast.LENGTH_LONG).show();
							FavoriteManager.addToFavorites(wine.getId(), WineInformationActivity.this);
							break;
						case R.id.unfav:
							wine.setFavorite(false);
							fav.setVisible(true);
							unfav.setVisible(false);
							Toast.makeText(WineInformationActivity.this, "Removed from favorites", Toast.LENGTH_LONG).show();
							FavoriteManager.removeFavorite(WineInformationActivity.this, wine.getId());
							break;
					}
					return false;
				}
			};
			
			fav.setOnMenuItemClickListener(iconListen);
			unfav.setOnMenuItemClickListener(iconListen);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		/*
		if(id==R.id.fav)
		{
			Log.d("woot","woot2");
			wine.setFavorite(true);
		}
		else if(id==R.id.unfav)
		{
			Log.d("woot","woot3");
			wine.setFavorite(false);
		}
		item.setVisible(!wine.isFavorite());
		item.setVisible(wine.isFavorite());
		*/
		return super.onOptionsItemSelected(item);
	}

}
