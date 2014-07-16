package com.jwray.jwraywines.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.databases.WineManager;

/**
 * @author Javon
 *
 */
public class WinePhotoFragment extends Fragment {

	private Context mContext;
	private WineManager obj;
	private static String WINE_IDENTIFIER = "id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		obj = new WineManager(mContext);

	}
	
	public static Fragment newInstance() {

		return new WinePhotoFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_pics, container,false);
			
			int id = getActivity().getIntent().getIntExtra(WINE_IDENTIFIER,-1);
			
			Wine aWine = null;
		    
			if(id>0)
			{
				aWine = obj.getWine(id);
				
				ImageView image = (ImageView) rootView.findViewById(R.id.wine_image);
				image.setImageDrawable(aWine.getPicture(mContext));
			}
		}
		catch(InflateException e)
		{
			Log.e("Wine Detail inflater", e.toString());
		}
		return rootView;
	}

}
