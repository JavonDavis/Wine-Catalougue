/**
 * 
 */
package com.jwray.jwraywines.classes;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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
 *
 */
public interface ParcelKeys {
	static final String NAME_IDENTIFIER ="name";
	static final String WINE_IDENTIFIER = "id";
	static final String BRAND_IDENTIFIER = "brand";
	static final String COUNTRY_IDENTIFIER = "country";
	static final String NOTE_IDENTIFITER = "note_id";
	
	/*============================================ Interfaces =================================================*/
	static interface NoteDialogInterface {
		void onNoteSelected(Note note, String key);
	}
	
	interface OptionNotifiers
	{
		static final String MEAL_TEXT ="Wine with a Meal";
		static final String TYPE_TEXT = "Wine by Type";
		static final String OCCASION_TEXT = "Wine for an Occasion";
		static final String TYPE_RED = "Red";
		static final String TYPE_WHITE = "White";
		static final String TYPE_SPARKLING = "Sparkling";
		static final String MEAL_CHICKEN = "Chicken";
		static final String MEAL_PASTA = "Pasta";
		static final String MEAL_PIZZA= "Pizza";
		static final String MEAL_PORK = "Pork";
		static final int HOME_IDENTIFIER = 1;
		static final int MEAL_IDENTIFIER = 2;
		static final int TYPE_IDENTIFIER = 3;
		static final int OCCASION_IDENTIFIER = 4;
	}
	
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

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			
			int id = (int) getItem(position);
			
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(mContext);
		        view = inflater.inflate(R.layout.wine_list_item, null);

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
