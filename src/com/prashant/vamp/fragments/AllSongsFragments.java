package com.prashant.vamp.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
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
	
	public AllSongsFragments(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsatnceState){
		super.onCreateView(inflater, container, savedInsatnceState);
		View rootView =inflater.inflate(R.layout.fragment_all_songs, container,false);
		/* Getting data for Icon and Solg list*/
		allSongsIcon=getResources().obtainTypedArray(R.array.top_icons_slidebar);
		allSongsTtitle=getResources().getStringArray(R.array.top_list_slidebar);
		list=new ArrayList<AllSongsItem>();
		System.gc();
		
		String Selection=MediaStore.Audio.Media.IS_MUSIC+"!=0";
		String[] projection={	
				MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.SIZE};
		
		
		/*Iterating through List*/
		for(int i=0;i<allSongsIcon.length();i++)
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
