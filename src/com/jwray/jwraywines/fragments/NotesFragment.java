package com.jwray.jwraywines.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NotesFragment extends Fragment {

	private Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();

	}
	
	public static Fragment newInstance() {
		
		return new NotesFragment();
	}

}
