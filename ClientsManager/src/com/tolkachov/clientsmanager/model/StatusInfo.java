package com.tolkachov.clientsmanager.model;

import com.tolkachov.clientsmanager.AppManager;

public class StatusInfo {

	
	private long mStatusInfoId;
	private long mClientId;
	private String mStatusName;
	private int mStatusWeight;
	private boolean mStatusState;
	private boolean mIsMine;
	
	private StatusInfoListener mListener;
	private OnStatusChangeListener mStatusChangeListener;
	
	public StatusInfo(long statusInfoId, long clientId, String statusName,
			int statusWeight, boolean statusState, boolean isMine) {
		super();
		this.mStatusInfoId = statusInfoId;
		this.mClientId = clientId;
		this.mStatusName = statusName;
		this.mStatusWeight = statusWeight;
		this.mStatusState = statusState;
		this.mIsMine = isMine;
		mListener = AppManager.getDatabaseWorker();
	}

	/**
	 * @return the mStatusInfoId
	 */
	public long getStatusInfoId() {
		return mStatusInfoId;
	}
	
	public void setStatusInfoId(long id){
		this.mStatusInfoId = id;
	}
	
	public long getClientId(){
		return mClientId;
	}

	/**
	 * @return the mStatusName
	 */
	public String getStatusName() {
		return mStatusName;
	}

	/**
	 * @return the mStatusWeight
	 */
	public int getStatusWeight() {
		return mStatusWeight;
	}

	/**
	 * @return the mStatusState
	 */
	public boolean getStatusState() {
		return mStatusState;
	}

	/**
	 * @param mStatusState the mStatusState to set
	 */
	public void setStatusState(boolean statusState) {
		this.mStatusState = statusState;
		mListener.updateStatusState(this);
		mStatusChangeListener.onStatusChange();
	}

	/**
	 * @return the mIsMine
	 */
	public boolean isMine() {
		return mIsMine;
	}

	/**
	 * @param mIsMine the mIsMine to set
	 */
	public void setMine(boolean isMine) {
		this.mIsMine = isMine;
		mListener.updateIsMineState(this);
		mStatusChangeListener.onStatusChange();
	}
		
	public void setOnStatusChangeListener(OnStatusChangeListener listener){
		this.mStatusChangeListener = listener;
	}
	
	public static interface StatusInfoListener{
		
		public void updateStatusState(StatusInfo statusInfo);
		
		public void updateIsMineState(StatusInfo statusInfo);
				
	}
	
	public static interface OnStatusChangeListener{
		
		public void onStatusChange();
		
	}
	
}
