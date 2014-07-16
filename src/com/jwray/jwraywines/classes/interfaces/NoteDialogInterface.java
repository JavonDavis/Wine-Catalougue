package com.jwray.jwraywines.classes.interfaces;

import com.jwray.jwraywines.classes.Note;



/**
 * 
 * @author Javon
 *
 */
public interface NoteDialogInterface {
	void onNewNote(Note note, String key);
	void viewNote(Note note, String view);
}
