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
import android.widget.Toast;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineListActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.databases.WineOpenHelper;

/**
 * Fragment for the page used to search for a wine by name
 * @author Javon Davis
 *
 */
public class SearchFragment extends Fragment implements ParcelKeys
{
	private Context mContext;
	private WineOpenHelper wineHelper;
	private String mColumnName;

	public static SearchFragment newInstance(final String columnName)
	{
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();

		args.putString(COLUMN_IDENTIFIER, columnName);
		
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		wineHelper = new WineOpenHelper(mContext);
		
		if(savedInstanceState==null)
			mColumnName = getArguments().getString(COLUMN_IDENTIFIER);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		final View rootView = inflater.inflate(R.layout.fragment_search, container,false);

		ArrayList<String> options; 
		
		final MultiAutoCompleteTextView wineSearch = (MultiAutoCompleteTextView) rootView.findViewById
				   (R.id.tImpl);
		
		switch(mColumnName)
		{
			case COLUMN_NAME:
				options = (ArrayList<String>) wineHelper.getAllWineNames();
				wineSearch.setHint(R.string.wineSearchHint);
				break;
			case COLUMN_BRAND:
				options = (ArrayList<String>) wineHelper.getAllBrands();
				wineSearch.setHint(R.string.wineBrandSearchHint);
				break;
			case COLUMN_COUNTRY:
				options = (ArrayList<String>) wineHelper.getAllCountries();
				wineSearch.setHint(R.string.wineCountrySearchHint);
				break;
			default:
				Toast.makeText(getActivity(), "Invalid column argument; ERROR: 404", Toast.LENGTH_LONG).show();
				options = (ArrayList<String>) wineHelper.getAllWineNames();
				break;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		   (mContext,android.R.layout.simple_list_item_1,options);
		
		
		
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
		                    
		                    //intent.putExtra(NAME_IDENTIFIER, query);
		                    intent.putExtra(COLUMN_IDENTIFIER, mColumnName);
		                    intent.putExtra(COLUMN_ARGUEMENT_IDENTIFIER, query);
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
				String query = (String) parent.getItemAtPosition(position);
    
                Intent intent = new Intent(mContext,WineListActivity.class);
                
                //intent.putExtra(NAME_IDENTIFIER, query);
                intent.putExtra(COLUMN_IDENTIFIER, mColumnName);
                intent.putExtra(COLUMN_ARGUEMENT_IDENTIFIER, query);
                wineSearch.getText().clear();
                startActivity(intent);
			}
		});
		wineSearch.setAdapter(adapter);

		wineSearch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		
		return rootView;
	}

}
