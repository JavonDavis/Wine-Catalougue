package com.jwray.jwraywines.classes;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwray.jwraywines.R;

public class WineAdapter extends ArrayAdapter<Wine> {
	
	Context mContext;
	public WineAdapter(Context context, int resource) {
		super(context, resource);
		mContext = context;
	}
	
	public WineAdapter(Context context, int resource, List<Wine> wines) {
	    super(context, resource, wines);
	    mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {

	        LayoutInflater inflater;
	        inflater = LayoutInflater.from(getContext());
	        view = inflater.inflate(R.layout.wine_list_item, null);

	    }
		
		Wine wine = getItem(position);
		
		if(wine!= null)
		{
			ImageView wineImage = (ImageView) view.findViewById(R.id.wineView);
			TextView wineName = (TextView) view.findViewById(R.id.wineName);
			TextView wineDescription = (TextView) view.findViewById(R.id.wineDescription);
			
			wineImage.setVisibility(View.GONE);
			//TODO delete the imageview 
			
			wineName.setText(wine.getName());
			wineDescription.setText("Country:"+wine.getCountry()+"\nBrand:"+wine.getBrand());
		}
		
		return view;
	}
}
