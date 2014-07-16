package com.jwray.jwraywines.classes;


public class Note {
	
	private String mTitle,mContent;
	private int mId; //used to match note to a wine
	private int identifier;
	
	public Note(String title,String content,int id)
	{
		setTitle(title);
		setContent(content);
		setId(id); 
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
	 * @return the mId
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setId(int mId) {
		this.mId = mId;
	}

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
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

}
