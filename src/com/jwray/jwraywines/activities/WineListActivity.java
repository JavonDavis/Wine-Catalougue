package com.jwray.jwraywines.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.fragments.WineListFragment;

public class WineListActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_list);
		
		getSupportFragmentManager().beginTransaction()
		.add(R.id.wine_container, new WineListFragment()).commit();
		
	}
}
