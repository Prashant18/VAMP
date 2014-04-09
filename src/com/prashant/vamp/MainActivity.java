package com.prashant.vamp;

import java.util.ArrayList;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.prashant.adapter.NavDrawerListAdapter;
import com.prashant.model.NavDrawerItem;
import com.prashant.vamp.fragments.AllSongsFragments;

public class MainActivity extends Activity {

	private DrawerLayout mdrawerLayout;
	private ListView mDrawerList;
	
	//Actionbar header Button toogle
	//Only supported after api level 14 but can beimplemented using support librabries.
	private ActionBarDrawerToggle mdrawerToogle;
	
	//Navigation DrawerTitle (Artist,Allsong,etc..) you can use String class too.
	private CharSequence mDrawerTitle;
	
	//Used to store App title you can youse String class too.
	private CharSequence mTitle;
	
	//SliderMenu Items
	private String[] navMenuTitle;
	private TypedArray navMenuIcons;
	
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		mTitle=mDrawerTitle=getTitle();
		navMenuTitle=getResources().getStringArray(R.array.top_list_slidebar);
		navMenuIcons=getResources().obtainTypedArray(R.array.top_icons_slidebar);
		mdrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList=(ListView) findViewById(R.id.list_slidebar);
		navDrawerItems=new ArrayList<NavDrawerItem>();
		
		for(int i=0;i<navMenuTitle.length;i++)
		{
			navDrawerItems.add(new NavDrawerItem(navMenuIcons.getResourceId(i, -1),navMenuTitle[i]));
		}
		
	navMenuIcons.recycle();	
	adapter=new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
	mDrawerList.setAdapter(adapter);
	mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	
	getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setHomeButtonEnabled(true);
		
	mdrawerToogle=new ActionBarDrawerToggle(this, mdrawerLayout, R.drawable.ic_drawer, R.string.app_name,R.string.app_name)
		{
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
            invalidateOptionsMenu();
						}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
            invalidateOptionsMenu();
						}
		
		};
		 mdrawerLayout.setDrawerListener(mdrawerToogle);
		 
		 if(savedInstanceState==null)
		 {
			 displayView(0);
		 }
		 
	}

	 private class SlideMenuClickListener implements
     ListView.OnItemClickListener {
 @Override
 public void onItemClick(AdapterView<?> parent, View view, int position,
         long id) {
     // display view for selected nav drawer item
     displayView(position);
 }
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		if(mdrawerToogle.onOptionsItemSelected(item)){
			return true;	
		}
		switch(item.getItemId())
		{
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	  @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // if nav drawer is opened, hide the action items
	        boolean drawerOpen = mdrawerLayout.isDrawerOpen(mDrawerList);
	        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
	        return super.onPrepareOptionsMenu(menu);
	    }
	

		private void displayView(int position) {
			
			Fragment fragment=null;
			switch (position)
			{
			case 0:
				{
					fragment=new AllSongsFragments();
					break;
				}
				default:
				{
					break;
				}
			}
			 if (fragment != null) {
		            FragmentManager fragmentManager = getFragmentManager();
		            fragmentManager.beginTransaction()
		                    .replace(R.id.frame_container, fragment).commit();
		 
		            // update selected item and title, then close the drawer
		            mDrawerList.setItemChecked(position, true);
		            mDrawerList.setSelection(position);
		            setTitle(navMenuTitle[position]);
		            mdrawerLayout.closeDrawer(mDrawerList);
		        } else {
		            // error in creating fragment
		            Log.e("MainActivity", "Error in creating fragment");
		        }
			
		}
	  @Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	        getActionBar().setTitle(mTitle);
	    }
	  
	  
	  @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mdrawerToogle.syncState();
	    }
	 
	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggles
	        mdrawerToogle.onConfigurationChanged(newConfig);
	    }
	  
}
