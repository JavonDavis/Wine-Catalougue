package com.jwray.jwraywines.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Class to manage the process of favoriting a wine
 * @author Javon
 *
 */
public class FavoriteManager {
	
	private static Set<String> favoriteIdSet = new HashSet<String>();
	private static List<String> ids = new ArrayList<String>();
	private final static String FAVORITE_IDENTIFIER = "favorites";
	
	public static void addToFavorites(int wineID,Context context)
	{
		ids.add(Integer.toString(wineID));
		refreshPrefs(context);
	}
	
	public static List<Integer> getFavorites(Context context)
	{
		SharedPreferences prefs = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
		Set<String> idSet = prefs.getStringSet(FAVORITE_IDENTIFIER, null);
		List<Integer> tempList = new ArrayList<Integer>();
		if(idSet!=null)
		{
			String[] idArray = (String[]) idSet.toArray();
			for(String string : idArray)
			{
				ids.add(string);
				try
				{
					int id = Integer.parseInt(string);
					tempList.add(id);
				}
				catch(java.lang.NumberFormatException e )
				{
					Log.e("prefs error",e+"|"+string);
				}
			}
		}
		return tempList;
	}

	public static boolean removeFavorite(Context context,int wineID)
	{
		boolean success = ids.remove(Integer.toString(wineID));
		refreshPrefs(context);
		return success;
	}
	
	private static void refreshPrefs(Context context) {
		SharedPreferences prefs = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		favoriteIdSet.addAll(ids);
		
		editor.putStringSet(FAVORITE_IDENTIFIER, favoriteIdSet);
		editor.commit();
		
	}

}
