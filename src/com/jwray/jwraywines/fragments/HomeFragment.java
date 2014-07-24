package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jwray.jwraywines.R;

/**
 * 
 * @author Javon Davis
 *
 */
public class HomeFragment extends Fragment{

	private Context mContext;
	private ListView options;
	
	private final static List<String> optionSet = new ArrayList<String>();
	
	public static Fragment newInstance() {
		return new HomeFragment();
	}

	public HomeFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}
	
	public void refreshList(int code)
	{
		optionSet.clear();
		switch(code)
		{
			case 1:
				optionSet.add("Wine  for a meal");
				optionSet.add("Wine by Type");
				optionSet.add("Wine for an Occasion");
				break;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		
		if(rootView!=null)
		{
			options = (ListView) rootView.findViewById(R.id.optionsList);
			refreshList(1);//give meaning 
			options.setAdapter(new HomeOptionAdapter(mContext, android.R.layout.simple_list_item_1, optionSet));
			
		}
		return rootView;
	}
	
	/*==========================Private Classes=============================================*/
	
	private class HomeOptionAdapter extends ArrayAdapter<String>
	{

		public HomeOptionAdapter(Context context, int resource) {
			super(context, resource);
		}
		
		public HomeOptionAdapter(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			
			if (view == null) {

		        LayoutInflater inflater;
		        inflater = LayoutInflater.from(getContext());
		        view = inflater.inflate(R.layout.home_option_item, null);

		    }
			
			String option = getItem(position);
			
			if(option!= null)
			{
				TextView item = (TextView) view.findViewById(R.id.option);
				item.setText(option);
			}
			
			return view;
		}
		
	}
}

