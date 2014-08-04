package com.jwray.jwraywines.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.NoteViewActivity;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.NotesManager;

/**
 * Fragment for the Dialog shown for a not click
 * @author Javon Davis
 *
 */
public class OptionsDialogFragment extends DialogFragment implements ParcelKeys{
	
	private final String[] options = {"View","Delete"};
	private NotesManager notesObj;
	private static Context context;
	private static Note note;
	
	private NoteDialogInterface mListener;
	
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		notesObj = new NotesManager(getContext());
		note = getNote();
		
	    builder.setTitle(R.string.pick)
	           .setItems(options, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               switch(which)
	               {
	               		case 0:
	               			Intent intent = new Intent(getContext(),NoteViewActivity.class);
	               			intent.putExtra(NOTE_IDENTIFITER, note.getIdentifier());
	               			startActivity(intent);
	               			break;
	               		case 1:
	               			notesObj.deleteNote(note);
	               			mListener.onNoteSelected(null, "delete");
	               			break;
	               		default:
	               			Toast.makeText(context,"Option not ready yet", Toast.LENGTH_LONG).show();
	               }
	           }
	    });
	    return builder.create();
	}

	private Note getNote() {		
		return note;
	}
	
	public static void setNote(Note mNote)
	{
		note = mNote;
	}

	public static void setContext(Context mContext)
	{
		context = mContext;
	}
	
	private Context getContext() {
		return context;
	}
}
