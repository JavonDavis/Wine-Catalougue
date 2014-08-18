package com.jwray.jwraywines.activities;

import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.ThreadControl;
import com.jwray.jwraywines.classes.WineContent;
import com.jwray.jwraywines.classes.databases.WineManager;
import com.jwray.jwraywines.fragments.BrandFragment;
import com.jwray.jwraywines.fragments.CountryFragment;
import com.jwray.jwraywines.fragments.HomeFragment;
import com.jwray.jwraywines.fragments.SearchFragment;

/**
 * First activity displayed for interaction with navigation tabs
 * @author Javon Davis
 *
 */
public class HomeActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	
	private WineManager obj;
	private ThreadControl tControl;
	private static Menu optionsMenu;
	private boolean homeFragmentIsVisible = true;
	private HomeFragment home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.activity_home);
		
		tControl = new ThreadControl();
		obj = new WineManager(this);
			
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setIcon(android.R.color.transparent);
		
		if(obj.getAllWines().isEmpty())
			refresh(null);
		
		// Create the adapter that will return a fragment for each of the four
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		//setTitle(mSectionsPagerAdapter.getPageTitle(mViewPager.getCurrentItem()));
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
						//setTitle(mSectionsPagerAdapter.getPageTitle(mViewPager.getCurrentItem()));
						if(position==0)
							setHomeFragmentIsVisible(true);
						else 
							setHomeFragmentIsVisible(false);
					}
					
					@Override
					public void onPageScrollStateChanged(int state)
					{
					    if (state == ViewPager.SCROLL_STATE_IDLE)
					            // Hide the keyboard.
					            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
					                .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
					}
				});
		

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setTabListener(this)
					.setText(mSectionsPagerAdapter.getPageTitle(i)));
					//.setIcon(mSectionsPagerAdapter.getPageIcon(i)));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		optionsMenu = menu;
		return super.onCreateOptionsMenu(menu);

	}
	
	/**
	 * Overridden and used to control the options on the home page
	 */
	@Override
	public void onBackPressed() {
		int identifier = home.getPreviousIdentifier();
		if(isHomeFragmentIsVisible()&&identifier!=0)
			home.refreshList(identifier);
		else
			super.onBackPressed();
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/**
	 * Reloads the enter database of wines
	 * @param item
	 */
	public void refresh(MenuItem item)
	{
		if(isNetworkAvailable())
			new WineContent().getWines(this, tControl);
		else
			Toast.makeText(this, "You need an internet connection to retrieve the information on the wines", Toast.LENGTH_LONG).show();
	}
	
	public static void setRefreshActionButtonState(final boolean refreshing) {
	    if (optionsMenu != null) {
	        final MenuItem refreshItem = optionsMenu
	            .findItem(R.id.wine_refresh);
	        if (refreshItem != null) {
	            if (refreshing) {
	                refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
	            } else {
	                refreshItem.setActionView(null);
	            }
	        }
	    }
	}
	
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	


	/**
	 * @return the homeFragmentIsVisible
	 */
	public boolean isHomeFragmentIsVisible() {
		return homeFragmentIsVisible;
	}

	/**
	 * @param homeFragmentIsVisible the homeFragmentIsVisible to set
	 */
	public void setHomeFragmentIsVisible(boolean homeFragmentIsVisible) {
		this.homeFragmentIsVisible = homeFragmentIsVisible;
	}



	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}  
		
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a Fragment (defined as a static inner class
			// below).
			Fragment f = null;
			switch(position)
			{
				case 0:
					f = HomeFragment.newInstance();
					home = (HomeFragment) f;
					break;
				case 1:
					f = SearchFragment.newInstance();
					break;
				case 2:
					f = BrandFragment.newInstance();
					break;
				case 3:
					f = CountryFragment.newInstance();
					break;
				default:
					f = null;
			}
			return f;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_home_section).toUpperCase(l);
			case 1:
				return getString(R.string.title_search_section).toUpperCase(l);
			case 2:
				return getString(R.string.title_brand_section).toUpperCase(l);
			case 3:
				return getString(R.string.title_country_section).toUpperCase(l);
			}
			return null;
		}
		
	}

}
