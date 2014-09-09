package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.WineContract;
import com.jwray.jwraywines.classes.databases.NoteOpenHelper;

/**
 * Fragment for displaying notes for a wine
 * @author Javon Davis
 *
 */
public class NotesFragment extends Fragment implements ParcelKeys,ParcelKeys.NoteDialogInterface{

	private Context mContext;
	private NoteOpenHelper notesObj;
	private NoteAdapter noteAdapter;
	private static Note note;
	private ArrayList<Note> notes;
	private static ListView notesList;
	private static TextView emptyText;
	private Wine wine;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		notesObj = new NoteOpenHelper(mContext);
		Long _id = getActivity().getIntent().getLongExtra(WINE_IDENTIFIER,-1);
		
		Uri uri = Uri.withAppendedPath(WineContract.WINES_URI, "/"+_id);
		
		Cursor mCursor = getActivity().getContentResolver().query(uri, allColumns, null, null, null);
		wine = WineContract.cursorToWine(getActivity(),mCursor);
	}
	
	public static Fragment newInstance() {
		
		return new NotesFragment();
	}
	
	public static void hideEmpty()
	{
		try
		{
			emptyText.setVisibility(View.GONE);
		}
		catch(java.lang.NullPointerException e)
		{
			
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		int id = wine.getId();
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_notes, container,false);
			
			emptyText = (TextView) rootView.findViewById(R.id.noteEmpty);
			notesList = (ListView) rootView.findViewById(R.id.notesList);
			
			notes = (ArrayList<Note>) notesObj.getNotesByWineId(id);
			
			if(!notes.isEmpty())
				emptyText.setVisibility(View.GONE);
			
			noteAdapter = new NoteAdapter(mContext,
					android.R.layout.simple_list_item_2,
					notes);
			
			                     
			notesList.setAdapter(noteAdapter);
			
			notesList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					note = noteAdapter.getItem(position);
					
					OptionsDialogFragment.setContext(mContext);
					OptionsDialogFragment.setNote(note);
					
					DialogFragment dialog = new OptionsDialogFragment();
			        dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OptionsDialog");
				}
			});
			
		}
		catch(InflateException e)
		{
			Log.e("Wine Notes inflater", e.toString());
		}
		return rootView;
	}

	@Override
	public void onNoteSelected(Note note, String key) {
		if(notes.isEmpty())
			emptyText.setVisibility(View.GONE);
		switch(key)
		{
			case "delete":
				notes = (ArrayList<Note>) notesObj.getNotesByWineId(wine.getId());
				
				noteAdapter = new NoteAdapter(mContext,
						android.R.layout.simple_list_item_2,
						notes);
				
				                     
				notesList.setAdapter(noteAdapter);
				
				notesList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Note note = noteAdapter.getItem(position);
						
						OptionsDialogFragment.setContext(mContext);
						OptionsDialogFragment.setNote(note);
						
						DialogFragment dialog = new OptionsDialogFragment();
				        dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OptionsDialog");
					}
				});
				break;
		}
		
	}

	/**
	 * @return the notesList
	 */
	public static ListView getNotesList() {
		return notesList;
	}
	/*============================ Adapter =====================================*/
	public static class NoteAdapter extends ArrayAdapter<Note> {
		
		public NoteAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		public NoteAdapter(Context context, int resource, List<Note> notes) {
		    super(context, resource, notes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(getContext());
		        view = inflater.inflate(R.layout.note_item, parent,false);

		    }
			
			Note note = getItem(position);
			
			if(note!=null)
			{
				TextView titleText = (TextView) view.findViewById(R.id.titleView);
				TextView contentText = (TextView) view.findViewById(R.id.contentPortion);
				TextView dateText = (TextView) view.findViewById(R.id.dateView);
				
				titleText.setText(note.getTitle());
				
				String content = note.getContent();
				
				int portionStop = content.length() > 200 ? 80:content.length()<26 ? content.length():content.length()/2;
	
				String portion = content.substring(0,portionStop);
				
				if(portion.length()<content.length()-1)
					portion+="...";
				
				contentText.setText(portion);
				
				String date = note.getDateCreated();
				
				if(date==null)
					date = "Could not retrieve date";
				dateText.setText(date);
			}
			return view;
		}
	}

	
}
