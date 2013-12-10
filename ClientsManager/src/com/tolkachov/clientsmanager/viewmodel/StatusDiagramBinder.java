package com.tolkachov.clientsmanager.viewmodel;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.tolkachov.clientsmanager.model.StatusInfo;

public class StatusDiagramBinder extends ModelViewBinder<List<StatusInfo>> {

	private Context mContext;
	
	protected StatusDiagramBinder(View view, List<StatusInfo> dataSource) {
		super(view, dataSource);
	}
	
	public StatusDiagramBinder(Context context, View view, List<StatusInfo> dataSource){
		super(view, dataSource);
		this.mContext = context;
	}

	@Override
	public void bind() {
		final int viewHeight = getView().getHeight();
		int sumWeight = 0;
		for (int i = 0; i < getDataSource().size(); i++) {
			ImageView line = new ImageView(mContext);
			StatusInfo statusInfo = getDataSource().get(i);
			if (statusInfo.isResult()) {
				sumWeight = statusInfo.getStatusWeight();
			} else {
				sumWeight += statusInfo.getStatusWeight();
			}
			
		}
	}

}
