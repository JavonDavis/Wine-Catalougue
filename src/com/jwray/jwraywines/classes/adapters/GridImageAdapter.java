package com.jwray.jwraywines.classes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GridImageAdapter extends BaseAdapter{

	private Context mContext;
	
	
	private Integer[] mIds = null;
	
	public GridImageAdapter(Context context, Integer[] IDs) {
		mContext = context;
		mIds = IDs;
	}
	
	@Override
	public int getCount() {
		
		return mIds.length;
	}

	@Override
	public Object getItem(int position) {
		
		return mIds[position];
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LinearLayout layout = new LinearLayout(mContext);
		int id = (int) getItem(position);

		layout.setGravity(Gravity.CENTER);
		layout.setPadding(10, 10, 10, 10);

		ImageView imageView = new ImageView(mContext);

		imageView.setId(id);
		
		Drawable wine_image = mContext.getResources().getDrawable(id);
		imageView.setImageDrawable(wine_image);

		layout.addView(imageView);
		
		return layout;
	}

}
