package com.jwrayandnephew.winecatlog.content;

public class Wine extends WineBase 
{
	public static int idGen=0;
	
	private int id;
	
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

}
