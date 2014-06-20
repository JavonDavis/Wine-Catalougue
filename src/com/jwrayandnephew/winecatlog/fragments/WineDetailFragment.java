package com.jwrayandnephew.winecatlog.fragments;

import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.activities.WineDetailActivity;
import com.jwrayandnephew.winecatlog.activities.WineListActivity;
import com.jwrayandnephew.winecatlog.content.Wine;
import com.jwrayandnephew.winecatlog.database.DatabaseHandler;

/**
 * A fragment representing a single Wine detail screen. This fragment is either
 * contained in a {@link WineListActivity} in two-pane mode (on tablets) or a
 * {@link WineDetailActivity} on handsets.
 */
public class WineDetailFragment extends Fragment 
{
	//database object
	DatabaseHandler obj;
	Context context;
	ImageView image;
	WineListActivity main;
	
	/**
	 * The fragment argument representing the wine that this fragment
	 * represents.
	 */
	public static final String ARG_WINE_ID = "wine_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Wine aWine;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public WineDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		obj = new DatabaseHandler(getActivity());
		context = getActivity();
		
		if (getArguments().containsKey(ARG_WINE_ID)) {
			// Load the content specified by the fragment
			// arguments. In a more practical scenario, use a Loader
			// to load content from a content provider.
			aWine = obj.getWine(getArguments().getInt(
					ARG_WINE_ID));
		}
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		main = (WineListActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wine_detail,
				container, false);
		
		ImageView image = (ImageView) rootView.findViewById(R.id.image_view);
		
		LinearLayout topLevel = main.topLevel;
		// Show the wine content as text in TextViews.
		if (aWine != null) {
			
			switch(aWine.getCountry().trim())
			{
				case "France":
					topLevel.setBackgroundResource(R.drawable.france);
					break;
				case "New Zealand":
					topLevel.setBackgroundResource(R.drawable.new_zealand);
					break;
				case "Italy":
					topLevel.setBackgroundResource(R.drawable.italy);
					break;
				case "Spain":
					topLevel.setBackgroundResource(R.drawable.spain);
					break;
				case "California":
					topLevel.setBackgroundResource(R.drawable.california);
					break;
				case "South Africa":
					topLevel.setBackgroundResource(R.drawable.south_africa);
					break;
				default:
					topLevel.setBackgroundResource(R.drawable.road);
					break;
			}
			
		
			new DownloadImageTask(image)
            .execute("http://capeassistantbeta.eu5.org/id"+aWine.getId()+".png",aWine.getId()+"",aWine.getName());
			
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new AlertDialog.Builder(context)
				    .setTitle("Save image")
				    .setMessage("Do you want to save this image to your device ?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	download("http://capeassistantbeta.eu5.org/id"+aWine.getId()+".png",aWine.getName(),"Wine");
				        }
				     })
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
					
				}
			});
			
			Log.e("url", "http://capeassistantbeta.eu5.org/id"+aWine.getId());
			getActivity().setTitle(aWine.getName());
			
			if(aWine.getAlcohol_level()!=0)
			{
				((TextView) rootView.findViewById(R.id.alcohol_view))
				.setText("Alcohol Level:"+aWine.getAlcohol_level()+"%");
			}
			
			if(!aWine.getDescription().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.descriptionTitle))
				.setText("Description");
				((TextView) rootView.findViewById(R.id.descriptionContent))
				.setText(aWine.getDescription());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.description)).removeAllViews();
			
			if(!aWine.getServingSuggestion().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.servingSuggestionTitle))
				.setText("Serving Suggestion");
				((TextView) rootView.findViewById(R.id.servingSuggestionContent))
				.setText(aWine.getServingSuggestion());
				
			}
			else
				((LinearLayout) rootView.findViewById(R.id.servingSuggestion)).removeAllViews();
			
			if(!aWine.getTastingNotes().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.tastingNotesTitle))
				.setText("Tasting Notes");
				((TextView) rootView.findViewById(R.id.tastingNotesContent))
				.setText(aWine.getTastingNotes());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.tastingNotes)).removeAllViews();
			
			if(!aWine.getWineOfOrigin().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.wineOfOriginTitle))
				.setText("Origin");
				((TextView) rootView.findViewById(R.id.wineOfOriginContent))
				.setText(aWine.getWineOfOrigin());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.wineOfOrigin)).removeAllViews();
			
			if(!aWine.getCellaringPotential().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.cellaringPotentialTitle))
				.setText("Cellaring Potential");
				((TextView) rootView.findViewById(R.id.cellaringPotentialContent))
				.setText(aWine.getCellaringPotential());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.cellaringPotential)).removeAllViews();
			
			if(!aWine.getMaturation().isEmpty() )
			{
				((TextView) rootView.findViewById(R.id.maturationTitle))
				.setText("Maturation");
				((TextView) rootView.findViewById(R.id.maturationContent))
				.setText(aWine.getMaturation());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.maturation)).removeAllViews();
			
			if(!aWine.getFoodPairing().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.foodPairingTitle))
				.setText("Food Pairing");
				((TextView) rootView.findViewById(R.id.foodPairingContent))
				.setText(aWine.getFoodPairing());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.foodPairing)).removeAllViews();
			
			if(!aWine.getWinemakerNotes().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.winemakerNotesTitle))
				.setText("Food Pairing");
				((TextView) rootView.findViewById(R.id.winemakerNotesContent))
				.setText(aWine.getFoodPairing());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.winemakerNotes)).removeAllViews();
		}

		return rootView;
	}
	
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	            in.close();
	            //saveImage(context,mIcon11,"id"+urls[1]);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(final Bitmap result) {
	    	if(result!=null)
	    	{
	    		bmImage.setImageBitmap(result);
	    		
	    	}
	    }
	}
	
    public void download(String file,String title,String description)
    {
    	String url = file;
    	DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
    	request.setDescription(description);
    	request.setTitle(title);
    	// in order for this if to run, you must use the android 3.2 to compile your app
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    	    request.allowScanningByMediaScanner();
    	    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
    	}
    	request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title+".ext");

    	// get download service and enqueue file
    	DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    	manager.enqueue(request);
    }
}
