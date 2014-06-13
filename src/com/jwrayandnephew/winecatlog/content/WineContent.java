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
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class WineContent 
{	
	public static List<Wine> WINES = new ArrayList<Wine>();
	public static List<String> NAMES = new ArrayList<String>();
	
	public static Map<String, Wine> WINE_MAP= new HashMap<String,Wine>();

	
	public void getWines(final Context context)
	{
		AsyncCaller caller = new AsyncCaller();
		caller.execute(context);
		
		try {
			caller.get();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
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
	        }
			
	      //paring data
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
	                
	                
	                Log.e("param1", id+"");
		        	Log.e("param2", name);
		        	Log.e("param3", alcohol_level+"");
		        	Log.e("param4", country);
		        	Log.e("param5", description);
		        	Log.e("param6", maturation);
		        	Log.e("param7", servingSuggestion);
		        	Log.e("param8", cellaringPotential);
		        	Log.e("param9", tastingNotes);
		        	Log.e("param10", winemakerNotes);
		        	Log.e("param11", foodPairing);
		        	Log.e("param12", wineOfOrigin);
		        	
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
		        	WINES.add(wine);
		        	NAMES.add(wine.getName());
		        	WINE_MAP.put(wine.getName(), wine);
	                
	        }
	         
	        }catch(JSONException e1){
	            Toast.makeText(params[0], "No wine Found", Toast.LENGTH_LONG).show();
	        }catch (ParseException e1){
	            e1.printStackTrace();
	        }
	        catch(NullPointerException e)
	        {
	        	e.printStackTrace();
	        }
	        
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
		
	}
}
