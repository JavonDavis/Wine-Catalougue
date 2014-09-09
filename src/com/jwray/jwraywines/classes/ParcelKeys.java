/**
 * 
 */
package com.jwray.jwraywines.classes;


/**
 * @author Javon Davis
 * Class used to govern and transport the general keys used throughout the application's life
 */
public interface ParcelKeys {
	static final String NAME_IDENTIFIER ="name";
	static final String WINE_IDENTIFIER = "id";
	static final String BRAND_IDENTIFIER = "brand";
	static final String COUNTRY_IDENTIFIER = "country";
	static final String NOTE_IDENTIFITER = "note_id";
	static final String TYPE_IDENTIFIER = "type";
	static final String MEAL_IDENTIFIER = "meal";
	static final String OCCASION_IDENTIFIER = "occasion";
	static final String COLUMN_IDENTIFIER = "column";
	static final String COLUMN_ARGUEMENT_IDENTIFIER = "column arguement";
	static final String COLUMN_ID ="_id";
	static final String COLUMN_WINE_ID ="ID";
	static final String COLUMN_NAME ="name";
	static final String COLUMN_DESCRIPTION ="description";
	static final String COLUMN_ALCOHOL_LEVEL ="alcohol_level";
	static final String COLUMN_MATURATION ="maturation";
	static final String COLUMN_TASTING_NOTES ="tasting_notes";
	static final String COLUMN_SERVING_SUGGESTION ="serving_suggestion";
	static final String COLUMN_CELLARING_POTENTIAL ="cellaring_potential";
	static final String COLUMN_FOOD_PAIRING ="food_pairing";
	static final String COLUMN_WINE_OF_ORIGIN ="wine_of_origin";
	static final String COLUMN_COUNTRY ="country";
	static final String COLUMN_WINEMAKER_NOTES ="winemaker_notes";
	static final String COLUMN_BRAND = "brand";
	static final String COLUMN_PRONOUNCIATION = "pronounciation";
	static final String COLUMN_MEAL = "meal";
	static final String COLUMN_OCCASION = "occasion";
	static final String COLUMN_TYPE = "type";
	
	static final int WINE_ID_IDENTIFIER = 1;
	
	static final String[] allColumns = {COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_COUNTRY,
			 COLUMN_TASTING_NOTES, COLUMN_MATURATION, COLUMN_FOOD_PAIRING, COLUMN_SERVING_SUGGESTION,
			 COLUMN_WINE_OF_ORIGIN, COLUMN_CELLARING_POTENTIAL, COLUMN_WINEMAKER_NOTES,
			 COLUMN_BRAND, COLUMN_ALCOHOL_LEVEL, COLUMN_PRONOUNCIATION, COLUMN_MEAL,
			 COLUMN_OCCASION, COLUMN_TYPE,COLUMN_WINE_ID};
	
	static final String WINE_TABLE_NAME = "wines";

	static final String PATH_WINE = "wine";
	static final String PATH_NOTE = "note";
	static final String PATH_FAVORITE = "favorite";
	
	
	
	/*============================================ Interfaces =================================================*/
	static interface NoteDialogInterface {
		void onNoteSelected(Note note, String key);
	}
	
	/**
	 * Interface for the keys for the home page options
	 * @author Javon Davis
	 *
	 */
	interface OptionNotifiers
	{
		static final String MEAL_TEXT ="Wine with a Meal";
		static final String TYPE_TEXT = "Wine by Type";
		static final String OCCASION_TEXT = "Wine for an Occasion";
		static final String TYPE_RED = "Red";
		static final String TYPE_WHITE = "White";
		static final String TYPE_ROSE = "Rose";
		static final String TYPE_SPARKLING = "Sparkling";
		static final String MEAT_CHICKEN = "Chicken";
		static final String MEAL_PASTA = "Pasta";
		static final String MEAL_PIZZA= "Pizza";
		static final String MEAT_PORK = "Pork";
		static final String MEAT_STEAK = "Steak";
		static final String MEAT_BEEF = "Beef";
		static final String MEAL_MEAT = "Meat";
		static final String MEAL_CHEESE = "Cheese";
		static final String MEAL_FRUIT = "Fruits";
		static final String MEAL_VEGGIES = "Vegetables";
		static final String MEAL_SEAFOOD = "Seafood";
		static final String OCCASION_ALL_TEXT = "All";
		static final String OCCASION_BDAY_TEXT = "Birthday/Anniversary";
		static final String OCCASION_COCKTAIL_TEXT = "Cocktails";
		static final String OCCASION_CHRISTMAS_TEXT = "Christmas";
		static final String OCCASION_DATE_TEXT = "Wine for a date";
		static final String RED_LIGHT = "Light";
		static final String RED_MEDIUM = "Medium";
		static final String RED_DARK = "Dark";
		static final String WHITE_DRY = "Dry";
		static final String WHITE_SWEET = "Sweet";
		static final String WHITE_RICH = "Rich";
		static final String GIFT_HOLIDAY= "Holiday Gift";
		static final int START_IDENTIFIER = 0;
		static final int HOME_IDENTIFIER = 1;
		static final int MEAL_IDENTIFIER = 2;
		static final int TYPE_IDENTIFIER = 3;
		static final int OCCASION_IDENTIFIER = 4;
		static final int WHITE_IDENTIFIER = 5;
		static final int RED_IDENTIFIER = 6;
		static final int MEAT_IDENTIFIER = 7;
		
	}
	
}
