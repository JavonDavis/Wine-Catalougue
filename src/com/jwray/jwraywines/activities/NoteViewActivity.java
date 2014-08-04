package com.jwray.jwraywines.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.NotesManager;

/**
 * Activity for the purpose of viewing a note
 * @author Javon Davis
 *
 */
public class NoteViewActivity extends Activity implements ParcelKeys{

	NotesManager notesObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_view);

		notesObj = new NotesManager(this);
		
		TextView title = (TextView) findViewById(R.id.noteTitle);

		TextView content = (TextView) findViewById(R.id.noteDetails);
		
		int identifier = getIntent().getIntExtra(NOTE_IDENTIFITER,-1);
		
		if(identifier>0)
		{
			Note note = null;
			for (Note mNote : notesObj.getAllNotes())
				if(mNote.getIdentifier()==identifier)
				{
					note = mNote;
					break;
				}
			
			title.setText(note.getTitle());
			setTitle(note.getTitle()+"-"+note.getDateCreated());
			
			content.setText(note.getContent());
		}
		else
			title.setText("Could Not find note :(");
			
	}
}
