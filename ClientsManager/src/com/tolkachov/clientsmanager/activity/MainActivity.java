package com.tolkachov.clientsmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.tolkachov.clientsmanager.AppManager;
import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.activity.fragment.ClientsListFragment.ClientsFragmentListener;
import com.tolkachov.clientsmanager.util.Logger;

public class MainActivity extends Activity implements ClientsFragmentListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AppManager.init(getApplicationContext());
		Logger.init();
		Logger.setLogMode(true);
		
	}
	
	@Override
	protected void onDestroy() {
		AppManager.onStopApplication();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public void onStartLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinishLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClientSelected() {
		Intent intent = new Intent(MainActivity.this, ClientInfoActivity.class);
		startActivity(intent);
	}

}
