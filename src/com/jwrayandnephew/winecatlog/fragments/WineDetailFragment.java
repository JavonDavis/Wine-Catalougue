package com.jwrayandnephew.winecatlog.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	Object main;
	
	/**
	 * The fragment argument representing the wine that this fragment
	 * represents.
	 */
	public static final String ARG_WINE_ID = "wine_id";
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Wine aWine;
	
	public static Fragment newInstance(int sectionNumber,int id) {
		if(sectionNumber ==2)
			return ImageFragment.newInstance(sectionNumber,id);
		WineDetailFragment fragment = new WineDetailFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putInt(WineDetailFragment.ARG_WINE_ID, id);
		fragment.setArguments(args);
		return fragment;
	}

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
		try
		{
			main = (WineListActivity) activity;
		}
		catch(java.lang.ClassCastException e)
		{
			main = (WineDetailActivity) activity;
		}
		catch(Exception e)
		{
			Toast.makeText(getActivity(), "Error 3", Toast.LENGTH_LONG).show();
			
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wine_detail,
				container, false);
		
		
		LinearLayout topLevel;
		try
		{
			topLevel = ((WineListActivity) main).topLevel;
		}
		catch(java.lang.ClassCastException e)
		{
			topLevel = (LinearLayout) getActivity().findViewById(R.id.detail);
		}
		// Show the wine content as text in TextViews.
		int rotation = getActivity().getWindow().getWindowManager().getDefaultDisplay().getRotation();
		
		if (aWine != null) {
			
			switch(aWine.getCountry().trim())
			{
				case "France":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.france_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
		            	topLevel.setBackgroundResource(R.drawable.france_horizontal);
		            }
					
					break;
				case "New Zealand":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.new_zealand_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
		            	topLevel.setBackgroundResource(R.drawable.new_zealand_horizontal);
		            }
					break;
				case "Italy":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.italy_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
						topLevel.setBackgroundResource(R.drawable.italy_horizontal);
		            }
					break;
				case "Spain":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.spain_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
		            	topLevel.setBackgroundResource(R.drawable.spain_horizontal);
		            }					
					break;
				case "California":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.cali_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
		            	topLevel.setBackgroundResource(R.drawable.cali_horizontal);
		            }						
					break;
				case "South Africa":
					if (rotation==Surface.ROTATION_0||rotation==Surface.ROTATION_180) {
						topLevel.setBackgroundResource(R.drawable.south_africa_vertical);
		            } else if (rotation==Surface.ROTATION_90||rotation==Surface.ROTATION_270) {
		            	topLevel.setBackgroundResource(R.drawable.south_africa_horizontal);
		            }						
					break;
				default:
					topLevel.setBackgroundResource(R.drawable.background);
					break;
			}
			
			getActivity().setTitle(aWine.getName());
			
			//======================== Data Checks =============================== 
			
			//check for alcohol level
			if(aWine.getAlcohol_level()!=0)
			{
				((TextView) rootView.findViewById(R.id.alcohol_view))
				.setText("Alcohol Level:"+aWine.getAlcohol_level()+"%");
			}
			
			//check for description
			if(!aWine.getDescription().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.descriptionTitle))
				.setText("Description");
				((TextView) rootView.findViewById(R.id.descriptionContent))
				.setText(aWine.getDescription());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.description)).removeAllViews();
			
			//check for serving suggestion
			if(!aWine.getServingSuggestion().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.servingSuggestionTitle))
				.setText("Serving Suggestion");
				((TextView) rootView.findViewById(R.id.servingSuggestionContent))
				.setText(aWine.getServingSuggestion());
				
			}
			else
				((LinearLayout) rootView.findViewById(R.id.servingSuggestion)).removeAllViews();
			
			//check for tasting notes
			if(!aWine.getTastingNotes().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.tastingNotesTitle))
				.setText("Tasting Notes");
				((TextView) rootView.findViewById(R.id.tastingNotesContent))
				.setText(aWine.getTastingNotes());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.tastingNotes)).removeAllViews();
			
			
			//check for wine of origin
			if(!aWine.getWineOfOrigin().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.wineOfOriginTitle))
				.setText("Origin");
				((TextView) rootView.findViewById(R.id.wineOfOriginContent))
				.setText(aWine.getWineOfOrigin());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.wineOfOrigin)).removeAllViews();
			
			
			//check for cellaring potential
			if(!aWine.getCellaringPotential().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.cellaringPotentialTitle))
				.setText("Cellaring Potential");
				((TextView) rootView.findViewById(R.id.cellaringPotentialContent))
				.setText(aWine.getCellaringPotential());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.cellaringPotential)).removeAllViews();
			
			//check for maturation
			if(!aWine.getMaturation().isEmpty() )
			{
				((TextView) rootView.findViewById(R.id.maturationTitle))
				.setText("Maturation");
				((TextView) rootView.findViewById(R.id.maturationContent))
				.setText(aWine.getMaturation());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.maturation)).removeAllViews();
			
			//check for food pairing
			if(!aWine.getFoodPairing().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.foodPairingTitle))
				.setText("Food Pairing");
				((TextView) rootView.findViewById(R.id.foodPairingContent))
				.setText(aWine.getFoodPairing());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.foodPairing)).removeAllViews();
			
			//check for winemaker's notes 
			if(!aWine.getWinemakerNotes().isEmpty())
			{
				((TextView) rootView.findViewById(R.id.winemakerNotesTitle))
				.setText("Winemaker's notes");
				((TextView) rootView.findViewById(R.id.winemakerNotesContent))
				.setText(aWine.getWinemakerNotes());
			}
			else
				((LinearLayout) rootView.findViewById(R.id.winemakerNotes)).removeAllViews();
		}

		return rootView;
	}
}
