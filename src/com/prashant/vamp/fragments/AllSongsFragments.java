package com.prashant.vamp.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prashant.adapter.AllSongsAdapter;
import com.prashant.converters.TimeConverter;
import com.prashant.model.AllSongsItem;
import com.prashant.vamp.R;

public class AllSongsFragments extends Fragment {
	
	
	private AllSongsAdapter adapter;/*Adapter for AllSongs*/
	
	        /*Variables That Holdes Custom data for Custom ListView*/
	
	
	public TimeConverter timeConverter;
	private ArrayList<AllSongsItem> list_inflate;
	Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	List<String> title_list=new ArrayList<String>();
	List<String> duration_list=new ArrayList<String>();
	List<String> artist_list=new ArrayList<String>();
	
	public String[] allSongsTitle;
	public String[] allSongsDuration;
	public String[] allSongsArtists;
		
		
       /* Empty Fragment Constructor Must Be initialized if you get java.NullPointer error*/
	public AllSongsFragments(){}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsatnceState){
		
		
		/*Inflate Fragment View to frame Layout*/
		super.onCreateView(inflater, container, savedInsatnceState);
		View rootView =inflater.inflate(R.layout.fragment_all_songs, container,false);
		list_inflate=new ArrayList<AllSongsItem>();
		
		
		String projection[]={
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.ALBUM,
			
		};
		
		Cursor csr=getActivity().getContentResolver().query(uri, projection, null, null, MediaStore.Audio.Media.TITLE);
		csr.moveToFirst();
		while(csr.moveToNext())
		{
			int title_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
			int duration_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
			int artist_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
			
			String title=csr.getString(title_id);
			String duration=csr.getString(duration_id);
			String artist=csr.getString(artist_id);
			
			Log.d("msg", title+" "+duration+" "+artist);
			
			Long time_duration=Long.parseLong(duration);
			String Seconds= String.valueOf((time_duration%60000)/1000);
	    	String Minutes=String.valueOf(time_duration/60000);
			String FIN;
			if(Seconds.length()==1)
	    	{
	    		FIN="0"+Minutes+":0"+Seconds;
	    	}else
	    	{
	    		FIN="0"+Minutes+":"+Seconds;
	    	}
			
			
			title_list.add(title);
			duration_list.add(FIN);
			artist_list.add(artist);
			
			allSongsTitle=title_list.toArray(new String[0]);
			allSongsDuration=duration_list.toArray(new String[0]);
			allSongsArtists=artist_list.toArray(new String[0]);
			
		}   
		csr.close();
		/* Iterating through all the instances of ListView*/
		for(int i=0;i<allSongsTitle.length;i++)
		{
			list_inflate.add(new AllSongsItem(allSongsTitle[i],allSongsDuration[i],allSongsArtists[i]));
		}
		adapter=new AllSongsAdapter(getActivity(), list_inflate);
		ListView lv=(ListView) rootView.findViewById(R.id.lv_all_songs);   
        lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				Toast tst=Toast.makeText(getActivity(),allSongsDuration[position]+" "+allSongsArtists[position], Toast.LENGTH_SHORT);
				tst.show();
						
		}	
		});
		return rootView;	
	}

}
