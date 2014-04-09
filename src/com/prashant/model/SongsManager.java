package com.prashant.model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
	
	final String MEDIA_PATH=new String("/sdcard/");
	private ArrayList<HashMap<String,String>> songsList=new ArrayList<HashMap<String,String>>();
	
	public SongsManager(){}
	
	public ArrayList<HashMap<String,String>> getPlayList()
	{
		
		File home=new File(MEDIA_PATH);
		if(home.listFiles(new FileExtentionFilter()).length>0)
		{
			for(File file:home.listFiles(new FileExtentionFilter()))
			{
				HashMap<String,String> songs=new HashMap<String, String>();
				songs.put("SongTitle",file.getName().substring(0,(file.getName().length()-4)));
				songs.put("SongPath", file.getPath());
				songsList.add(songs);
			}
		}
		return songsList;
	}

}

class FileExtentionFilter implements FilenameFilter{
	public boolean accept(File dir,String name)
	{
		return (name.endsWith(".mp3")||name.endsWith(".MP3"));
	}
}
