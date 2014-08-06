/**
 * 
 */
package com.jwray.jwraywines.classes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.databases.FavoriteManager;
import com.jwray.jwraywines.classes.databases.WineManager;

/**
 * @author Javon Davis
 * Class used to govern the general keys used throughout the application's life
 */
public interface ParcelKeys {
	static final String NAME_IDENTIFIER ="name";
	static final String WINE_IDENTIFIER = "id";
	static final String BRAND_IDENTIFIER = "brand";
	static final String COUNTRY_IDENTIFIER = "country";
	static final String NOTE_IDENTIFITER = "note_id";
	static final String TYPE_IDENTIFIER = "type";
	static final String MEAL_IDENTIFIER = "meal";
	static final String OCCASION_IDENTIFIER = "occasion";
	
	
	/*============================================ Interfaces =================================================*/
	static interface NoteDialogInterface {
		void onNoteSelected(Note note, String key);
	}
	
	/**
	 * Interface for the keys for the home page options
	 * @author Javon Davis
	 *
	 */
	interface OptionNotifiers
	{
		static final String MEAL_TEXT ="Wine with a Meal";
		static final String TYPE_TEXT = "Wine by Type";
		static final String OCCASION_TEXT = "Wine for an Occasion";
		static final String TYPE_RED = "Red";
		static final String TYPE_WHITE = "White";
		static final String TYPE_ROSE = "Rose";
		static final String TYPE_SPARKLING = "Sparkling";
		static final String MEAT_CHICKEN = "Chicken";
		static final String MEAL_PASTA = "Pasta";
		static final String MEAL_PIZZA= "Pizza";
		static final String MEAT_PORK = "Pork";
		static final String MEAT_STEAK = "Steak";
		static final String MEAT_BEEF = "Beef";
		static final String MEAL_MEAT = "Meat";
		static final String MEAL_CHEESE = "Cheese";
		static final String MEAL_FRUIT = "Fruits";
		static final String MEAL_VEGGIES = "Vegetables";
		static final String MEAL_SEAFOOD = "Seafood";
		static final String OCCASION_ALL_TEXT = "All";
		static final String OCCASION_BDAY_TEXT = "Birthday/Anniversary";
		static final String OCCASION_COCKTAIL_TEXT = "Cocktails";
		static final String OCCASION_CHRISTMAS_TEXT = "Christmas";
		static final String OCCASION_DATE_TEXT = "Wine for a date";
		static final String RED_LIGHT = "Light";
		static final String RED_MEDIUM = "Medium";
		static final String RED_DARK = "Dark";
		static final String WHITE_DRY = "Dry";
		static final String WHITE_SWEET = "Sweet";
		static final String WHITE_RICH = "Rich";
		static final String GIFT_HOLIDAY= "Holiday Gift";
		static final int START_IDENTIFIER = 0;
		static final int HOME_IDENTIFIER = 1;
		static final int MEAL_IDENTIFIER = 2;
		static final int TYPE_IDENTIFIER = 3;
		static final int OCCASION_IDENTIFIER = 4;
		static final int WHITE_IDENTIFIER = 5;
		static final int RED_IDENTIFIER = 6;
		static final int MEAT_IDENTIFIER = 7;
		
	}
	
	/**
	 * Adapter for the facorites grid on homepage
	 * @author Javon Davis
	 *
	 */
	static class FavoriteAdapter extends BaseAdapter{

		Context mContext;
		ArrayList<Integer> mWineIds;
		FavoriteManager favObj;
		WineManager obj;
		
		
		public FavoriteAdapter(Context context) {
			mContext = context;
			favObj = new FavoriteManager(mContext);
			obj = new WineManager(mContext);
			mWineIds = (ArrayList<Integer>) favObj.getAllFavorites();
			
		}
		
		@Override
		public int getCount() {
			
			return mWineIds.size();
		}

		@Override
		public Object getItem(int position) {
			
			return mWineIds.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			
			int id = (int) getItem(position);
			
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(mContext);
		        view = inflater.inflate(R.layout.wine_list_item, parent,false);

		    }
			
			try
			{
				Wine wine = obj.getWine(id);
				
				if(wine!= null)
				{
					ImageView wineImage = (ImageView) view.findViewById(R.id.wineView);
					TextView wineName = (TextView) view.findViewById(R.id.wineName);
					TextView wineDescription = (TextView) view.findViewById(R.id.wineDescription);
					
					wineImage.setVisibility(View.GONE);
					//TODO delete the imageview or load it in async task
					
					wineName.setText(wine.getName());
					wineDescription.setText("Country:"+wine.getCountry()+"\nBrand:"+wine.getBrand());
				}
			}
			catch(java.lang.NullPointerException e)
			{
				
			}
			
			return view;
		}

	}
}
