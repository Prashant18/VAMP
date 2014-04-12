package com.prashant.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prashant.model.AllSongsItem;
import com.prashant.vamp.R;

public class AllSongsAdapter extends BaseAdapter {

	
	private Context context;
	private ArrayList<AllSongsItem> allSongsItems;
	
	public AllSongsAdapter(Context context,ArrayList<AllSongsItem> allSongsItems)
	{
		this.context=context;
		this.allSongsItems=allSongsItems;	
	}
	
	
	@Override
	public int getCount() {
		
		return allSongsItems.size();
	}

	@Override
	public Object getItem(int position) {
		
		return allSongsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null)
		{
			LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView=mInflater.inflate(R.layout.all_songs_list_each_row, null);
			Log.d("convertView","ConvertView is null");
		}
		
		TextView textTitle=(TextView) convertView.findViewById(R.id.all_songs_Text);
		textTitle.setText(allSongsItems.get(position).getTitle());
		
		
		TextView textDuration=(TextView) convertView.findViewById(R.id.duration);
		textDuration.setText(allSongsItems.get(position).getDuration());
		
		TextView textArtist=(TextView) convertView.findViewById(R.id.artit_details);
		textArtist.setText(allSongsItems.get(position).getArtist());
		
		Log.d("Return View","View Has been return");
		return convertView;
		
		
	}

}
