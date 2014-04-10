package com.prashant.model;

public class AllSongsItem {
	private String title;
	private String duration;
	private String artist;
	
	public AllSongsItem(){}
	
	public AllSongsItem(String title,String duration,String artist)
	{
		this.title=title;
		this.duration=duration;
		this.artist=artist;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration=duration;
	}
	public String getArtist() {
		return this.artist;
	}

	public void setArtist(String artist) {
		this.artist=artist;
	}
	
	
	
	//Setting Up GetterSetter Methods
	
	
}

