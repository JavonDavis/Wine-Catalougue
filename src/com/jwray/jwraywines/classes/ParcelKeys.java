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
	static String BRAND_IDENTIFIER = "brand";
	static String COUNTRY_IDENTIFIER = "country";
	static String NOTE_IDENTIFITER = "note_id";
	
	/*============================================ Interfaces =================================================*/
	static interface NoteDialogInterface {
		void onNoteSelected(Note note, String key);
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
