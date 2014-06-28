package com.jwrayandnephew.winecatlog.content;

import android.graphics.Bitmap;

public class Wine extends WineBase 
{
	public static int idGen=0;
	
	private int id;
	private Bitmap bitmap;
	
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

}
