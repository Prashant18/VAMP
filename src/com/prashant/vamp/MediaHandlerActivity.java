package com.prashant.vamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;

public class MediaHandlerActivity extends Activity {

	MediaPlayer mp=new MediaPlayer();;
	
	 Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	 String[] projection={
			 
			 MediaStore.Audio.Media.DATA
	 };
	 List<String> path_list=new ArrayList<String>();
	 String[] paths;
	 Cursor csr=this.getContentResolver().query(uri, projection, null, null, MediaStore.Audio.Media.TITLE);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 while(csr.moveToNext()){
			 int path_index=csr.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
			 String path_string=csr.getString(path_index);
			 path_list.add(path_string);
			 paths=path_list.toArray(new String[0]);
		 }
	}
	public void playSongfromIndex(int index){

		 try {
	            mp.reset();
	            mp.setDataSource(paths[index]);
	            mp.prepare();
	            mp.start();
		
		 
			} catch (IllegalArgumentException e) {
					e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
								}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media_handler, menu);
		return true;
	}

}
