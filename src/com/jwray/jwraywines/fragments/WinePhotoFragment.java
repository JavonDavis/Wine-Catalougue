package com.jwray.jwraywines.fragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jwray.jwraywines.R;
import com.jwray.jwraywines.classes.ParcelKeys;
import com.jwray.jwraywines.classes.Wine;
import com.jwray.jwraywines.classes.WineContract;

/**
 * Fragment used to display the wine photo
 * @author Javon Davis
 *
 */
public class WinePhotoFragment extends Fragment implements ParcelKeys{

	private Wine aWine;
	private ImageView image;
	
	private LruCache<String, Bitmap> mMemoryCache;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Long _id = getActivity().getIntent().getLongExtra(WINE_IDENTIFIER,-1);
		
		Uri uri = Uri.withAppendedPath(WineContract.WINES_URI, "/"+_id);
		
		Cursor mCursor = getActivity().getContentResolver().query(uri, allColumns, null, null, null);
		aWine = WineContract.cursorToWine(getActivity(),mCursor);
		
		// Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 8;

	    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };

	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}
	
	public void loadBitmap(String key, ImageView mImageView) {
	    final String imageKey = key;

	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        mImageView.setImageBitmap(bitmap);
	    } else {
	        mImageView.setImageBitmap(null);
	        BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
	        task.execute(key);
	    }
	}
	
	public static Fragment newInstance() {

		return new WinePhotoFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = null;
		try{
			rootView = inflater.inflate(R.layout.fragment_wine_pics, container,false);
			assert(aWine!=null) : Log.d("null", "wine is null in photo fragment");
			
			if(aWine.getId()>0)
			{
				
				image = (ImageView) rootView.findViewById(R.id.wine_image);
				//image.setImageBitmap(aWine.getPicture(mContext));
				
				loadBitmap(aWine.getName(), image);
	
			}
		}
		catch(InflateException e)
		{
			Log.e("Wine Detail inflater", e.toString());
		}
		return rootView;
	}
	
	//=====================Private Classes=================================//
	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    
		private ImageView wineImageView;
	    public BitmapWorkerTask(ImageView mImageView) {
			wineImageView = mImageView;
		}

		@Override
	    protected Bitmap doInBackground(String... params) {
	        final Bitmap bitmap = aWine.getPicture(getActivity());
	        if(bitmap!=null)
	        {
		        addBitmapToMemoryCache(params[0], bitmap);
		        return bitmap;
	        }
	        return null;
	    }
		
		@Override
		protected void onPostExecute(Bitmap result) {
			wineImageView.setImageBitmap(result);
		}
	}
}
