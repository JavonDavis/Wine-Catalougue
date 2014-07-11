package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.DatabaseHandler;
import com.jwray.jwraywines.classes.Wine;

/**
 * 
 * @author Javon Davis
 *
 */
public class HomeFragment extends Fragment
{
	private Context mContext;
	private EditText wineSearch;
	private static final String NAME_IDENTIFIER ="name";
	private DatabaseHandler obj = null;;
	private Random generator = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		obj = new DatabaseHandler(mContext);
		generator = new Random();
		
	}

	public static HomeFragment newInstance()
	{
		return new HomeFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		try{
			rootView = inflater.inflate(R.layout.fragment_home, container,false);
		}
		catch(InflateException e)
		{
			Log.e("Search inflater", e.toString());
		}
		
		wineSearch = (EditText) rootView.findViewById(R.id.wineSearchView);
		
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
	                    intent.putExtra(NAME_IDENTIFIER, query);
	                    
	                    startActivity(intent);
	                }
	            }
	            return false;
	        }
	    });
		
		Button viewAll = (Button) rootView.findViewById(R.id.viewAll);
		
		viewAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), WineListActivity.class);
				
				startActivity(intent);
				
			}
		});
		ArrayList<Wine> wines = (ArrayList<Wine>) obj.getAllWines();
		Integer[] drawableIds = new Integer[4];
		;
		
		for(int i =0; i<4;i++)
		{
			Wine wine = wines.get(generator.nextInt(wines.size()));
			drawableIds[i] = wine.getPictureId(mContext);
		}
			
		Log.d("length",drawableIds[0]+"");
		
		return rootView;
	}
	
}
