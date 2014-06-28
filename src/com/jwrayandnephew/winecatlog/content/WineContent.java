package com.jwrayandnephew.winecatlog.content;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class WineContent 
{	
	//Lists used to hold the wines and there names separately, the item in the lists must correspond to each other
	public static List<Wine> WINES = new ArrayList<Wine>();
	public static List<String> NAMES = new ArrayList<String>();
	
	//hashmap used to match a wines' name to itself 
	public static Map<String, Wine> WINE_MAP= new HashMap<String,Wine>();

	
	public void getWines(final Context context)
	{
		AsyncCaller caller = new AsyncCaller();
		caller.execute(context);
		
		try {
			caller.get();
		} catch (InterruptedException e) 
		{
			Toast.makeText(context, "AsyncCaller Interrupted Exception", Toast.LENGTH_LONG).show();
		} 
		catch (ExecutionException e) 
		{
			Toast.makeText(context, "AsyncCaller Execution Exception", Toast.LENGTH_LONG).show();
		}
	}
	
	public String removeBreaks(String string)
	{
		return string.replaceAll("[\\t\\n\\r]"," ");

	}
	
	private class AsyncCaller extends AsyncTask<Context,String,String>
	{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Context... params) {
			
			NAMES.clear();
			
			InputStream is = null;
	        StringBuilder sb=null;
	        String result=null;
	        Context context = params[0];
	        
	      //http post
	        try{
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://capeassistantbeta.eu5.org/test.php");
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	        }
	        catch(Exception e)
	        {
	            Log.e("log_tag", "Error in http connection"+e.toString());
	            Toast.makeText(context, "Internet Connection error", Toast.LENGTH_LONG).show();
	        }
	        
	        //convert response to string
	        try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            sb = new StringBuilder();
	            sb.append(reader.readLine() + "\n");
	            String line="0";
	          
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	             
	            is.close();
	            result=sb.toString();
	             
	        }
	        catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	            Toast.makeText(context, "Error converting result from server", Toast.LENGTH_LONG).show();
	        }
	        
	        
			
	      //paring data
	         @SuppressWarnings("unused")
			 int id=0;
	         
	         String name= null;
	    	 String country= null;
	    	 String description= null;
	    	 double alcohol_level = 0;
	    	 //ArrayList<String> awards = new ArrayList<String>();
	    	 String wineOfOrigin= null;
	    	 String servingSuggestion= null;
	    	 String cellaringPotential= null;
	    	 String maturation= null;
	    	 String tastingNotes= null;
	    	 String foodPairing= null;
	    	 String winemakerNotes= null;
	    	
	        try{
		        JSONArray jArray = new JSONArray(result);
		        JSONObject json_data=null;
		         
		        for(int i=0;i<jArray.length();i++){
		                json_data = jArray.getJSONObject(i);
		                
		                id=json_data.getInt("id");
		                name=json_data.getString("name");
		                country=json_data.getString("country");
		                alcohol_level=json_data.getDouble("alcohol_level");
		                description=json_data.getString("description");
		                wineOfOrigin=json_data.getString("wine_of_origin");
		                maturation=json_data.getString("maturation");
		                tastingNotes=json_data.getString("tasting_notes");
		                foodPairing=json_data.getString("food_pairing");
		                winemakerNotes=json_data.getString("winemaker_notes");
		                cellaringPotential=json_data.getString("cellaring_potential");
		                servingSuggestion=json_data.getString("serving_suggestion");
		                
		                name = removeBreaks(name);
		                country = removeBreaks(country);
		                description = removeBreaks(description);
		                tastingNotes = removeBreaks(tastingNotes);
		                servingSuggestion = removeBreaks(servingSuggestion);
		                wineOfOrigin = removeBreaks(wineOfOrigin);
		                cellaringPotential = removeBreaks(cellaringPotential);
		                foodPairing = removeBreaks(foodPairing);
		                winemakerNotes = removeBreaks(winemakerNotes);
		                maturation = removeBreaks(maturation);
			        	
			        	Wine wine = new Wine();
			        	wine.setName(name);
			        	wine.setCountry(country);
			        	wine.setDescription(description);
			        	wine.setAlcohol_level(alcohol_level);
			        	wine.setCellaringPotential(cellaringPotential);
			        	wine.setFoodPairing(foodPairing);
			        	wine.setMaturation(maturation);
			        	wine.setServingSuggestion(servingSuggestion);
			        	wine.setTastingNotes(tastingNotes);
			        	wine.setWinemakerNotes(winemakerNotes);
			        	wine.setWineOfOrigin(wineOfOrigin);
			        	
			        	String urldisplay = "http://capeassistantbeta.eu5.org/id"+wine.getId()+".png";
				        Bitmap bitmap = null;
				        try {
				            InputStream in = new java.net.URL(urldisplay).openStream();
				            bitmap = BitmapFactory.decodeStream(in);
				            in.close();
				        } catch (Exception e) {
				        	Toast.makeText(context, "Error Downloading Image for "+wine.getName(), Toast.LENGTH_LONG).show();
				        }
				        wine.setBitmap(bitmap);
			        	
			        	WINES.add(wine);
			        	NAMES.add(wine.getName());
			        	WINE_MAP.put(wine.getName(), wine);
			        		                
		        }
	         
	        }catch(JSONException e1){
	            Toast.makeText(context, "No wine Found", Toast.LENGTH_LONG).show();
	        }catch (ParseException e1){
	        	Toast.makeText(context, "Parse Exception Identified", Toast.LENGTH_LONG).show();
	        }
	        catch(NullPointerException e)
	        {
	        	Toast.makeText(context, "Null Pointer Exception", Toast.LENGTH_LONG).show();
	        }
	        
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
		
	}
}
