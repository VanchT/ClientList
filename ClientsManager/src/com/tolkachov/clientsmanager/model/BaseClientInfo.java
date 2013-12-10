package com.tolkachov.clientsmanager.model;

import android.database.Cursor;

import com.tolkachov.clientsmanager.AppManager;
import com.tolkachov.clientsmanager.util.Util;

public class BaseClientInfo {

	public static final String DEFAULT_CLIENT_STATUS = "false:false,false:false,false:false,false:false,false:false,false:false,";
	
	protected long mClientId;
	protected String mClientName;
	protected String mPhotoUrl;
	protected String mBirthday;
	private String mStatus;
	private String mRelationType;
	
	private OnStatusChangeListener mStatusChangeListener;

	public BaseClientInfo(String clientName){
		super();
		this.mClientName = clientName;
		this.mStatus = DEFAULT_CLIENT_STATUS;
	}
	
	public BaseClientInfo(long clientId, String clientName, String photoUrl,
			String birthday, String status, String relationType) {
		super();
		this.mClientId = clientId;
		this.mClientName = clientName;
		this.mPhotoUrl = photoUrl;
		this.mBirthday = birthday;
		if (Util.isNullOrEmpty(status)){
			this.mStatus = DEFAULT_CLIENT_STATUS;
		} else {
			this.mStatus = status;
		}
		this.mRelationType = relationType;
	}
	
	public static BaseClientInfo valueOf(Cursor cursor){
		ClientInfo clientInfo = AppManager.getDatabaseWorker().obtainClientFromCuror(cursor);
		return clientInfo.getBaseClientInfo();
	}

	/**
	 * @return the mClientId
	 */
	public long getClientId() {
		return mClientId;
	}
	
	public void setClientId(long id){
		this.mClientId = id;
	}

	/**
	 * @return the mClientName
	 */
	public String getClientName() {
		return mClientName;
	}

	/**
	 * @return the mPhotoUrl
	 */
	public String getPhotoUrl() {
		return mPhotoUrl;
	}

	/**
	 * @return the mBirthday
	 */
	public String getBirthday() {
		return mBirthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.mBirthday = birthday;
	}

	/**
	 * @return the mStatus
	 */
	public String getStatus() {
		return mStatus;
	}
	
	protected void setStatus(String status){
		this.mStatus = status;
		mStatusChangeListener.onStatusChange(status);
	}

	/**
	 * @return the mRelationType
	 */
	public String getRelationType() {
		return mRelationType;
	}

	/**
	 * @param mRelationType the mRelationType to set
	 */
	public void setRelationType(String mRelationType) {
		this.mRelationType = mRelationType;
	}

	public void setOnStatusChangeListener(OnStatusChangeListener listener){
		this.mStatusChangeListener = listener;
	}
	
	public static interface OnStatusChangeListener {
		
		public void onStatusChange(String newstatus);
		
	}
}