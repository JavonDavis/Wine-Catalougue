package com.jwray.jwraywines.activities;

import java.util.Locale;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.activity_home);
		//getSupportActionBar().set
		
		tControl = new ThreadControl();
		obj = new WineManager(this);
			
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setIcon(android.R.color.transparent);
		
		if(obj.getAllWines().isEmpty())
			new WineContent().getWines(this, tControl);
		
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
						
					}
					
					@Override
					public void onPageScrollStateChanged(int state)
					{
					    if (state == ViewPager.SCROLL_STATE_IDLE)
					        if (mViewPager.getCurrentItem()!= 0)
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
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home, menu);
	    return true;

	}
	
	@Override
	public void onBackPressed() {
		// TODO Consiider making a dialog
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		return super.onOptionsItemSelected(item);
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
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}  
		
		
		public Drawable getPageIcon(int position)
		{
			Resources resources = getResources();
			switch(position)
			{
				case 0:
					return resources.getDrawable(android.R.drawable.ic_search_category_default);
				case 1:
					return resources.getDrawable(R.drawable.brands_icon);
				case 2:
					return resources.getDrawable(R.drawable.country_icon);
				//case 3:
					//return resources.getDrawable(R.drawable.wine_list_icon);
				default:
					return null;
			}
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a Fragment (defined as a static inner class
			// below).
			//TODO make error fragment
			Fragment f = null;
			switch(position)
			{
				case 0:
					f = HomeFragment.newInstance();
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
			//case 3:
				//return getString(R.string.title_list_section).toUpperCase(l);
			}
			return null;
		}
		
	}

}
