package com.jwray.jwraywines.fragments;

import java.util.ArrayList;

import android.content.Context;
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
import android.widget.ListView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.classes.DatabaseHandler;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.WineAdapter;

public class WineListFragment extends Fragment
{
	private Context mContext;
	private DatabaseHandler obj;
	private String brand,country,name;
	private static String BRAND_IDENTIFIER = "brand";
	private static String COUNTRY_IDENTIFIER = "country";
	private static String NAME_IDENTIFIER = "name";
	private static String WINE_IDENTIFIER = "id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		obj = new DatabaseHandler(mContext);
		
		brand = getActivity().getIntent().getStringExtra(BRAND_IDENTIFIER);
		country = getActivity().getIntent().getStringExtra(COUNTRY_IDENTIFIER);
		name = getActivity().getIntent().getStringExtra(NAME_IDENTIFIER);
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
			else
			{
				wines = (ArrayList<Wine>) obj.getAllWines();
			}
			
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
		catch(InflateException e)
		{
			Log.e("Wine List inflater", e.toString());
		}
		
		
		return rootView;
	}
}

