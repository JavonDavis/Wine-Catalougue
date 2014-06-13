package com.jwrayandnephew.winecatlog.content;

import java.util.ArrayList;

public abstract class WineBase 
{
	
	protected String name= null;
	protected String country= null;
	protected String description= null;
	protected double alcohol_level;
	protected ArrayList<String> awards = new ArrayList<String>();
	protected String wineOfOrigin= null;
	protected String servingSuggestion= null;
	protected String cellaringPotential= null;
	protected String maturation= null;
	protected String tastingNotes= null;
	protected String foodPairing= null;
	protected String winemakerNotes= null;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the alcohol_level
	 */
	public double getAlcohol_level() {
		return alcohol_level;
	}
	/**
	 * @param alcohol_level the alcohol_level to set
	 */
	public void setAlcohol_level(double alcohol_level) {
		this.alcohol_level = alcohol_level;
	}
	/**
	 * @return the awards
	 */
	public ArrayList<String> getAwards() {
		return awards;
	}
	/**
	 * @param awards the awards to set
	 */
	public void setAwards(ArrayList<String> awards) {
		this.awards = awards;
	}
	/**
	 * @return the wineOfOrgin
	 */
	public String getWineOfOrigin() {
		return wineOfOrigin;
	}
	/**
	 * @param wineOfOrgin the wineOfOrgin to set
	 */
	public void setWineOfOrigin(String wineOfOrgin) {
		this.wineOfOrigin = wineOfOrgin;
	}
	/**
	 * @return the servingSuggestion
	 */
	public String getServingSuggestion() {
		return servingSuggestion;
	}
	/**
	 * @param servingSuggestion the servingSuggestion to set
	 */
	public void setServingSuggestion(String servingSuggestion) {
		this.servingSuggestion = servingSuggestion;
	}
	/**
	 * @return the cellaringPotential
	 */
	public String getCellaringPotential() {
		return cellaringPotential;
	}
	/**
	 * @param cellaringPotential the cellaringPotential to set
	 */
	public void setCellaringPotential(String cellaringPotential) {
		this.cellaringPotential = cellaringPotential;
	}
	/**
	 * @return the maturation
	 */
	public String getMaturation() {
		return maturation;
	}
	/**
	 * @param maturation the maturation to set
	 */
	public void setMaturation(String maturation) {
		this.maturation = maturation;
	}
	/**
	 * @return the tastingNotes
	 */
	public String getTastingNotes() {
		return tastingNotes;
	}
	/**
	 * @param tastingNotes the tastingNotes to set
	 */
	public void setTastingNotes(String tastingNotes) {
		this.tastingNotes = tastingNotes;
	}
	/**
	 * @return the foodPairing
	 */
	public String getFoodPairing() {
		return foodPairing;
	}
	/**
	 * @param foodPairing the foodPairing to set
	 */
	public void setFoodPairing(String foodPairing) {
		this.foodPairing = foodPairing;
	}
	/**
	 * @return the winemakerNotes
	 */
	public String getWinemakerNotes() {
		return winemakerNotes;
	}
	/**
	 * @param winemakerNotes the winemakerNotes to set
	 */
	public void setWinemakerNotes(String winemakerNotes) {
		this.winemakerNotes = winemakerNotes;
	}

	
}
