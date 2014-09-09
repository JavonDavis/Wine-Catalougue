/**
 * 
 */
package com.jwray.jwraywines.classes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.jwray.jwraywines.classes.databases.WineOpenHelper;

/**
 * @author Javon Davis
 *
 */
public class WineProvider extends ContentProvider implements ParcelKeys {

	SQLiteDatabase db;
	WineOpenHelper wineHelper;
	
	private final static UriMatcher uriMatcher;
	
	static
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); // Initializes uriMatcher
		uriMatcher.addURI(WineContract.CONTENT_AUTHORITY, WINE_TABLE_NAME, WineContract.WINES);
		uriMatcher.addURI(WineContract.CONTENT_AUTHORITY, WINE_TABLE_NAME+"/#", WineContract.WINE_BY_ID);
	}

	@Override
	public boolean onCreate() {
		wineHelper = new WineOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		db = wineHelper.getReadableDatabase();
		
		switch(uriMatcher.match(uri))
		{
			case WineContract.WINES:
				if (TextUtils.isEmpty(sortOrder)) 
					sortOrder = COLUMN_NAME+" ASC";
				break;
			case WineContract.WINE_BY_ID:
				selection = COLUMN_ID+" = " +uri.getLastPathSegment();
				if (TextUtils.isEmpty(sortOrder)) 
					sortOrder = COLUMN_NAME+" ASC";
				break;
			default:
				throw new IllegalArgumentException("Unrecognized uri");
		}
		
		cursor = db.query(WINE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		db = wineHelper.getReadableDatabase();
		
		if(uriMatcher.match(uri)==WineContract.WINES)
		{
			db.insert(WINE_TABLE_NAME, null, values);
		}
		
		db.close();
		
		getContext().getContentResolver().notifyChange(uri, null); // 
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
