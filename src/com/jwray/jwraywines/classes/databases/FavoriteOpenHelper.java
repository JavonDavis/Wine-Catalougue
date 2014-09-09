package com.jwray.jwraywines.classes.databases;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for the sqlLite database used to house all the favorite wines
 * @author Javon Davis
 *
 */
public class FavoriteOpenHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "favoriteManagement";
	private static final String TABLE_NAME = "favorites";
	private static final String KEY_ID = "wine_id";

	@SuppressWarnings("unused")
	private Context mContext;
	
	public FavoriteOpenHelper(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		mContext = context;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "create table "+TABLE_NAME+" ("
				+ KEY_ID +" integer);";
		
		db.execSQL(createTable);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
						
		onCreate(db);
		
	}
	
	public void insert(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_ID, id);
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
	
	public List<Integer> getAllFavorites()
	{
		List<Integer> ids = new ArrayList<Integer>();

		//query
		String query = "select * from "+TABLE_NAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int id = Integer.parseInt(cursor.getString(0));
				ids.add(id);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return ids;
	}
	
	public boolean deleteFavorite(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String arg = KEY_ID+"="+id;
		
		db.delete(TABLE_NAME, arg, null);
		db.close();
		return true;
		
	}

}
