package com.tolkachov.clientsmanager.activity.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;

import com.tolkachov.clientsmanager.AppManager;
import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.activity.adapter.ClientsListAdapter;
import com.tolkachov.clientsmanager.model.BaseClientInfo;
import com.tolkachov.clientsmanager.model.ClientInfo;

public class ClientsListFragment extends ListFragment implements LoaderCallbacks<Cursor>, OnQueryTextListener {

	private ClientsFragmentListener mFragmentListener;
	private ClientsListAdapter mAdapter;
	private String mCurFilter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mFragmentListener = (ClientsFragmentListener)activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_clients_list, container, false);
	}	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fillTestData();
	}
		
	private void fillTestData(){
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				for (int i = 1; i <= 20; i++){
					String relationType = "Холодный";
					if (i % 2 == 0) relationType = "Знакомый";
					BaseClientInfo clientInfo = new BaseClientInfo(-1,
							"Client " + String.valueOf(i), null, "11/1/1988", null, relationType);
					AppManager.getDatabaseWorker().addClient(new ClientInfo(clientInfo, null, null));
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				updateData();
			};
			
		}.execute();
		
	}

	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle params) {
		return new CursorLoader(getActivity()){
			@Override
			public Cursor loadInBackground() {
				Cursor cursor = AppManager.getDatabaseWorker().loadClientsList(null, null);
				return cursor;
			}
		};
	}
	

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor newCursor) {
		mAdapter.swapCursor(newCursor);
	}
	

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void updateData(){
		mAdapter = new ClientsListAdapter(getActivity(), null);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(AppManager.getActualLoaderId(), null, this);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ClientsListAdapter adapter = (ClientsListAdapter)l.getAdapter();
		ClientInfo clientInfo = new ClientInfo((BaseClientInfo)adapter.getItem(position), null, null);
		AppManager.setCurrentSelectedClient(clientInfo);
		mFragmentListener.onClientSelected();
	}
	
	public interface ClientsFragmentListener extends FragmentBaseListener {
		
		public void onClientSelected();
		
	}
	
}
