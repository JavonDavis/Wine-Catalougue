package com.jwray.jwraywines.classes.databases;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.jwray.jwraywines.classes.Wine;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;


public class WineManager extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 40;
	private static final String DATABASE_NAME = "wineManagement";
	
	private static final String TABLE_NAME = "wines";
	
	//column names
	private static final String KEY_ID ="id";
	private static final String KEY_NAME ="name";
	private static final String KEY_DESCRIPTION ="description";
	private static final String KEY_ALCOHOL_LEVEL ="alcohol_level";
	private static final String KEY_MATURATION ="maturation";
	private static final String KEY_TASTING_NOTES ="tasting_notes";
	private static final String KEY_SERVING_SUGGESTION ="serving_suggestion";
	private static final String KEY_CELLARING_POTENTIAL ="cellaring_potential";
	private static final String KEY_FOOD_PAIRING ="food_pairing";
	private static final String KEY_WINE_OF_ORIGIN ="wine_of_origin";
	private static final String KEY_COUNTRY ="country";
	private static final String KEY_WINEMAKER_NOTES ="winemaker_notes";
	private static final String KEY_BRAND = "brand";
	private static final String KEY_PRONOUNCIATION = "pronounciation";
	private static final String KEY_MEAL = "meal";
	private static final String KEY_OCCASION = "occasion";
	private static final String KEY_TYPE = "type";
	
	private Context context;
	private FavoriteManager favObj;
	ArrayList<Integer> favorites;

	//neccessary contructor to call parent constructor
	public WineManager(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		this.context = context;
		favObj = new FavoriteManager(context);
		favorites = (ArrayList<Integer>) favObj.getAllFavorites();
	}
	
	/*
	 *Override Methods 
	 * onCreate has to be overridden for a different table
	 *TODO- write more abstract database handler
	 */
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "create table "+TABLE_NAME+" ("
				+ KEY_ID +" integer primary key,"+ KEY_NAME +" text,"+ KEY_DESCRIPTION +" text,"+ KEY_COUNTRY +" text,"
				+ KEY_TASTING_NOTES +" text,"+ KEY_MATURATION +" text,"+ KEY_FOOD_PAIRING +" text,"+ KEY_SERVING_SUGGESTION +" text,"
				+ KEY_WINE_OF_ORIGIN +" text,"+ KEY_CELLARING_POTENTIAL +" text,"+ KEY_WINEMAKER_NOTES +" text,"
				+ KEY_BRAND +" text,"+ KEY_ALCOHOL_LEVEL +" double,"+ KEY_PRONOUNCIATION +" text,"+ KEY_MEAL +" text,"
				+ KEY_OCCASION +" text,"+ KEY_TYPE +" text"
				+");";
		
		db.execSQL(createTable);
		//db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				
		onCreate(db);
	}
	
	//wine crud methods 
	public void insert(Wine wine)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//values.put(KEY_ID, wine.getId());
		values.put(KEY_NAME, wine.getName());
		values.put(KEY_DESCRIPTION, wine.getDescription());
		values.put(KEY_WINE_OF_ORIGIN, wine.getWineOfOrigin());
		values.put(KEY_TASTING_NOTES, wine.getTastingNotes());
		values.put(KEY_MATURATION, wine.getMaturation());
		values.put(KEY_FOOD_PAIRING, wine.getFoodPairing());
		values.put(KEY_SERVING_SUGGESTION, wine.getServingSuggestion());
		values.put(KEY_COUNTRY, wine.getCountry());
		values.put(KEY_ALCOHOL_LEVEL, wine.getAlcohol_level());
		values.put(KEY_CELLARING_POTENTIAL, wine.getCellaringPotential());
		values.put(KEY_WINEMAKER_NOTES, wine.getWinemakerNotes());
		values.put(KEY_BRAND, wine.getBrand());
		values.put(KEY_MEAL,wine.getMeal());
		values.put(KEY_PRONOUNCIATION,wine.getPronounciation());
		values.put(KEY_OCCASION,wine.getOccasion());
		values.put(KEY_TYPE,wine.getType());
		
		//saveToInternalSorage(wine.getBitmap(), wine.getName());
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
	
	//TODO delete if proven to be no longer necessary
	@SuppressWarnings("unused")
	private String saveToInternalSorage(Bitmap bitmapImage,String name){
        ContextWrapper contextWrapper = new ContextWrapper(context);
        
         // path to /data/data/yourapp/app_data/Wine Pictures
        File directory = contextWrapper.getDir("Wine Pictures", Context.MODE_PRIVATE);
        
        // Create picture
        File picturePath =new File(directory,name+".png");

        FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(picturePath);

       // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            
        } catch (Exception e) {
        	//Toast.makeText(context, "Error Saving Image - "+e, Toast.LENGTH_LONG).show();
        }
        return directory.getAbsolutePath();
    }
	
	public List<Wine> getAllWines()
	{
		List<Wine> wines = new ArrayList<Wine>();

		//query
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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

	public List<Wine> getAllWines(String name)
	{
		List<Wine> wines = new ArrayList<Wine>();

		//query
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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
		
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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
		
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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
		
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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
		String query = "select * from "+TABLE_NAME+" where "+KEY_ID+"="+id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst())
		{
			Wine wine = new Wine();
			wine.setId(Integer.parseInt(cursor.getString(0)));
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
	    db.delete(TABLE_NAME, null, null);
	    return true;
	}

	public List<Wine> getAllWinesByMeal(String meal) {
		List<Wine> wines = new ArrayList<Wine>();
		
		String query = "select * from "+TABLE_NAME +" order by "+KEY_NAME+" asc";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Wine wine = new Wine();
				wine.setId(Integer.parseInt(cursor.getString(0)));
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
}
