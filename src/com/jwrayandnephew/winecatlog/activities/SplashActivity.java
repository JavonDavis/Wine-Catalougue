package com.jwrayandnephew.winecatlog.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jwrayandnephew.winecatlog.R;
import com.jwrayandnephew.winecatlog.content.WineContent;
import com.jwrayandnephew.winecatlog.database.DatabaseHandler;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		DatabaseHandler obj = new DatabaseHandler(this);
		
		if(obj.getAllWines().isEmpty())
			new WineContent().getWines(this);		
		else	
			startActivity(new Intent(this,WineListActivity.class));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

}
