package com.jwray.jwraywines.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.adapters.FavoriteAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

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
	private static final String WINE_IDENTIFIER = "id";
	private GridView favorites;
	public static FavoriteAdapter favAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	public static HomeFragment newInstance()
	{
		return new HomeFragment();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		favAdapter = new FavoriteAdapter(mContext);
		favorites.setAdapter(favAdapter);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		final View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		
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
		
		SlidingUpPanelLayout slider = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
		
		slider.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View panel, float slideOffset) {
			}

			@Override
			public void onPanelCollapsed(View panel) {
				TextView handle = (TextView) rootView.findViewById(R.id.handle);
				handle.setText(R.string.slideUpText);
				
			}

			@Override
			public void onPanelExpanded(View panel) {
				TextView handle = (TextView) rootView.findViewById(R.id.handle);
				handle.setText(R.string.slideDownText);
			}

			@Override
			public void onPanelAnchored(View panel) {
			}

			@Override
			public void onPanelHidden(View panel) {
			}
			
			
		});
		 
		favAdapter = new FavoriteAdapter(mContext);
		
		favorites = (GridView) rootView.findViewById(R.id.favoriteView);
		favorites.setAdapter(favAdapter);

		favorites.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext,WineInformationActivity.class);

				int ID = (int) favAdapter.getItem(position);
				intent.putExtra(WINE_IDENTIFIER, ID);
				startActivity(intent);
			}

		});

		//Check to ensure it waits for when async task is finished in the case of the database being re-populated
		
		
		return rootView;
	}
	
}
