package com.prashant.vamp.fragments;

import java.util.ArrayList;
import java.util.List;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.prashant.adapter.AllSongsAdapter;
import com.prashant.model.AllSongsItem;
import com.prashant.vamp.R;

public class AllArtistFragment extends Fragment {
	
	
	private AllSongsAdapter adapter;/*Adapter for AllSongs*/
	
	        /*Variables That Holdes Custom data for Custom ListView*/
	private String[] allSongsTtitle;
	private String[] allSongsDuration;
	private String[] allSongsArtists;
	
	private ArrayList<AllSongsItem> list_inflate;
            
	       /* Supportive Variables For Data MediaStore Class*/
		final String TRACK_NAME=MediaStore.Audio.Media.TITLE;
		final String ALBUM_ID_=MediaStore.Audio.Albums.ALBUM_ID;
		List<String> temp=new ArrayList<String>();
		List<String> temp_duration=new ArrayList<String>();
		List<String> temp_artist=new ArrayList<String>();
		
		/*URI FOR PARSING MEDIASTORE*/
		Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		
		
       /* Empty Fragment Constructor Must Be initialized if you get java.NullPointer error*/
	public AllArtistFragment(){}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsatnceState){
		
		
		/*Inflate Fragment View to frame Layout*/
		super.onCreateView(inflater, container, savedInsatnceState);
		View rootView =inflater.inflate(R.layout.fragment_all_songs, container,false);
		
		
		
		list_inflate=new ArrayList<AllSongsItem>();
		
		/* Getting data of Songs's Title From Media Store*/
		String[] proj = { 
				MediaStore.Audio.Media.TITLE
				 };
	  
		Cursor musiccursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null,TRACK_NAME );
	    musiccursor.moveToFirst();
	    while(musiccursor.moveToNext()){
	    	int index=musiccursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
	    	String data= musiccursor.getString(index);
	    	temp.add(data);
	    	allSongsTtitle=temp.toArray(new String[0]);
	    }		
	    
	    
	    /* Getting fata of song's duration from MediaStore (milliseconds)*/
	    String[] proj_duration={
	    	MediaStore.Audio.Media.DURATION
	    };
		Cursor duration_cursor=getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_duration, null, null, TRACK_NAME);
	    duration_cursor.moveToFirst();
	    while(duration_cursor.moveToNext())
	    {
	    	int index_duraion=duration_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
	    	String data_duration=duration_cursor.getString(index_duraion);
	    	Log.d("data_duration",data_duration);
	    	Long td=Long.parseLong(data_duration);
	    	String Seconds= String.valueOf((td%60000)/1000);
	    	String Minutes=String.valueOf(td/60000);
	    	String FIN;
	    	if(Seconds.length()==1)
	    	{
	    		FIN="0"+Minutes+":0"+Seconds;
	    	}else
	    	{
	    		FIN="0"+Minutes+":"+Seconds;
	    	}
	    	temp_duration.add(FIN);
	    	allSongsDuration=temp_duration.toArray(new String[0]);
	    }
	    
	    /* Getting Data from Song's Artist's from MediaStore*/
	    String[] proj_artist={
	    		MediaStore.Audio.Media.ARTIST
	    };
		Cursor artist_cursor=getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_artist, null, null, TRACK_NAME);
	    artist_cursor.moveToFirst();
	    while(artist_cursor.moveToNext())
	    {
	    	int index_artist=artist_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
	    	String data_artist=artist_cursor.getString(index_artist);
	    	temp_artist.add(data_artist);
	    	allSongsArtists=temp_artist.toArray(new String[0]); 	
	    }
	    
	    
		/* Iterating through all the instances of ListView*/
		for(int i=0;i<allSongsTtitle.length;i++)
		{
			list_inflate.add(new AllSongsItem(allSongsTtitle[i],allSongsDuration[i],allSongsArtists[i]));
		}
		//allSongsIcon.recycle();
		adapter=new AllSongsAdapter(getActivity(), list_inflate);
		ListView lv=(ListView) rootView.findViewById(R.id.lv_all_songs);
		/*Applying Adapter*/   
		                     lv.setAdapter(adapter);
		                     
		                     
		                     
		/* Applying OnClickListener method */
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				
				Toast tst=Toast.makeText(getActivity(), "TEXT SELECTED"+id, Toast.LENGTH_SHORT);
				tst.show();	
			}	
		});
		return rootView;	
	}

}
