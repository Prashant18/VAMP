package com.prashant.vamp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.database.Cursor;
import android.media.MediaPlayer;
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
import com.prashant.converters.durationConverter;
import com.prashant.model.AllSongsItem;
import com.prashant.vamp.R;

public class AllSongsFragments extends Fragment {
	
	private AllSongsAdapter adapter;
	//private TypedArray allSongsIcon;
	private String[] allSongsTtitle;
	private String[] allSongsDuration;
	private String[] allSongsArtists;
	private ArrayList<AllSongsItem> list;
//	private Cursor csr;
	MediaPlayer nmMediaPlayer;
	final String TRACK_NAME=MediaStore.Audio.Media.TITLE;
	final String ALBUM_ID_=MediaStore.Audio.Albums.ALBUM_ID;
	Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	durationConverter dcr;
	public AllSongsFragments(){}
	
	
//	public Cursor getTrackCursor(Context context,Cursor cursor)
//	{
//		
//		ContentResolver csr=context.getContentResolver();
//		final String[] Coloumns={TRACK_NAME};
//		cursor=csr.query(uri, Coloumns,null, null, null);
//		if(cursor.moveToNext()){
//			int index=cursor.getColumnIndex(MediaColumns.TITLE);
//			String name=cursor.getString(index);
//			Log.d("AUDIO",name);
//		}
//		return cursor;		
//	}
//	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsatnceState){
		super.onCreateView(inflater, container, savedInsatnceState);
		View rootView =inflater.inflate(R.layout.fragment_all_songs, container,false);
		/* Getting data for Icon and Solg list*/
		//allSongsIcon=getResources().obtainTypedArray(R.array.top_icons_slidebar);
		//allSongsTtitle=getResources().getStringArray(R.array.top_list_slidebar);		
		list=new ArrayList<AllSongsItem>();
		String[] proj = { 
				MediaStore.Audio.Media.TITLE
				 };
	    @SuppressWarnings("deprecation")
		Cursor musiccursor = getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null,TRACK_NAME );
	    musiccursor.moveToFirst();
	    //int count=musiccursor.getCount();
	    List<String> temp=new ArrayList<String>();
	    List<String> temp_duration=new ArrayList<String>();
	    List<String> temp_artist=new ArrayList<String>();
	    while(musiccursor.moveToNext()){
	    	int index=musiccursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
	    	String data= musiccursor.getString(index);
	    	temp.add(data);
	    //	Log.d("TRACK",temp.toString());
	    	allSongsTtitle=temp.toArray(new String[0]);
	    }		
	    String[] proj_duration={
	    	MediaStore.Audio.Media.DURATION
	    };
	    
	    @SuppressWarnings("deprecation")
		Cursor duration_cursor=getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_duration, null, null, TRACK_NAME);
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
	    
	    
	    String[] proj_artist={
	    		MediaStore.Audio.Media.ARTIST
	    };
	    
	    @SuppressWarnings("deprecation")
		Cursor artist_cursor=getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_artist, null, null, TRACK_NAME);
	    artist_cursor.moveToFirst();
	    while(artist_cursor.moveToNext())
	    {
	    	int index_artist=artist_cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
	    	String data_artist=artist_cursor.getString(index_artist);
	    	temp_artist.add(data_artist);
	    	allSongsArtists=temp_artist.toArray(new String[0]); 	
	    }
	    
	    
		/*Iterating through List*/
		for(int i=0;i<allSongsTtitle.length;i++)
		{
			list.add(new AllSongsItem(allSongsTtitle[i],allSongsDuration[i],allSongsArtists[i]));
		}
		//allSongsIcon.recycle();
		adapter=new AllSongsAdapter(getActivity(), list);
		ListView lv=(ListView) rootView.findViewById(R.id.lv_all_songs);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				
				Toast tst=Toast.makeText(getActivity(), "TEXT SELECTED"+id, Toast.LENGTH_SHORT);
				tst.show();
			//	parent.setBackgroundResource(R.drawable.list_all_song_item_bg_pressed);
				
				
			}
			
			
			
			
		});
		return rootView;	
	}

}
