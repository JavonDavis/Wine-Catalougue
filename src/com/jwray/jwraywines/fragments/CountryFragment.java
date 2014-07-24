package com.jwray.jwraywines.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.ParcelKeys;

/**
 * 
 * @author Javon Davis
 *
 */
public class CountryFragment extends Fragment implements ParcelKeys
{
	private Context mContext;
	private EditText wineSearch;
	
	//TODO check countries, i.e california
	
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

		final View rootView = inflater.inflate(R.layout.fragment_search, container,false);
			
		if(rootView!=null)
		{
			wineSearch = (EditText) rootView.findViewById(R.id.wineSearchView);
			wineSearch.setHint(R.string.wineCountrySearchHint);
			
			wineSearch.setOnTouchListener(new OnTouchListener() {
		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            //final int DRAWABLE_LEFT = 0;
		            //final int DRAWABLE_TOP = 1;
		            final int DRAWABLE_RIGHT = 2;
		            //final int DRAWABLE_BOTTOM = 3;

		            if(event.getAction() == MotionEvent.ACTION_UP) {
		                if(event.getRawX()>= ((wineSearch.getRight() - wineSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))-50) {
		                	String query = wineSearch.getText().toString();
		                	
		                    Intent intent = new Intent(mContext,WineListActivity.class);
		                    
		                    Log.d("query", query);
		                    intent.putExtra(COUNTRY_IDENTIFIER, query);
		                    
		                    startActivity(intent);
		                }
		            }
		            return false;
		        }
		    });
			
		}
		
		return rootView;
	}
	
}
