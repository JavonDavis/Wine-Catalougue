package com.jwray.jwraywines.fragments;

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
import android.widget.GridView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.adapters.GridImageAdapter;

public class BrandFragment extends Fragment
{
	private Context mContext;
	private static String BRAND_IDENTIFIER = "brand";
	
	public static final Integer[] mBrandIds = {
        R.drawable.lamargue,
        R.drawable.cathedral_cellar,
        R.drawable.naked_grape,
        R.drawable.teruzzi_puthod,
        R.drawable.sella_mosca,
        R.drawable.cecchi,
        R.drawable.cinzano,
        R.drawable.cloudy_bay,
        R.drawable.torres,
        R.drawable.masi,
        R.drawable.mirassou,
        R.drawable.golden_kaan
	};

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	
	public static BrandFragment newInstance()
	{
		return new BrandFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = null;
		try
		{
			rootView = inflater.inflate(R.layout.fragment_brands, container,false);
			
		}
		catch(InflateException e)
		{
			Log.e("brand inflater", e.toString());
		}
		
		if(rootView!=null)
		{
			
			final GridImageAdapter gridImageAdapter = new GridImageAdapter(mContext,mBrandIds);
			GridView brandGrid = (GridView) rootView.findViewById(R.id.brandView);
			brandGrid.setAdapter(gridImageAdapter);
			
			
			brandGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

							String imageBrand = mContext.getResources()
									.getResourceEntryName((int) gridImageAdapter.getItem(position));
							switch(imageBrand)
							{
								case "cathedral_cellar":
									imageBrand = "Cathedral Cellar";
									break;
								case "golden_kaan":
									imageBrand = "Golden Kaan";
									break;
								case "california":
									imageBrand = "California";
									break;
								case "masi":
									imageBrand = "Masi";
									break;
								case "cloudy_bay":
									imageBrand = "Cloudy Bay";
									break;
								case "cecchi":
									imageBrand = "Cecchi";
									break;
								case "lamargue":
									imageBrand = "Lamargue";
									break;
								case "mirassou":
									imageBrand = "Mirassou";
									break;
								case "torres":
									imageBrand = "Torres";
									break;
								case "naked_grape":
									imageBrand = "Naked Grape";
									break;
								case "cinzano":
									imageBrand = "Cinzano";
									break;
								case "sella_mosca":
									imageBrand = "Sella&Mosca";
									break;
								case "teruzzi_puthod":
									imageBrand = "Teruzzi & Puthod";
									break;
							}
							Intent intent = new Intent(mContext,WineListActivity.class);
							intent.putExtra(BRAND_IDENTIFIER, imageBrand);
							mContext.startActivity(intent);
					
				}
			});
			
		}
		
		return rootView;
	}
}

