package com.jwray.jwraywines.classes.databases;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jwray.jwraywines.classes.Note;

/**
 * Helper class used to manage all the notes in the application and links them to the wine by using an associative ID which is the ID of it's wine
 * @author Javon Davis
 *
 */
public class NoteOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 5;
	private static final String DATABASE_NAME = "NoteManagement";
	private static final String TABLE_NAME = "notes";

	private static final String KEY_ID = "id";
	private static final String KEY_WINE_ID = "wine_id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_DATE = "date";
	
	@SuppressWarnings("unused")
	private Context mContext;
	
	public NoteOpenHelper(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		mContext = context;
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "create table "+TABLE_NAME+" ("
				+KEY_ID +" integer primary key,"+KEY_TITLE+" text,"+ KEY_CONTENT +" text,"+ KEY_WINE_ID +" integer,"+KEY_DATE+" text);";
		
		db.execSQL(createTable);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
						
		onCreate(db);
		
	}
	
	public void insertNote(Note note)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_WINE_ID, note.getId());
		values.put(KEY_CONTENT, note.getContent());
		values.put(KEY_TITLE, note.getTitle());
		values.put(KEY_CONTENT, note.getContent());
		values.put(KEY_DATE, note.getDateCreated());
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
	
	public List<Note> getAllNotes()
	{
		List<Note> notes = new ArrayList<Note>();

		//query
		String query = "select * from "+TABLE_NAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Note note;
		
		int identifier,wineID;
		String title,content,date;
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				identifier = Integer.parseInt(cursor.getString(0));
				title = cursor.getString(1);
				content = cursor.getString(2);
				wineID = Integer.parseInt(cursor.getString(3));
				date = cursor.getString(4);
								
				note = new Note(title,content,wineID,date);
				note.setIdentifier(identifier);
				notes.add(note);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return notes;
	}
	
	public List<Note> getNotesByWineId(int id)
	{
		List<Note> notes = new ArrayList<Note>();

		//query
		String query = "select * from "+TABLE_NAME+" where "+KEY_WINE_ID+"="+id+";";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Note note;
		int identifier,wineID;
		String title,content,date;
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				identifier = Integer.parseInt(cursor.getString(0));
				title = cursor.getString(1);
				content = cursor.getString(2);
				wineID = Integer.parseInt(cursor.getString(3));
				date = cursor.getString(4);

				note = new Note(title,content,wineID,date);
				note.setIdentifier(identifier);
				notes.add(note);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return notes;
	}
	
	public boolean deleteNote(Note note)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String arg = KEY_ID+"="+note.getIdentifier();
		
		db.delete(TABLE_NAME, arg, null);
		db.close();
		return true;
		
	}
}
