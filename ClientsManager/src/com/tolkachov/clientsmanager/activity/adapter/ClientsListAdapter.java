package com.tolkachov.clientsmanager.activity.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.data.DatabaseHelper;
import com.tolkachov.clientsmanager.model.BaseClientInfo;
import com.tolkachov.clientsmanager.util.Logger;
import com.tolkachov.clientsmanager.viewmodel.ClientListItemBinder;

public class ClientsListAdapter extends CursorAdapter {


	public ClientsListAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ClientListItemBinder binder = null;
		BaseClientInfo clientInfo = BaseClientInfo.valueOf(cursor);
		if (view.getTag() == null) {
			binder = new ClientListItemBinder(view, clientInfo);
			binder.bind();
			view.setTag(binder);
		} else {
			binder = (ClientListItemBinder)view.getTag();
			binder.bind(clientInfo);
		}		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.item_client_list, parent, false);
	}
	
	@Override
	public long getItemId(int position) {
		Logger.d("", "ClientsListAdapter::getItemId()");
		Cursor cursor = getCursor();
		if (cursor == null) {
			return super.getItemId(position);
		} else {
			cursor.moveToPosition(position);
			long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID));
			return id;
		}
	}
	
	@Override
	public Object getItem(int position) {
		Cursor cursor = getCursor();
		cursor.moveToPosition(position);
		return BaseClientInfo.valueOf(cursor);
	}

}
