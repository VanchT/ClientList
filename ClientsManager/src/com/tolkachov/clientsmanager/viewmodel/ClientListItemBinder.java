package com.tolkachov.clientsmanager.viewmodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.model.BaseClientInfo;
import com.tolkachov.clientsmanager.util.Util;
import com.tolkachov.clientsmanager.widget.LinearIndicator;

public class ClientListItemBinder extends ModelViewBinder<BaseClientInfo> {

	private ImageView mIndicatorPhone;
	private ImageView mIndicatorBirthday;
	private ImageView mIndicatorEvent;
	private LinearIndicator mClientStatus;
	private ImageView mClientPhoto;
	private TextView mClientName;
	private TextView mClientRelation;
	
	
	public ClientListItemBinder(View view, BaseClientInfo dataSource) {
		super(view, dataSource);
		mIndicatorPhone = (ImageView)getView().findViewById(R.id.client_phone_indicator);
		mIndicatorBirthday = (ImageView)getView().findViewById(R.id.client_birthday_indicator);
		mIndicatorEvent = (ImageView)getView().findViewById(R.id.client_event_indicator);
		mClientStatus = (LinearIndicator)getView().findViewById(R.id.client_status_indicator);
		mClientPhoto = (ImageView)getView().findViewById(R.id.client_photo);
		mClientName = (TextView)getView().findViewById(R.id.client_name);
		mClientRelation = (TextView)getView().findViewById(R.id.client_relation_type);
		
		mIndicatorPhone.setVisibility(View.GONE);
		mIndicatorBirthday.setVisibility(View.GONE);
		mIndicatorEvent.setVisibility(View.GONE);
	}

	@Override
	public void bind() {
		BaseClientInfo client = getDataSource();
		mClientName.setText(client.getClientName());
		mClientRelation.setText(client.getRelationType());
		updateStatusIndicator();
		if (!Util.isNullOrEmpty(client.getPhotoUrl())) {
			//TODO: Not implemented yet
		}
		
		if (Util.compareByManthAndDay(client.getBirthday())){
			mIndicatorBirthday.setVisibility(View.VISIBLE);
		}
		
		client.setOnStatusChangeListener(new BaseClientInfo.OnStatusChangeListener() {
			
			@Override
			public void onStatusChange(String newstatus) {
				updateStatusIndicator();				
			}
		});
	}
	
	private void updateStatusIndicator(){
		String[] statuses = getDataSource().getStatus().split(",");
		for (int i = 0; i < statuses.length; i++){
			String[] status = statuses[i].split(":");
			if (Boolean.getBoolean(status[0])){
				mClientStatus.setColumnImage(i + 1, R.drawable.bg_status_indicator_no);
			} else {
				if (Boolean.getBoolean(status[1])){
					mClientStatus.setColumnImage(i + 1, R.drawable.img_status_indicator_green);
				} else {
					mClientStatus.setColumnImage(i + 1, R.drawable.img_status_indicator_red);
				}
			}
		}
	}

}
