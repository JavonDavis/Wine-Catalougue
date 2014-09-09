/**
 * 
 */
package com.jwray.jwraywines.classes;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.jwray.jwraywines.classes.databases.FavoriteOpenHelper;

/**
 * @author Javon Davis
 *
 */
public class WineContract implements ParcelKeys{
	public static final String CONTENT_AUTHORITY ="com.jwray.jwraywines";
	
	public static final Uri WINES_URI = Uri.parse("content://"+WineContract.CONTENT_AUTHORITY+"/"+WINE_TABLE_NAME);
	
	public static final int WINES = 0;
	public static final int WINE_BY_ID = 1;
	/**
	 * 
	 */
	public WineContract() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * method to change a cursor with a single row into a wine object
	 * @param context
	 * @param cursor
	 * @return Wine object
	 * @throws IllegalArgumentException - if row cannot be made into wine
	 */
	public static Wine cursorToWine(Context context,Cursor cursor) {
		if(cursor.moveToFirst())
		{
			Wine wine = new Wine(Integer.parseInt(cursor.getString(17)));
			wine.setName(cursor.getString(1));
			wine.setDescription(cursor.getString(2));
			wine.setCountry(cursor.getString(3));
			wine.setTastingNotes(cursor.getString(4));
			wine.setMaturation(cursor.getString(5));
			wine.setFoodPairing(cursor.getString(6));
			wine.setServingSuggestion(cursor.getString(7));
			wine.setWineOfOrigin(cursor.getString(8));
			wine.setCellaringPotential(cursor.getString(9));
			wine.setWinemakerNotes(cursor.getString(10));
			wine.setBrand(cursor.getString(11));
			wine.setAlcohol_level(Double.parseDouble(cursor.getString(12)));
			wine.setPronounciation(cursor.getString(13));
			wine.setMeal(cursor.getString(14));
			wine.setOccasion(cursor.getString(15));
			wine.setType(cursor.getString(16));
			
			FavoriteOpenHelper favObj = new FavoriteOpenHelper(context);
			
			ArrayList<Integer> favorites = (ArrayList<Integer>) favObj.getAllFavorites();
			
			if(favorites.contains(wine.getId()))
				wine.setFavorite(true);
			
			cursor.close();
			return wine;
		}
		else
		{
			throw new IllegalArgumentException("Cursor could not be made into a wine object");
		}
	}
}
