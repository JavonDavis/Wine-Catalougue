package com.jwray.jwraywines.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.NoteOpenHelper;

/**
 * Dialog for adding a new note
 * @author Javon
 *
 */
public class NoteDialogFragment extends DialogFragment implements ParcelKeys{

	private NoteOpenHelper notesObj;
	private static Context context;
	
	public NoteDialogFragment()
	{		
	}
	
	NoteDialogInterface mListener;
	
	@Override
	 public void onAttach(Activity activity) {
	    super.onAttach(activity);    
	    try {    
	        mListener = (NoteDialogInterface) activity;
	    } catch (ClassCastException e) {    
	        throw new ClassCastException(activity.toString()
	                + " must implement OnNoteDialogListener");
	    }
	 }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.new_note);
        
        notesObj = new NoteOpenHelper(getContext());
        //Get the Layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        final View noteItem = inflater.inflate(R.layout.note_dialog, null);
        //inflate the custom view
        builder.setView(noteItem)
        		.setPositiveButton(R.string.ok, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						int id = getArguments().getInt("id");
						
						EditText title = (EditText) noteItem.findViewById(R.id.title);
						EditText content = (EditText) noteItem.findViewById(R.id.content);
						
						SimpleDateFormat sdf = new SimpleDateFormat("h:m a d MMM, yyyy",Locale.US);
						String date = sdf.format(new Date());
	
						Note note = new Note(title.getText().toString(),content.getText().toString(),id,date);
						
						notesObj.insertNote(note);
						
						NotesFragment.hideEmpty();
						mListener.onNoteSelected(null,null);
						Toast.makeText(getActivity(), "Note Created", Toast.LENGTH_LONG).show();
						
				
					}
				})
				.setNegativeButton(R.string.cancel, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
        
		return builder.create();
	}

	public static void setContext(Context mContext)
	{
		context = mContext;
	}
	
	private Context getContext() {
		return context;
	}
	
}
