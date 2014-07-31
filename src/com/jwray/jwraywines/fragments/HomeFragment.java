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
import android.widget.Button;
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		inflater.inflate(R.menu.home, menu);
		super.onCreateOptionsMenu(menu, inflater);
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
				prompt_text.setText("What kind of wine are you looking for?"); //move to the ParcelKeys class
				optionSet.add(MEAL_TEXT);
				optionSet.add(TYPE_TEXT);
				optionSet.add(OCCASION_TEXT);
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
				optionSet.add(OCCASION_DATE_TEXT);
				optionSet.add(OCCASION_GIFT_TEXT);	
				setPreviousIdentifier(HOME_IDENTIFIER);
				break;
			case OptionNotifiers.DATE_IDENTIFIER:
				prompt_text.setText("Nice! What kind of date?");
				optionSet.add(DATE_BREAKFAST);
				optionSet.add(DATE_LUNCH);		
				optionSet.add(DATE_DINNER);
				setPreviousIdentifier(OptionNotifiers.OCCASION_IDENTIFIER);
				break;
			case OptionNotifiers.GIFT_IDENTIFIER:
				prompt_text.setText("Cool, what kind of gift are you looking for?");
				optionSet.add(GIFT_BIRTHDAY);
				optionSet.add(GIFT_ANNIVERSARY);		
				optionSet.add(GIFT_HOLIDAY);
				setPreviousIdentifier(OptionNotifiers.OCCASION_IDENTIFIER);
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
			Intent intent;
			switch(option)
			{
				case TYPE_TEXT:
					refreshList(OptionNotifiers.TYPE_IDENTIFIER);
					break;
				case TYPE_RED:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case TYPE_ROSE:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
					break;
				case TYPE_WHITE:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.TYPE_IDENTIFIER, option);
                    
                    startActivity(intent);
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
				case DATE_BREAKFAST:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    //startActivity(intent);
					break;
				case DATE_LUNCH:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    //startActivity(intent);
					break;
				case DATE_DINNER:
					intent = new Intent(mContext,WineListActivity.class);
                    
                    intent.putExtra(ParcelKeys.OCCASION_IDENTIFIER, option);
                    
                    //startActivity(intent);
					break;
				case OCCASION_GIFT_TEXT:
					refreshList(OptionNotifiers.GIFT_IDENTIFIER);
					break;
				case OCCASION_DATE_TEXT:
					refreshList(OptionNotifiers.DATE_IDENTIFIER);
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

