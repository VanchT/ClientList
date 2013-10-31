package com.tolkachov.clientsmanager.activity.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tolkachov.clientsmanager.AppManager;
import com.tolkachov.clientsmanager.R;

public class ClientsListFragment extends Fragment {

	private ListView mList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_clients_list, container, false);
	}	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mList = (ListView)getView().findViewById(android.R.id.list);
		updateList();
	}
	
	private void updateList(){
		//TODO: Not implemented yet
		Cursor clients = AppManager.getDatabaseWorker().loadClientsList(null, null);
	}
	
}
