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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.FavoriteManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

public class BrandFragment extends Fragment implements ParcelKeys
{
	private Context mContext;
	private EditText wineSearch;
	private GridView favorites;
	private static FavoriteAdapter favAdapter;
	private FavoriteManager favObj;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		favObj = new FavoriteManager(mContext);
	}

	
	public static BrandFragment newInstance()
	{
		return new BrandFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//TODO- surround in try catch
		final View rootView = inflater.inflate(R.layout.fragment_search, container,false);
			
		
		if(rootView!=null)
		{
			wineSearch = (EditText) rootView.findViewById(R.id.wineSearchView);
			wineSearch.setHint(R.string.wineBrandSearchHint);
			
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
		                    intent.putExtra(BRAND_IDENTIFIER, query);
		                    
		                    startActivity(intent);
		                }
		            }
		            return false;
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
					handle.setTextColor(mContext.getResources().getColor(R.color.white));
					
				}

				@Override
				public void onPanelExpanded(View panel) {
					TextView handle = (TextView) rootView.findViewById(R.id.handle);
					handle.setText(R.string.slideDownText);
					handle.setTextColor(mContext.getResources().getColor(R.color.black));
				}

				@Override
				public void onPanelAnchored(View panel) {
				}

				@Override
				public void onPanelHidden(View panel) {
				}
				
				
			});
			
			TextView empty = (TextView) rootView.findViewById(R.id.favoriteEmpty);
			
			if(!favObj.getAllFavorites().isEmpty())
				empty.setVisibility(View.GONE);
			 
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
			
		}
		
		return rootView;
	}
}

