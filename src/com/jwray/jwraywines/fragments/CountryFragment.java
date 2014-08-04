package com.jwray.jwraywines.fragments;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.WineManager;

/**
 * Fragment for the Country search page
 * @author Javon Davis
 *
 */
public class CountryFragment extends Fragment implements ParcelKeys
{
	private Context mContext;
	
	//TODO check countries, i.e california
	private WineManager obj;
	
	@SuppressWarnings("unused")
	private MultiAutoCompleteTextView wineSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		obj = new WineManager(mContext);
	}

	
	public static CountryFragment newInstance()
	{
		return new CountryFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_search, container,false);
			
		if(rootView!=null)
		{
			
			ArrayList<String> countries = (ArrayList<String>) obj.getaAllCountries();
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>
					   (mContext,android.R.layout.simple_list_item_1,countries);
			
			final MultiAutoCompleteTextView wineSearch = (MultiAutoCompleteTextView) rootView.findViewById
					   (R.id.tImpl);
			
			wineSearch.setHint(R.string.wineCountrySearchHint);
			
			wineSearch.setOnTouchListener(new OnTouchListener() {
				
		        @SuppressLint("ClickableViewAccessibility")
				@Override
		        public boolean onTouch(View v, MotionEvent event) {
		            //final int DRAWABLE_LEFT = 0;
		            //final int DRAWABLE_TOP = 1;
		            final int DRAWABLE_RIGHT = 2;
		            //final int DRAWABLE_BOTTOM = 3;

		            if(event.getAction() == MotionEvent.ACTION_UP) {
		                if(event.getRawX()>= ((wineSearch.getRight() - wineSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))-50) {
		                	String query = wineSearch.getText().toString();
		                	
		                	if(!query.isEmpty())
		                	{
			                    Intent intent = new Intent(mContext,WineListActivity.class);
			                    
			                    
			                    intent.putExtra(COUNTRY_IDENTIFIER, query);
			                    wineSearch.getText().clear();
			                    startActivity(intent);
		                	}
		                	else 
		                	{
		                		new AlertDialog.Builder(mContext)
		                			.setTitle("Blank Search")
		                			.setMessage("Your search request was empty")
		                			.setIcon(android.R.drawable.ic_dialog_alert)
		                		     .show();
		                	}
		                }
		            }
		            return false;
		        }
		    });
			
			wineSearch.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String country = (String) parent.getItemAtPosition(position);
					
					Intent intent = new Intent(mContext,WineListActivity.class);
	                  
	                intent.putExtra(COUNTRY_IDENTIFIER, country);
	                wineSearch.getText().clear();
	                startActivity(intent);
				}
			});
			wineSearch.setAdapter(adapter);

			wineSearch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
			
		}
		
		return rootView;
	}
	
}
