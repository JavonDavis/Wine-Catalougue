package com.jwrayandnephew.winecatlog.activities;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

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
	private static boolean showDemo =true;
	MenuItem view;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_wine_list);

		topLevel = (LinearLayout) findViewById(R.id.topLevel);
		
		if (findViewById(R.id.pager) != null) {
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(mTwoPane)
		{
			view = menu.add("View Image")
					.setOnMenuItemClickListener(
							new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					//Toast.makeText(getApplicationContext(), "Button to be removed and replaced by giving the users the ability to swipe", Toast.LENGTH_LONG).show();
					try
					{
						int nextItem=mViewPager.getCurrentItem()+1;
						
						if(nextItem==2)
							nextItem=mViewPager.getCurrentItem()-1;
						
						mViewPager.setCurrentItem(nextItem);
					}
					catch(java.lang.NullPointerException e)
					{
						
					}
					return false;
				}
			});
			view.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			view.setTitle("");
		}
		return true;
	}

	/**
	 * Callback method from {@link WineListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(int id) {
		if (mTwoPane) {
			view.setTitle("Show Image");
			mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),id);

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int state) {
					
				}
				
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					if(position==0)
						view.setTitle("View Image");
					else
						view.setTitle("View Details");
					
				}
				
				@Override
				public void onPageScrollStateChanged(int position) {
				}
			});
			if(showDemo)
			{
				Toast.makeText(getApplicationContext(), "Swipe to see picture",
                        Toast.LENGTH_LONG).show();
				try {
					Interpolator sInterpolator = new DecelerateInterpolator();
				    Field mScroller;
				    mScroller = ViewPager.class.getDeclaredField("mScroller");
				    mScroller.setAccessible(true); 
				    FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), sInterpolator,1000);
				    //scroller.setFixedDuration(5000);
				    mScroller.set(mViewPager, scroller);
				    
				    pageSwitcher(2);
					showDemo = false;
					
					
				} catch (NoSuchFieldException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
							
			}

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, WineDetailActivity.class);
			detailIntent.putExtra(WineDetailFragment.ARG_WINE_ID, id);
			startActivity(detailIntent);
		}
	}
	
	Timer timer;
	int page = 1;

	public void pageSwitcher(int seconds) {
	    timer = new Timer(); // At this line a new Thread will be created
	    timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
	                                                                    // in
	    // milliseconds
	}

	    // this is an inner class...
	class RemindTask extends TimerTask {

	    @Override
	    public void run() {

	        // As the TimerTask run on a seprate thread from UI thread we have
	        // to call runOnUiThread to do work on UI thread.
	        runOnUiThread(new Runnable() {
	            public void run() {

	                if (page > 1) { 
	                    timer.cancel();
	                } else {
	                    mViewPager.setCurrentItem(page++);
	                    
	                }
	                	
	            }
	        });

	    }
	}

	public class FixedSpeedScroller extends Scroller {

	    private int mDuration = 0;

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }


		public FixedSpeedScroller(Context context, Interpolator interpolator,int i) {
	        super(context, interpolator);
	        this.mDuration=i;
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
	        super(context, interpolator, flywheel);
	    }


	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        // Ignore received duration, use fixed one instead
	        super.startScroll(startX, startY, dx, dy, mDuration);
	    }
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		int itemID;
		public SectionsPagerAdapter(FragmentManager fm,int id) {
			super(fm);
			this.itemID=id;
		}
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a Fragment (defined as a static inner class
			// below).
			return WineDetailFragment.newInstance(position + 1,this.itemID);
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}
		
		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
		
	}
}
