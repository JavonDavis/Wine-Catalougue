package com.jwray.jwraywines.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.activities.WineInformationActivity;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.WineContract;

/**
 * Fragment for displaying a list of wines based on the intent passed @see ParcelKeys.java for key options
 * @author Javon Davis
 *
 */
public class WineListFragment extends Fragment implements ParcelKeys,LoaderManager.LoaderCallbacks<Cursor>
{
	private static final int LOADER = 0x01;
	private Context mContext;
	private String columnName,columnArgs;
	
	private SimpleCursorAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		
		columnName = getActivity().getIntent().getStringExtra(COLUMN_IDENTIFIER);
		columnArgs = getActivity().getIntent().getStringExtra(COLUMN_ARGUEMENT_IDENTIFIER);
	}

	
	public static WineListFragment newInstance()
	{
		return new WineListFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_wine_list, container,false);

		final ListView wineList = (ListView) rootView.findViewById(R.id.wine_list);
		
		String from[] = {COLUMN_NAME,COLUMN_COUNTRY,COLUMN_BRAND};
		
		int[] to = {R.id.wineName,R.id.wineCountry,R.id.wineBrand};
		
		adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.wine_list_item, null,
				from, to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		wineList.setAdapter(adapter);
		
		wineList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(mContext,WineInformationActivity.class);
				
				intent.putExtra(WINE_IDENTIFIER, id);
				startActivity(intent);
			}
			
		});
		
		getLoaderManager().restartLoader(LOADER, null, this);
		
		return rootView;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader;
		if(columnName!=null && columnArgs!=null)
			cursorLoader = new CursorLoader(getActivity(), WineContract.WINES_URI,allColumns,columnName + " LIKE ?",
					new String[]{"%"+columnArgs+"%"},null);
		else
			cursorLoader = new CursorLoader(getActivity(), WineContract.WINES_URI,allColumns,null,null,null);
		
		return cursorLoader;
	}


	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		adapter.swapCursor(arg1);
		if(adapter.getCount()==0)
			new AlertDialog.Builder(mContext)
				.setTitle("No Results")
				.setMessage("Your search returned no results")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((Activity) mContext).finish();
						
					}
				})
			     .show();
	}


	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
		
	}

}

