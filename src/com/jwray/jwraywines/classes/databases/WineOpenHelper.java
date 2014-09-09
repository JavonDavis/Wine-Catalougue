package com.jwray.jwraywines.classes.databases;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.Wine;

/**
 * Helper class to manage all the wines in the application
 * @author Javon Davis
 *
 */
public class WineOpenHelper extends SQLiteOpenHelper implements ParcelKeys {
	
	private static final int DATABASE_VERSION = 41;
	private static final String DATABASE_NAME = "wineManagement";
	
	@SuppressWarnings("unused")
	private Context context;
	private FavoriteOpenHelper favObj;
	private ArrayList<Integer> favorites;

	//neccessary contructor to call parent constructor
	public WineOpenHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		this.context = context;
		favObj = new FavoriteOpenHelper(context);
		favorites = (ArrayList<Integer>) favObj.getAllFavorites();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "create table "+WINE_TABLE_NAME+" ("
				+ COLUMN_ID +" integer primary key,"+COLUMN_NAME +" text,"+ COLUMN_DESCRIPTION +" text,"+ COLUMN_COUNTRY +" text,"
				+ COLUMN_TASTING_NOTES +" text,"+ COLUMN_MATURATION +" text,"+ COLUMN_FOOD_PAIRING +" text,"+ COLUMN_SERVING_SUGGESTION +" text,"
				+ COLUMN_WINE_OF_ORIGIN +" text,"+ COLUMN_CELLARING_POTENTIAL +" text,"+ COLUMN_WINEMAKER_NOTES +" text,"
				+ COLUMN_BRAND +" text,"+ COLUMN_ALCOHOL_LEVEL +" double,"+ COLUMN_PRONOUNCIATION +" text,"+ COLUMN_MEAL +" text,"
				+ COLUMN_OCCASION +" text,"+ COLUMN_TYPE +" text,"
				+ COLUMN_WINE_ID +" integer);";
		
		db.execSQL(createTable);
		//db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + WINE_TABLE_NAME);
				
		onCreate(db);
	}
	
	//wine crud methods 
	public void insert(Wine wine)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_NAME, wine.getName());
		values.put(COLUMN_WINE_ID, wine.getId());
		values.put(COLUMN_DESCRIPTION, wine.getDescription());
		values.put(COLUMN_WINE_OF_ORIGIN, wine.getWineOfOrigin());
		values.put(COLUMN_TASTING_NOTES, wine.getTastingNotes());
		values.put(COLUMN_MATURATION, wine.getMaturation());
		values.put(COLUMN_FOOD_PAIRING, wine.getFoodPairing());
		values.put(COLUMN_SERVING_SUGGESTION, wine.getServingSuggestion());
		values.put(COLUMN_COUNTRY, wine.getCountry());
		values.put(COLUMN_ALCOHOL_LEVEL, wine.getAlcohol_level());
		values.put(COLUMN_CELLARING_POTENTIAL, wine.getCellaringPotential());
		values.put(COLUMN_WINEMAKER_NOTES, wine.getWinemakerNotes());
		values.put(COLUMN_BRAND, wine.getBrand());
		values.put(COLUMN_MEAL,wine.getMeal());
		values.put(COLUMN_PRONOUNCIATION,wine.getPronounciation());
		values.put(COLUMN_OCCASION,wine.getOccasion());
		values.put(COLUMN_TYPE,wine.getType());
		
		//saveToInternalSorage(wine.getBitmap(), wine.getName());
		
		// Inserting Row
		db.insert(WINE_TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	public List<Wine> getAllWines()
	{
		List<Wine> wines = new ArrayList<Wine>();

		//query
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				// Adding wine to list
				wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	}

	public List<String> getAllBrands()
	{
		List<String> brands = new ArrayList<String>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) 
			do {
				String brand = cursor.getString(11);
				if(!brands.contains(brand.trim()))
					brands.add(brand.trim());
				
			} while (cursor.moveToNext());
		
		cursor.close();
		db.close();
		
		return brands;
	}
	
	public List<String> getAllCountries()
	{
		List<String> countries = new ArrayList<String>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) 
			do {
				String country = cursor.getString(3);
				if(!countries.contains(country.trim()))
					countries.add(country.trim());
				
			} while (cursor.moveToNext());
		
		cursor.close();
		db.close();
		
		return countries;
	}
	
	public List<Wine> getAllWines(String name)
	{
		List<Wine> wines = new ArrayList<Wine>();

		//query
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				// Adding wine to list
				if(wine.getName().toLowerCase(Locale.US).contains(name.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	}
	
	
	public List<Wine> getAllWinesByBrand(String brand)
	{
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				
				// Adding wine to list
				if(wine.getBrand().toLowerCase(Locale.US).contains(brand.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	} 
	
	public List<Wine> getAllWinesByType(String type)
	{
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				
				// Adding wine to list
				if(wine.getType().toLowerCase(Locale.US).contains(type.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	}
	
	public List<Wine> getAllWinesByCountry(String country)
	{
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				
				// Adding wine to list
				if(wine.getCountry().toLowerCase(Locale.US).contains(country.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	} 
	
	
	public Wine getWine(int id)
	{
		//query
		String query = "select * from "+WINE_TABLE_NAME+" where "+COLUMN_WINE_ID+"="+id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst())
		{
			Wine wine = new Wine(Integer.parseInt(cursor.getString(17)));
			wine.setColumnId(Integer.parseInt(cursor.getString(0)));
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
			
			if(favorites.contains(wine.getId()))
				wine.setFavorite(true);
			
			cursor.close();
			db.close();
			return wine;
		}
		cursor.close();
		db.close();	
		return null;
	}

	public boolean deleteAllWines()
	{
		SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
	    db.delete(WINE_TABLE_NAME, null, null);
	    return true;
	}

	public List<Wine> getAllWinesByMeal(String meal) {
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				
				// Adding wine to list
				if(wine.getMeal().toLowerCase(Locale.US).contains(meal.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	}

	public List<String> getAllWineNames() {
		List<String> names = new ArrayList<String>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) 
			do {
				String name = cursor.getString(1);
				if(!names.contains(name.trim()))
					names.add(name.trim());
				
			} while (cursor.moveToNext());
		
		cursor.close();
		db.close();
		
		return names;
	}

	public List<Wine> getAllWinesByOccasion(String occasion) {
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+WINE_TABLE_NAME +" order by "+COLUMN_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
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
				
				if(favorites.contains(wine.getId()))
					wine.setFavorite(true);
				
				// Adding wine to list
				if(wine.getOccasion().toLowerCase(Locale.US).contains(occasion.toLowerCase(Locale.US)))
					wines.add(wine);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return wines;
	}
}
