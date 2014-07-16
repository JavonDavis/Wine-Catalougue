package com.jwray.jwraywines.classes.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.Note;

public class NoteAdapter extends ArrayAdapter<Note> {

	@SuppressWarnings("unused")
	private Context mContext;
	
	public NoteAdapter(Context context, int resource) {
		super(context, resource);
		mContext = context;
	}
	
	public NoteAdapter(Context context, int resource, List<Note> notes) {
	    super(context, resource, notes);
	    mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {

	        LayoutInflater inflater;
	        inflater = LayoutInflater.from(getContext());
	        view = inflater.inflate(R.layout.note_item, null);

	    }
		
		Note note = getItem(position);
		
		if(note!=null)
		{
			TextView titleText = (TextView) view.findViewById(R.id.titleView);
			TextView contentText = (TextView) view.findViewById(R.id.contentPortion);
			TextView dateText = (TextView) view.findViewById(R.id.dateView);
			
			titleText.setText(note.getTitle());
			
			String content = note.getContent();
			
			int portionStop = content.length() > 200 ? 80:content.length()<26 ? content.length():50;
			Log.d("content", content.length()+"");
			String portion = content.substring(0,portionStop)+"...";
			contentText.setText(portion);
			
			dateText.setText("date posted to go here");
		}
		return view;
	}
}
