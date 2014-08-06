package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.FavoriteManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

/**
 * Fragment for the homepage, processing for the options list and the favorites are done here
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.home, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	/**
	 * Class used to reload the options on the home page based on a code
	 * @param code
	 */
	public void refreshList(int code)
	{
		optionSet.clear();
		switch(code)
		{
			case HOME_IDENTIFIER:
				prompt_text.setText("What kind of wine are you looking for?"); //move to the ParcelKeys class
				optionSet.add(MEAL_TEXT);
				optionSet.add(TYPE_TEXT);
				optionSet.add(OCCASION_TEXT);
				setPreviousIdentifier(START_IDENTIFIER);
				break;
			case OptionNotifiers.TYPE_IDENTIFIER:
				prompt_text.setText("What type of wine?");
				optionSet.add(TYPE_RED);
				optionSet.add(TYPE_ROSE);
				optionSet.add(TYPE_WHITE);
				optionSet.add(TYPE_SPARKLING);
				setPreviousIdentifier(HOME_IDENTIFIER);
				break;
			case OptionNotifiers.MEAL_IDENTIFIER:
				prompt_text.setText("Please choose an option that best describes your meal");
				optionSet.add(MEAL_MEAT);
				optionSet.add(MEAL_PASTA);
				optionSet.add(MEAL_PIZZA);
				optionSet.add(MEAL_CHEESE);
				optionSet.add(MEAL_VEGGIES);
				optionSet.add(MEAL_SEAFOOD);
				optionSet.add(MEAL_FRUIT);
				setPreviousIdentifier(HOME_IDENTIFIER);
				break;
			case OptionNotifiers.OCCASION_IDENTIFIER:
				prompt_text.setText("What's the occasion?");
				optionSet.add(OCCASION_ALL_TEXT);
				optionSet.add(OCCASION_BDAY_TEXT);	
				optionSet.add(OCCASION_CHRISTMAS_TEXT);
				optionSet.add(OCCASION_COCKTAIL_TEXT);
				setPreviousIdentifier(HOME_IDENTIFIER);
				break;
			case OptionNotifiers.WHITE_IDENTIFIER:
				prompt_text.setText("What kind of white?");
				optionSet.add(WHITE_DRY);
				optionSet.add(WHITE_RICH);		
				optionSet.add(WHITE_SWEET);
				setPreviousIdentifier(OptionNotifiers.TYPE_IDENTIFIER);
				break;
			case OptionNotifiers.RED_IDENTIFIER:
				prompt_text.setText("What kind of red?");
				optionSet.add(RED_LIGHT);		
				optionSet.add(RED_MEDIUM);
				optionSet.add(RED_DARK);
				setPreviousIdentifier(OptionNotifiers.TYPE_IDENTIFIER);
				break;
			case OptionNotifiers.MEAT_IDENTIFIER:
				prompt_text.setText("What kind of meat?");
				optionSet.add(MEAT_BEEF);
				optionSet.add(MEAT_CHICKEN);		
				optionSet.add(MEAT_STEAK);
				optionSet.add(MEAT_PORK);
				setPreviousIdentifier(OptionNotifiers.MEAL_IDENTIFIER);
				break;
			default:
				Toast.makeText(mContext, "Invalid Identifier", Toast.LENGTH_LONG).show();
				break;
		}
		if(listAdapter!=null)
			listAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		final View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		
		if(rootView!=null)
		{
			
			prompt_text = (TextView) rootView.findViewById(R.id.prompter);
			options = (ListView) rootView.findViewById(R.id.optionsList);
			
			refreshList(HOME_IDENTIFIER);//give meaning 
			listAdapter = new HomeOptionAdapter(mContext, android.R.layout.simple_list_item_1, optionSet);
			
			options.setAdapter(listAdapter);
			options.setOnItemClickListener(new InitialListener());
			
			
			SlidingUpPanelLayout slider = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
			
			if(slider!=null)
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
			Intent intent;
			switch(option)
			{
				case TYPE_TEXT:
					refreshList(OptionNotifiers.TYPE_IDENTIFIER);
					break;
				case TYPE_RED:
					refreshList(OptionNotifiers.RED_IDENTIFIER);
					break;
				case TYPE_ROSE:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case TYPE_WHITE:
					refreshList(OptionNotifiers.WHITE_IDENTIFIER);
					break;
				case TYPE_SPARKLING:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_TEXT:
					refreshList(OptionNotifiers.MEAL_IDENTIFIER);
					break;
				case OCCASION_TEXT:
					refreshList(OptionNotifiers.OCCASION_IDENTIFIER);
					break;
				case RED_DARK:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option+" "+TYPE_RED);
                    
                    startActivity(intent);
					break;
				case RED_LIGHT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option+" "+TYPE_RED);
                    
                    startActivity(intent);
					break;
				case RED_MEDIUM:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option+" "+TYPE_RED);
                    
                    startActivity(intent);
					break;
				case WHITE_DRY:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option+" "+TYPE_WHITE);
                    
                    startActivity(intent);
					break;
				case WHITE_RICH:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option+" "+TYPE_WHITE);
                    
                    startActivity(intent);
					break;
				case WHITE_SWEET:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_PASTA:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_PIZZA:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_CHEESE:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_FRUIT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_VEGGIES:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_SEAFOOD:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAL_MEAT:
					refreshList(OptionNotifiers.MEAT_IDENTIFIER);
					break;
				case MEAT_BEEF:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAT_CHICKEN:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAT_PORK:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case MEAT_STEAK:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.MEAL_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case OCCASION_ALL_TEXT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case OCCASION_BDAY_TEXT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case OCCASION_COCKTAIL_TEXT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case OCCASION_CHRISTMAS_TEXT:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    startActivity(intent);
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
		
		@SuppressLint("InflateParams")
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

