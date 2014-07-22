package com.jwray.jwraywines.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author Javon
 *
 */
public class Wine extends WineBase
{
	public static int idGen=0;
	
	private int id;
	private Bitmap bitmap; //TODO delete 
	private Drawable picture;
	private boolean favorite = false;
	
	public Wine() 
	{
		idGen++;
		setId(idGen);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the picture
	 */
	public Drawable getPicture(Context context) {
		
		String mDrawableName = "id"+getId();
    	int resID = context.getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());

    	try
    	{
    		picture = context.getResources().getDrawable(resID);
    		
    	}
    	catch(android.content.res.Resources.NotFoundException e)
    	{
    		Log.e("Resource not found stack", context.getPackageName());
    		Log.e("name", mDrawableName);
    		Log.e("wine name",getName());
    		Log.e("id", resID+"");
    	}
		return picture;
		
	}
	
	
	@Override
	public String toString()
	{
		String id = "ID:"+getId();
		String name ="name:"+getName();
		String brand = "brand:"+getBrand();
		String country = "country:"+getCountry();
		String image = "picture is null? "+(picture==null ? true:false);
		
		return id+"\n"+name+"\n"+brand+"\n"+country+"\n"+image;
	}

	/**
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 * @param favorite the favorite to set
	 */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}


}
