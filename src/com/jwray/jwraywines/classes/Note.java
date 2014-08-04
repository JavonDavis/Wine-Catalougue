package com.jwray.jwraywines.classes;

/**
 * Class structure for a Note
 * @author Javon Davis
 *
 */
public class Note {
	
	private String mTitle,mContent;
	private int mId; //used to match note to a wine
	private int identifier;
	private String DateCreated;
	
	public Note(String title,String content,int id,String date)
	{
		setTitle(title);
		setContent(content);
		setId(id);
		setDateCreated(date);
	}

	/**
	 * @return the mTitle
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * @param mTitle the mTitle to set
	 */
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	/**
	 * @return the mContent
	 */
	public String getContent() {
		return mContent;
	}

	/**
	 * @param mContent the mContent to set
	 */
	public void setContent(String mContent) {
		this.mContent = mContent;
	}

	/**
	 * @return the wine ID of this note
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @param (int) the wine ID for this note
	 */
	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * @return the unique identifier for the note
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param (int) the unique identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public String toString()
	{
		String title = "Title:"+getTitle();
		String wineId = "Wine ID:"+getId();
		String id = "ID:"+getIdentifier();
		
		return title+"\n"+wineId+"\n"+id;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return DateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		DateCreated = dateCreated;
	}

}
