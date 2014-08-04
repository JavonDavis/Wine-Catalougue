package com.jwray.jwraywines.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.databases.FavoriteManager;
import com.jwray.jwraywines.classes.databases.NotesManager;
import com.jwray.jwraywines.classes.databases.WineManager;
import com.jwray.jwraywines.fragments.NoteDialogFragment;
import com.jwray.jwraywines.fragments.NotesFragment;
import com.jwray.jwraywines.fragments.OptionsDialogFragment;
import com.jwray.jwraywines.fragments.WineDetailFragment;
import com.jwray.jwraywines.fragments.WineDrawerFragment;
import com.jwray.jwraywines.fragments.WinePhotoFragment;

/**
 * Activity for the entire wine page and fragments used
 * @author Javon Davis
 *
 */
public class WineInformationActivity extends ActionBarActivity implements
		WineDrawerFragment.NavigationDrawerCallbacks,ParcelKeys,ParcelKeys.NoteDialogInterface {
	

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
	private WineManager obj;
	private Wine wine;
	private FavoriteManager favObj;
	private int wineId;
	private NotesManager notesObj;
	
	//life cycle helper for facebook share dialog
	private UiLifecycleHelper uiHelper;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_information);

		mWineDrawerFragment = (WineDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		
		obj = new WineManager(this);
		favObj = new FavoriteManager(this);
		notesObj = new NotesManager(this);
		
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
		
		wine = obj.getWine(getIntent().getIntExtra(WINE_IDENTIFIER,-1));
		wineId = wine.getId();
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE );
		getSupportActionBar().setIcon(android.R.color.transparent);
		
		mTitle = wine.getName();
		setTitle(mTitle);

		// Set up the drawer.
		mWineDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
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

	private void restoreActionBar() {
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
			//final MenuItem share = menu.findItem(R.id.share);
			
			OnMenuItemClickListener iconListen = new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					switch(item.getItemId())
					{
						case R.id.fav:
							wine.setFavorite(true);
							fav.setVisible(false);
							unfav.setVisible(true);
							try
							{
								favObj.insert(wine.getId());
								Log.e("size", ""+favObj.getAllFavorites().size());
								Toast.makeText(WineInformationActivity.this, "Wine added to favorites", Toast.LENGTH_LONG).show();
							}
							catch(Exception e)
							{
								Log.e("error", e.toString());
								Toast.makeText(WineInformationActivity.this, "Problem adding to favorites", Toast.LENGTH_LONG).show();
							}
							break;
						case R.id.unfav:
							wine.setFavorite(false);
							fav.setVisible(true);
							unfav.setVisible(false);
							Toast.makeText(WineInformationActivity.this, "Wine removed from favorites", Toast.LENGTH_LONG).show();
							favObj.deleteFavorite(wine.getId());
							break;
							/*
						case R.id.share:
							if (FacebookDialog.canPresentShareDialog(getApplicationContext(), 
                                    FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
									// Publish the post using the Share Dialog
									FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(WineInformationActivity.this)
									       .setName(wine.getName())
									       .setApplicationName(WineInformationActivity.this.getResources().getString(R.string.app_name))
									       .setDescription("Hey I'm using [app name] and I just checked out the "+wine.getName())
									       .build();
									uiHelper.trackPendingDialogCall(shareDialog.present());
								
								} else {
									// publish the post using the Feed Dialog
									//publishFeedDialog();
								}
							break;*/
					}
					return false;
				}
			};
			
			fav.setOnMenuItemClickListener(iconListen);
			unfav.setOnMenuItemClickListener(iconListen);
			//share.setOnMenuItemClickListener(iconListen);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@SuppressWarnings("unused")
	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", "Facebook SDK for Android");
	    params.putString("caption", "Build great social ap"
	    		+ "ps and get more installs.");
	    params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	    params.putString("link", "https://developers.facebook.com/android");
	    params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(this,
	            Session.getActiveSession(),
	            params))
	        .setOnCompleteListener(new OnCompleteListener() {

	           
	        	@Override
				public void onComplete(Bundle values, FacebookException error) {
	                if (error == null) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(WineInformationActivity.this,
	                            "Posted story, id: "+postId,
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // User clicked the Cancel button
	                        Toast.makeText(WineInformationActivity.this.getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                    Toast.makeText(WineInformationActivity.this.getApplicationContext(), 
	                        "Publish cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                } else {
	                    // Generic, ex: network error
	                    Toast.makeText(WineInformationActivity.this.getApplicationContext(), 
	                        "Error posting story", 
	                        Toast.LENGTH_SHORT).show();
	                }
	            }

	        })
	        .build();
	    feedDialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		if(id==R.id.add_note)
		{
			NoteDialogFragment.setContext(this);
			DialogFragment dialog = new NoteDialogFragment();
			Bundle args = new Bundle();
			args.putInt("id", wineId);
			dialog.setArguments(args);
			dialog.show(getSupportFragmentManager(), "NoteDialogFragment");
		}
		else if(id==R.id.pronounce)
		{
			MediaPlayer pronunciation = wine.getVoicePronounciation(this);
			if(pronunciation!=null)
				pronunciation.start();
			else
				Toast.makeText(this, "No Pronunciation available for this wine", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onNoteSelected(Note mNote, String key) {
		ArrayList<Note> notes = (ArrayList<Note>) notesObj.getNotesByWineId(wineId);
		
		final NotesFragment.NoteAdapter noteAdapter = new NotesFragment.NoteAdapter(this,
				android.R.layout.simple_list_item_2,
				notes);
		
		ListView noteView = NotesFragment.getNotesList();
		if(noteView!=null)   
		{
			noteView.setAdapter(noteAdapter);
			
			noteView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Note note = noteAdapter.getItem(position);
					
					OptionsDialogFragment.setContext(WineInformationActivity.this);
					OptionsDialogFragment.setNote(note);
					
					DialogFragment dialog = new OptionsDialogFragment();
			        dialog.show(WineInformationActivity.this.getSupportFragmentManager(), "OptionsDialog");
				}
			});
		}
	}


}
