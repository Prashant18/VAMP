package com.prashant.vamp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prashant.adapter.AllSongsAdapter;
import com.prashant.converters.TimeConverter;
import com.prashant.model.AllSongsItem;
import com.prashant.player.playSong;
import com.prashant.vamp.MediaHandlerActivity;
import com.prashant.vamp.R;

public class AllSongsFragments extends Fragment {
	
	
	private AllSongsAdapter adapter;/*Adapter for AllSongs*/
	
	        /*Variables That Holdes Custom data for Custom ListView*/

	MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
	byte[] art;
	public TimeConverter timeConverter;
	private ArrayList<AllSongsItem> list_inflate;
	Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	List<String> title_list=new ArrayList<String>();
	List<String> duration_list=new ArrayList<String>();
	List<String> artist_list=new ArrayList<String>();
	List<String> data_list=new ArrayList<String>();
	List<String> Uri_list=new ArrayList<String>();
	public MediaHandlerActivity mediaHandlerActivity=new MediaHandlerActivity();
	
	public String[] allSongsTitle;
	public String[] allSongsDuration;
	public String[] allSongsArtists;
	public String[] allSongsData;
	public String[] allSongsUri;
		
	public playSong psg=new playSong();;
	MediaPlayer mp=new MediaPlayer();
	
		
       /* Empty Fragment Constructor Must Be initialized if you get java.NullPointer error*/
	public AllSongsFragments(){}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInsatnceState){
		/*Inflate Fragment View to frame Layout*/
		super.onCreateView(inflater, container, savedInsatnceState);
		View rootView =inflater.inflate(R.layout.fragment_all_songs, container,false);
		
		
		/* ImageView and buttons on the screen*/
		final ImageView art_bottom=(ImageView) rootView.findViewById(R.id.album_image);
		final TextView song_name_bottom=(TextView)rootView.findViewById(R.id.song_text);

		ImageView play,pause,next,prevoius;
		
		play=(ImageView) rootView.findViewById(R.id.button_play);
		next=(ImageView) rootView.findViewById(R.id.button_next);
		prevoius=(ImageView) rootView.findViewById(R.id.button_previous);
		list_inflate=new ArrayList<AllSongsItem>();
		String projection[]={
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.MediaColumns.DATA,
			
			
		};
		
		Cursor csr=getActivity().getContentResolver().query(uri, projection,  MediaStore.Audio.Media.IS_MUSIC + " = 1", null, MediaStore.Audio.Media.TITLE+" ASC");
		csr.moveToFirst();
		while(csr.moveToNext())
		{
			int title_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
			int duration_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
			int artist_id=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
			int data_id=csr.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
			
			
			
			String title=csr.getString(title_id);
			String duration=csr.getString(duration_id);
			String artist=csr.getString(artist_id);
			String data_path=csr.getString(data_id);
			
			Uri uri=MediaStore.Audio.Media.getContentUriForPath(data_path);
			
			String uri_songs=uri.toString();
			Log.d("msg", title+" "+duration+" "+artist+" "+data_path+" "+uri_songs);
			
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
			data_list.add(data_path);
			
			allSongsTitle=title_list.toArray(new String[0]);
			allSongsDuration=duration_list.toArray(new String[0]);
			allSongsArtists=artist_list.toArray(new String[0]);
			allSongsData=data_list.toArray(new String[0]);
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
				Toast tst=Toast.makeText(getActivity(),allSongsData[position], Toast.LENGTH_SHORT);
				tst.show();
				try{
				mediaHandlerActivity.playSongfromIndex(position);
				}
				catch(Exception e_ps)
				{
					Log.d("cant open class", "Unable to locate method");
				}
	
				mediaMetadataRetriever.setDataSource(allSongsData[position]);
					try{
					art=mediaMetadataRetriever.getEmbeddedPicture();
					Bitmap songImage=BitmapFactory.decodeByteArray(art, 0, art.length);
					art_bottom.setImageBitmap(songImage);
				}catch(Exception e)
				{
					art_bottom.setImageResource(R.drawable.ic_launcher);
					
				}
					try{						
						song_name_bottom.setText(allSongsTitle[position]);
						song_name_bottom.setSelected(true);
					}catch(Exception e2)
					{
						e2.printStackTrace();
						song_name_bottom.setText("FAILED TO SET TEXT");		
						
					}
						
		}	
		});
			
		return rootView;
	}	

}
