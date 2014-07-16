package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.adapters.WineItemAdapter;
import com.jwray.jwraywines.classes.databases.WineManager;

/**
 * Fragment for the detail page of wine 'x'
 * @author Javon
 *
 */
public class WineDetailFragment extends Fragment {

	private Context mContext;
	private static String WINE_IDENTIFIER = "id";
	private WineManager obj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		
		obj = new WineManager(mContext);

	}
	
	public static Fragment newInstance() {
		
		return new WineDetailFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_detail, container,false);
			
			int id = getActivity().getIntent().getIntExtra(WINE_IDENTIFIER,-1);
			
			Wine aWine = null;
			TextView view=null;
			ExpandableListView expView = null;
			
			List<String> headers = new ArrayList<String>();
		    HashMap<String, List<String>> contentMap = new HashMap<String , List<String>>();;
		    expView = (ExpandableListView) rootView.findViewById(R.id.information);
		    
			if(id>0)
			{
				aWine = obj.getWine(id);
				
				//check for alcohol level
				if(aWine.getAlcohol_level()!=0)
				{
					view = ((TextView) rootView.findViewById(R.id.alcohol));
					view.setText("Alcohol Level:"+aWine.getAlcohol_level()+"%");
					view.setVisibility(View.VISIBLE);
				}
				
				if(!aWine.getBrand().isEmpty())
				{
					view = ((TextView) rootView.findViewById(R.id.brand));
					view.setText("Brand:"+aWine.getBrand());
					view.setVisibility(View.VISIBLE);
				}
				
				if(!aWine.getCountry().isEmpty())
				{
					view = ((TextView) rootView.findViewById(R.id.country));
					view.setText("Country:"+aWine.getCountry());
					view.setVisibility(View.VISIBLE);
				}
				
				//check for description
				if(!aWine.getDescription().isEmpty())
				{
					headers.add("Description");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getDescription());
					
					contentMap.put("Description", content);

				}
				
				//check for serving suggestion
				if(!aWine.getServingSuggestion().isEmpty())
				{
					headers.add("Serving Suggestion");

					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getServingSuggestion());
					
					contentMap.put("Serving Suggestion", content);
					
				}
				
				//check for tasting notes
				if(!aWine.getTastingNotes().isEmpty())
				{
					headers.add("Tasting Notes");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getTastingNotes());
					
					contentMap.put("Tasting Notes", content);
					
				}
				
				
				//check for wine of origin
				if(!aWine.getWineOfOrigin().isEmpty())
				{
					headers.add("Origin");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getWineOfOrigin());
					
					contentMap.put("Origin", content);
				}
				
				//check for cellaring potential
				if(!aWine.getCellaringPotential().isEmpty())
				{
					headers.add("Cellaring Potential");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getCellaringPotential());
					
					contentMap.put("Cellaring Potential", content);
				}
				
				//check for maturation
				if(!aWine.getMaturation().isEmpty() )
				{
					headers.add("Maturation");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getMaturation());
					
					contentMap.put("Maturation", content);
				}

				//check for food pairing
				if(!aWine.getFoodPairing().isEmpty())
				{
					headers.add("Food Pairing");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getFoodPairing());
					
					contentMap.put("Food Pairing", content);
				}

				//check for winemaker's notes 
				if(!aWine.getWinemakerNotes().isEmpty())
				{
					headers.add("Winemaker's Notes");
					
					ArrayList<String> content = new ArrayList<String>();
					content.add(aWine.getWinemakerNotes());
					
					contentMap.put("Winemaker's Notes", content);
				}
				
				WineItemAdapter adapter = new WineItemAdapter(mContext,headers,contentMap);
				
				expView.setAdapter(adapter);
			}
		}
		catch(InflateException e)
		{
			Log.e("Wine Detail inflater", e.toString());
		}
		
		return rootView;
	}

}