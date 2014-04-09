package com.prashant.vamp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.prashant.adapter.AllSongsAdapter;
import com.prashant.model.AllSongsItem;
import com.prashant.vamp.R;

public class AllSongsFragments extends Fragment {
	
	private AllSongsAdapter adapter;
	private TypedArray allSongsIcon;
	private String[] allSongsTtitle;
	private ArrayList<AllSongsItem> list;
	private Cursor csr;
	MediaPlayer nmMediaPlayer;
	final String TRACK_NAME=MediaStore.Audio.Media.TITLE;
	final String ALBUM_ID_=MediaStore.Audio.Albums.ALBUM_ID;
	Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
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
		allSongsIcon=getResources().obtainTypedArray(R.array.top_icons_slidebar);
		//allSongsTtitle=getResources().getStringArray(R.array.top_list_slidebar);		
		list=new ArrayList<AllSongsItem>();
		String[] proj = { 
				MediaStore.Audio.Media.DISPLAY_NAME,
				 };
	    @SuppressWarnings("deprecation")
		Cursor musiccursor = getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null); 
	    int count=musiccursor.getCount();
	    List<String> temp=new ArrayList<String>();
	    while(musiccursor.moveToNext()){
	    	int index=musiccursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
	    	String data= musiccursor.getString(index);
	    	temp.add(data);
	    	Log.d("TRACK","allSongsIcon");
	    	allSongsTtitle=temp.toArray(new String[0]);
	    }
	    
	    String proj_icon[]={
	    		MediaStore.Audio.Albums.ALBUM_ART
	    };
	    
	    Cursor musicIcon=getActivity().managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, proj_icon, MediaStore.Audio.Albums._ID+"=?", null, null);
	    
	    while(musiccursor.moveToNext())
	    {
	    	 long albumId = musicIcon.getLong(musicIcon.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
	    	final Uri ART_CONTENT_URI=Uri.parse("content://media/external/audio/albumart");
	    	Uri albumArtUri=ContentUris.withAppendedId(ART_CONTENT_URI,albumId);
	    	
	    	 Bitmap bitmap = null;
	         try {
	             bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), albumArtUri);
	         } catch (Exception exception) {
	             // log error
	         }
	    	
	    }
		
		/*Iterating through List*/
		for(int i=0;i<allSongsTtitle.length;i++)
		{
			list.add(new AllSongsItem(allSongsIcon.getResourceId(i, -1), allSongsTtitle[i]));
		}
		allSongsIcon.recycle();
		adapter=new AllSongsAdapter(getActivity(), list);
		ListView lv=(ListView) rootView.findViewById(R.id.lv_all_songs);
		lv.setAdapter(adapter);
		return rootView;	
	}

}
