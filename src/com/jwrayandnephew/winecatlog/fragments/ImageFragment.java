package com.jwrayandnephew.winecatlog.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.activities.WineListActivity;
import com.jwrayandnephew.winecatlog.content.Wine;
import com.jwrayandnephew.winecatlog.database.DatabaseHandler;

public class ImageFragment extends Fragment {
	ImageView image;
	Wine aWine;
	DatabaseHandler obj;
	Object main;
	
	public static final String ARG_WINE_ID = "wine_id";
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	public static ImageFragment newInstance(int sectionNumber,int id) {
		ImageFragment fragment = new ImageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putInt(ImageFragment.ARG_WINE_ID, id);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		obj = new DatabaseHandler(getActivity());		
		if (getArguments().containsKey(ARG_WINE_ID)) {
			// Load the content specified by the fragment
			// arguments.			
			aWine = obj.getWine(getArguments().getInt(
					ARG_WINE_ID));						//get the wine
			
		}	
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		main = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.wine_image,
				container, false);
		
		LinearLayout topLevel;
		try
		{
			topLevel = ((WineListActivity) main).topLevel;
		}
		catch(java.lang.ClassCastException e)
		{
			topLevel = (LinearLayout) getActivity().findViewById(R.id.image);
		}
		// Show the wine content as text in TextViews.
		int rotation = getActivity().getWindow().getWindowManager().getDefaultDisplay().getRotation();
		
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
	
		image = (ImageView) rootView.findViewById(R.id.image);
		image.setImageBitmap(loadImageFromStorage(aWine.getName()));
		return rootView;
		
	}
	
	private Bitmap loadImageFromStorage(String name)
	{
		ContextWrapper cw = new ContextWrapper(getActivity());
		
        // path to /data/data/yourapp/app_data/Wine Pictures
       File path = cw.getDir("Wine Pictures", Context.MODE_PRIVATE);
	    try {
	        File f=new File(path, name+".png");
	        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	        return b;
	    } 
	    catch (FileNotFoundException e) 
	    {
	        Toast.makeText(getActivity(), "Error 1", Toast.LENGTH_LONG).show();
	    }
	    catch(Exception e)
	    {
	    	Toast.makeText(getActivity(), "Error 2", Toast.LENGTH_LONG).show();
	    }
	    
	    return null;
	}
}
