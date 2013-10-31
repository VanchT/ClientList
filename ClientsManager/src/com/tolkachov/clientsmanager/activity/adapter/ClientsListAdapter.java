package com.tolkachov.clientsmanager.activity.adapter;

import java.util.List;

import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.model.BaseClientInfo;
import com.tolkachov.clientsmanager.viewmodel.ClientListItemBinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ClientsListAdapter extends BaseAdapter {

	private Context mContext;
	private List<BaseClientInfo> mItems; 
	
	public ClientsListAdapter(Context context, List<BaseClientInfo> items){
		super();
		this.mContext = context;
		this.mItems = items;
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mItems.get(position).getClientId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_client_list, parent);
		}
		ClientListItemBinder binder = null;
		if (convertView.getTag() == null) {
			binder = new ClientListItemBinder(convertView, mItems.get(position));
			binder.bind();
			convertView.setTag(binder);
		} else {
			binder = (ClientListItemBinder)convertView.getTag();
			binder.bind(mItems.get(position));
		}
		
		return convertView;
	}

}
