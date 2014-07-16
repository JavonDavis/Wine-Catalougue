package com.jwray.jwraywines.fragments;

import java.util.ArrayList;

import android.content.Context;
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
import android.widget.ListView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.adapters.NoteAdapter;
import com.jwray.jwraywines.classes.databases.NotesManager;
import com.jwray.jwraywines.classes.interfaces.NoteDialogInterface;

public class NotesFragment extends Fragment implements NoteDialogInterface{

	private Context mContext;
	private static String WINE_IDENTIFIER = "id";
	private NotesManager notesObj;
	private NoteAdapter noteAdapter;
	private static Note note;
	private ArrayList<Note> notes;
	public static ListView notesList;
	int id;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		notesObj = new NotesManager(mContext);
		id = getActivity().getIntent().getIntExtra(WINE_IDENTIFIER,-1);
	}
	
	public static Fragment newInstance() {
		
		return new NotesFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_notes, container,false);
			notesList = (ListView) rootView.findViewById(R.id.notesList);
			
			notes = (ArrayList<Note>) notesObj.getNotesByWineId(id);
			
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
	public void onNewNote(Note note, String key) {
		switch(key)
		{
			case "delete":
				notes = (ArrayList<Note>) notesObj.getNotesByWineId(id);
				
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

	@Override
	public void viewNote(Note note, String view) {
	}
}
