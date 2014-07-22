package com.jwray.jwraywines.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jwray.jwraywines.R;

/**
 * 
 * @author Javon Davis
 *
 */
public class HomeFragment extends Fragment{

	private Context mContext;
	private ProgressBar recommendationProgress;
	private Button reccommend;
	public static Fragment newInstance() {
		return new HomeFragment();
	}

	public HomeFragment() {
		mContext = getActivity();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		
		if(rootView!=null)
		{
			recommendationProgress = (ProgressBar) rootView.findViewById(R.id.recommendationProgress);
			reccommend = (Button) rootView.findViewById(R.id.recommendButton);
		}
		return rootView;
	}
}

