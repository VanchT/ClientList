package com.tolkachov.clientsmanager.viewmodel;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.tolkachov.clientsmanager.R;
import com.tolkachov.clientsmanager.model.StatusInfo;

public class StatusBinder extends ModelViewBinder<StatusInfo> {

	private CheckBox mMeState;
	private CheckBox mStatusState;	
	
	public StatusBinder(View view, StatusInfo statusInfo) {
		super(view, statusInfo);
		
		mMeState = (CheckBox)getView().findViewById(R.id.me_state);
		mStatusState = (CheckBox)getView().findViewById(R.id.status_state);
	}

	@Override
	public void bind() {
		
		//mMeState.setOnCheckedChangeListener(null);
		//mStatusState.setOnCheckedChangeListener(null);		
		
		mMeState.setChecked(getDataSource().isMine());
		mStatusState.setChecked(getDataSource().getStatusState());
		mStatusState.setText(getDataSource().getStatusName());
		
		mMeState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getDataSource().setMine(isChecked);
			}
		});
		
		mStatusState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getDataSource().setStatusState(isChecked);
			}
		});
		
	}

}
