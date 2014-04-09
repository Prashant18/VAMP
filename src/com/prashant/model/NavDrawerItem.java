package com.prashant.model;


//Create variable for icons and String title, Take icon as an int data.
public class NavDrawerItem {
	private String title;
	private int icon;
	
	//creating constructors
	public NavDrawerItem(){}
	
	public NavDrawerItem(int icon,String title){
		
		this.icon=icon;
		this.setTitle(title);
	}
	
	//Setting up Getter-Setter Methods for data manipulation and retrivation process
	
	public String getTitle()
	{
		return this.title;
	}
	
	public int getIcon()
	{
		return this.icon;
	}
	
	
	public void setIcon(int icon)
	{
		this.icon=icon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
