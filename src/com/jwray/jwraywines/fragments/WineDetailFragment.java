package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Wine;
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
				
				view = ((TextView) rootView.findViewById(R.id.name));
				view.setText(aWine.getName());
				
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
				
				if(!aWine.getPronounciation().isEmpty())
				{
					view = ((TextView) rootView.findViewById(R.id.pronounciation));
					view.setText(aWine.getPronounciation());
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

	
	/*=================================Adapeter=================================*/
	/**
	 * Adapter for items displayed when viewing wine details
	 * @author Javon Davis
	 *
	 */
	public class WineItemAdapter extends BaseExpandableListAdapter {

		private Context mContext;
		private List<String> _listDataHeader; // header titles
		
	    // child data in format of header title, child title
	    private HashMap<String, List<String>> _listDataChild;
		
		public WineItemAdapter(Context context, List<String> listDataHeader,
	            HashMap<String, List<String>> listChildData) {
			mContext = context;
			_listDataHeader=listDataHeader;
			_listDataChild=listChildData;
		}
		
		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.wine_item_header, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.header);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final String childText = (String) getChild(groupPosition, childPosition);
			 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.wine_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.content);
	 
	        txtListChild.setText(childText);
	        return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}

}
