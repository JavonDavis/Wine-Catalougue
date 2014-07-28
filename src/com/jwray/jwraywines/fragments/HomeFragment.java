package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.FavoriteManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

/**
 * 
 * @author Javon Davis
 *
 */
public class HomeFragment extends Fragment implements ParcelKeys,ParcelKeys.OptionNotifiers{

	private Context mContext;
	private GridView favorites;
	private static FavoriteAdapter favAdapter;
	private FavoriteManager favObj;
	private ListView options;
	private HomeOptionAdapter listAdapter;
	private static List<String> optionSet = new ArrayList<String>();
	private TextView prompt_text;
	private int previousIdentifier = 0;  //used to identify the previous list 
	private Button back;
	
	public static Fragment newInstance() {
		return new HomeFragment();
	}

	public HomeFragment() {
	}
	
	public static void resetFavorites()
	{
		favAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		favAdapter = new FavoriteAdapter(mContext);
		favorites.setAdapter(favAdapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		favObj = new FavoriteManager(mContext);
	}
	
	private void refreshList(int code)
	{
		optionSet.clear();
		if(code!= HOME_IDENTIFIER)
			back.setVisibility(View.VISIBLE);
		else
			back.setVisibility(View.GONE);
		
		switch(code)
		{
			case HOME_IDENTIFIER:
				prompt_text.setText("What kind of wine are you looking for?"); //move to the parcelkeys class
				optionSet.add(MEAL_TEXT);
				optionSet.add(TYPE_TEXT);
				optionSet.add(OCCASION_TEXT);
				break;
			case TYPE_IDENTIFIER:
				prompt_text.setText("What type of wine?");
				optionSet.add(TYPE_RED);
				optionSet.add(TYPE_WHITE);
				optionSet.add(TYPE_SPARKLING);
				break;
			case MEAL_IDENTIFIER:
				prompt_text.setText("Please choose an option that best describes your meal");
				optionSet.add(MEAL_CHICKEN);
				optionSet.add(MEAL_PASTA);
				optionSet.add(MEAL_PIZZA);
				optionSet.add(MEAL_PORK);
				break;
		}
		if(listAdapter!=null)
			listAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		
		if(rootView!=null)
		{
			back = (Button) rootView.findViewById(R.id.backButton);
			
			back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refreshList(getPreviousIdentifier());
					
				}
			});
			
			prompt_text = (TextView) rootView.findViewById(R.id.prompter);
			options = (ListView) rootView.findViewById(R.id.optionsList);
			
			refreshList(HOME_IDENTIFIER);//give meaning 
			listAdapter = new HomeOptionAdapter(mContext, android.R.layout.simple_list_item_1, optionSet);
			
			options.setAdapter(listAdapter);
			options.setOnItemClickListener(new InitialListener());
			
			
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
	
	/**
	 * @return the previousIdentifier
	 */
	public int getPreviousIdentifier() {
		return previousIdentifier;
	}

	/**
	 * @param previousIdentifier the previousIdentifier to set
	 */
	public void setPreviousIdentifier(int previousIdentifier) {
		this.previousIdentifier = previousIdentifier;
	}

	/*============================== Listeners =============================================*/
	private class InitialListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			String option = (String) parent.getItemAtPosition(position);
			switch(option)
			{
				case TYPE_TEXT:
					refreshList(TYPE_IDENTIFIER);
					setPreviousIdentifier(HOME_IDENTIFIER);
					break;
				case TYPE_RED:
					break;
				case MEAL_TEXT:
					refreshList(MEAL_IDENTIFIER);
					setPreviousIdentifier(HOME_IDENTIFIER);
					break;
			}
		}
		
	}
	
	/*==========================Private Classes=============================================*/
	
	private class HomeOptionAdapter extends ArrayAdapter<String>
	{

		public HomeOptionAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		public HomeOptionAdapter(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(getContext());
		        view = inflater.inflate(R.layout.home_option_item, null);

		    }
			
			String option = getItem(position);
			
			if(option!= null)
			{
				TextView item = (TextView) view.findViewById(R.id.option);
				item.setText(option);
			}
			
			return view;
		}
		
	}

}

