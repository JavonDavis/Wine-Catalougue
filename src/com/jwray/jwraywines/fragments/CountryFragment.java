package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;

public class CountryFragment extends Fragment
{
	private Context mContext;
	private static String COUNTRY_IDENTIFIER = "country";
	
	@SuppressWarnings("unused")
	private static final Integer[] mCountryIds = 
		{
			R.drawable.italy,
			R.drawable.france,
			R.drawable.spain,
			R.drawable.new_zealand,
			R.drawable.south_africa,
			R.drawable.california
		};
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	
	public static CountryFragment newInstance()
	{
		return new CountryFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		
		List<ImageView> countryImages = new ArrayList<ImageView>();
		
		try{
			rootView = inflater.inflate(R.layout.fragment_country, container,false);
			ImageView spainImage = (ImageView) rootView.findViewById(R.id.spainView);
			ImageView italyImage = (ImageView) rootView.findViewById(R.id.italyView);
			ImageView franceImage = (ImageView) rootView.findViewById(R.id.franceView);
			ImageView africaImage = (ImageView) rootView.findViewById(R.id.africaView);
			ImageView californiaImage = (ImageView) rootView.findViewById(R.id.californiaView);
			ImageView zealandImage = (ImageView) rootView.findViewById(R.id.zealandView);
			
			countryImages.add(spainImage);
			countryImages.add(italyImage);
			countryImages.add(franceImage);
			countryImages.add(africaImage);
			countryImages.add(californiaImage);
			countryImages.add(zealandImage);
			
			for(ImageView view : countryImages)
				view.setOnClickListener(new ImageListener());
			
		}
		catch(InflateException e)
		{
			Log.e("Country inflater", e.toString());
		}
		catch(java.lang.NullPointerException e)
		{
			Log.e("NullPointerException in imageviews", e.toString());
		}
		
		return rootView;
	}
	
	private void ImageDialog(final String country, String prompt)
	{
		new AlertDialog.Builder(mContext)
	    .setTitle("View wine by country")
	    .setMessage(prompt)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            Intent intent = new Intent(mContext,WineListActivity.class);
	            
	            intent.putExtra(COUNTRY_IDENTIFIER, country);
	            
	            startActivity(intent);
	        }
	     })
	    .setNegativeButton(android.R.string.no, null)
	    .setIcon(android.R.drawable.ic_dialog_info)
	     .show();
	}
	
	//===================================Listener======================================//
	private class ImageListener implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			String country = v.getContentDescription().toString();
			String prompt =null;
			switch(country)
			{
				case "spain":
					country = "Spain";
					prompt = mContext.getString(R.string.spain_prompt);
					ImageDialog(country, prompt);
					break;
				case "italy":
					country="Italy";
					prompt = mContext.getString(R.string.italy_prompt);
					ImageDialog(country, prompt);
					break;
				case "new_zealand":
					country="New Zealand";
					prompt = mContext.getString(R.string.new_zealand_prompt);
					ImageDialog(country, prompt);
					break;
				case "south_africa":
					country = "South Africa";
					prompt = mContext.getString(R.string.south_africa_prompt);
					ImageDialog(country, prompt);
					break;
				case "california":
					country = "California";
					prompt = mContext.getString(R.string.california_prompt);
					ImageDialog(country, prompt);
					break;
				case "france":
					country="France";
					prompt = mContext.getString(R.string.france_prompt);
					ImageDialog(country, prompt);
					break;
				default:
					Log.e("content", "Invalid content description");
			}
			
		}
		
	}
}
