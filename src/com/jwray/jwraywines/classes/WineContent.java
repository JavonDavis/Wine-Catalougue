package com.jwray.jwraywines.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jwray.jwraywines.classes.databases.WineManager;
import com.jwray.jwraywines.fragments.SearchFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;


public class WineContent 
{	
	//Lists used to hold the wines and there names separately, the item in the lists must correspond to each other
	public static List<Wine> WINES = new ArrayList<Wine>();
	public static List<String> NAMES = new ArrayList<String>();
	
	private Context context;
	//private ProgressBar progress;
	private ThreadControl control;
	private static ProgressDialog progress;
	
	//hashmap used to match a wines' name to itself 
	private static Map<String, Wine> WINE_MAP= new HashMap<String,Wine>();
	
	public void getWines(final Context mContext,ThreadControl mControl)
	{
		context = mContext;
		progress = new ProgressDialog(context);
		control = mControl;
		AsyncCaller caller = new AsyncCaller();
		caller.execute();
		
	}
	
	public String removeBreaks(String string)
	{
		return string.replaceAll("[\\t\\n\\r]"," ");

	}
	
	private class AsyncCaller extends AsyncTask<Context,String,String>
	{
		
		@Override
		protected void onProgressUpdate(String... values) {
			Log.e("prog", values.toString());
		}
		
		@Override
		protected void onPreExecute() {
			//progressDialog = ProgressDialog.show(context, "Please give me a moment", "Filling up cellar.", true, false);
			//((Activity) context).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
			//((Activity) context).setProgressBarIndeterminateVisibility(true);
			
			progress.setMessage("Fetching all the wines");
		    progress.setIndeterminate(true);
		    progress.setCancelable(false);
		    progress.show();
		}
		
		@Override
		protected String doInBackground(Context... params) {
			try {
				control.waitIfPaused();
			} catch (InterruptedException e2) {

				e2.printStackTrace();
			}
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
	            //Toast.makeText(context, "Error converting result from server", Toast.LENGTH_LONG).show();
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
	    	 String brand = null;
	    	
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
		                brand=json_data.getString("brand");
		                
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
		                brand = removeBreaks(brand);
			        	
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
			        	wine.setBrand(brand);
			        	
			        	
			        	//Got wines from the internet with this method
			        	/*
			        	String urldisplay = "http://capeassistantbeta.eu5.org/id"+wine.getId()+".png";
				        Bitmap bitmap = null;
				        try {
				            InputStream in = new java.net.URL(urldisplay).openStream();
				            bitmap = BitmapFactory.decodeStream(in);
				            in.close();
				        } catch (Exception e) {
				        	Log.e("imageError","Error Downloading Image for "+wine.getName());
				        }
				        wine.setBitmap(bitmap);
			        	*/
			        	
			        	
			        	WINES.add(wine);
			        	NAMES.add(wine.getName());
			        	WINE_MAP.put(wine.getName(), wine);
			        		                
		        }
	         
	        }catch(JSONException e1){
	            Log.e("No wine Found",e1.toString());
	        }catch (ParseException e1){
	        	Log.e("Parse Exception Identified", e1.toString());
	        }
	        catch(NullPointerException e)
	        {
	        	Log.e("Null Pointer Exception",e.toString());
	        }
	        
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			//Intent intent = new Intent(context,WineListActivity.class);
			WineManager obj = new WineManager(context);
			
	        for(Wine wine: WINES)
				obj.insert(wine);
	        
	        for(Wine wine : obj.getAllWines())
	        	Log.e("wine", wine.toString());

	       // context.startActivity(intent);
			//progressDialog.dismiss();
	        
	        if(progress.isShowing())
	            progress.dismiss();
	        
	        SearchFragment.resetFavorites();
	        
	        ((Activity) context).setProgressBarIndeterminateVisibility(false);
		}
		
	}
}
