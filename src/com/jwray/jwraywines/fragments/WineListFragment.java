package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.databases.WineManager;

/**
 * Fragment for displaying a list of wines based on the intent passed @see ParcelKeys.java for key options
 * @author Javon Davis
 *
 */
public class WineListFragment extends Fragment implements ParcelKeys
{
	private Context mContext;
	private WineManager obj;
	private String brand,country,name,type,meal,occasion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		obj = new WineManager(mContext);
		
		brand = getActivity().getIntent().getStringExtra(BRAND_IDENTIFIER);
		country = getActivity().getIntent().getStringExtra(COUNTRY_IDENTIFIER);
		name = getActivity().getIntent().getStringExtra(NAME_IDENTIFIER);
		type = getActivity().getIntent().getStringExtra(TYPE_IDENTIFIER);
		meal = getActivity().getIntent().getStringExtra(MEAL_IDENTIFIER);
		occasion = getActivity().getIntent().getStringExtra(OCCASION_IDENTIFIER);
	}

	
	public static WineListFragment newInstance()
	{
		return new WineListFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_list, container,false);
			
			ListView wineList = (ListView) rootView.findViewById(R.id.wine_list);
			
			ArrayList<Wine> wines = new ArrayList<Wine>();
			
			if(brand!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWinesByBrand(brand); 
			}
			else if(country!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWinesByCountry(country); 
			}
			else if(name!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWines(name);
			}
			else if(type!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWinesByType(type);
			}
			else if(meal!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWinesByMeal(meal);
			}
			else if(occasion!=null)
			{
				wines = (ArrayList<Wine>) obj.getAllWinesByOccasion(occasion);
			}
			else
			{
				wines = (ArrayList<Wine>) obj.getAllWines();
			}
			
			if(!wines.isEmpty())
			{
				final WineAdapter wineAdapter = new WineAdapter(mContext,
						android.R.layout.simple_list_item_2,
						wines);
				
				wineList.setAdapter(wineAdapter);
				
				wineList.setOnItemClickListener(new OnItemClickListener() {
	
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(mContext,WineInformationActivity.class);
						
						int ID = wineAdapter.getItem(position).getId();
						intent.putExtra(WINE_IDENTIFIER, ID);
						startActivity(intent);
					}
				});
			}
			else
			{
				new AlertDialog.Builder(mContext)
    			.setTitle("No Results")
    			.setMessage("Your search returned no results")
    			.setIcon(android.R.drawable.ic_dialog_alert)
    			.setPositiveButton(android.R.string.ok, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((Activity) mContext).finish();
						
					}
				})
    		     .show();
				
				
			}
		}
		catch(InflateException e)
		{
			Log.e("Wine List inflater", e.toString());
		}
		
		
		return rootView;
	}
	
	/*=================================== Adapter =================================*/
	/**
	 * Adapter for the wine list
	 * @author Javon Davis
	 *
	 */
	private class WineAdapter extends ArrayAdapter<Wine> {
		
		public WineAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		public WineAdapter(Context context, int resource, List<Wine> wines) {
		    super(context, resource, wines);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(getContext());
		        view = inflater.inflate(R.layout.wine_list_item, parent,false);

		    }
			
			Wine wine = getItem(position);
			
			if(wine!= null)
			{
				ImageView wineImage = (ImageView) view.findViewById(R.id.wineView);
				TextView wineName = (TextView) view.findViewById(R.id.wineName);
				TextView wineDescription = (TextView) view.findViewById(R.id.wineDescription);
				
				wineImage.setVisibility(View.GONE);
				//TODO delete the imageview 
				
				wineName.setText(wine.getName());
				wineDescription.setText("Country:"+wine.getCountry()+"\nBrand:"+wine.getBrand());
			}
			
			return view;
		}
	}

}

