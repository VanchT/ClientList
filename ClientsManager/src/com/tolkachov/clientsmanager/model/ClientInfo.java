package com.tolkachov.clientsmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.tolkachov.clientsmanager.AppManager;
import com.tolkachov.clientsmanager.model.BaseClientInfo.OnStatusChangeListener;

public class ClientInfo {

	private BaseClientInfo mBaseClientInfo;
	private String mClientProfession;
	private String mClientAbout;
	private List<ContactInfo> mContacts;
	private List<StatusInfo> mStatuses;
	
	private ClientInfoListener mListener;
	
 	public ClientInfo(String clientName){
		super();
		this.mBaseClientInfo = new BaseClientInfo(clientName); 
		this.mContacts = new ArrayList<ContactInfo>();
		this.mStatuses = new ArrayList<StatusInfo>();
	}
	
	public ClientInfo(BaseClientInfo baseClientInfo, String clientProfession, 
			String clientAbout) {
		super();
		this.mBaseClientInfo = baseClientInfo;
		this.mClientProfession = clientProfession;
		this.mClientAbout = clientAbout;
		
		mListener = AppManager.getDatabaseWorker();
	}

	/**
	 * @return the mClientProfession
	 */
	public String getClientProfession() {
		return mClientProfession;
	}

	/**
	 * @param clientProfession the clientProfession to set
	 */
	public void setClientProfession(String clientProfession) {
		this.mClientProfession = clientProfession;
		mListener.updateClientProfession(this);
	}

	/**
	 * @return the mClientAbout
	 */
	public String getClientAbout() {
		return mClientAbout;
	}

	/**
	 * @param clientAbout the clientAbout to set
	 */
	public void setClientAbout(String clientAbout) {
		this.mClientAbout = clientAbout;
		mListener.updateClientAbout(this);
	}

	/**
	 * @return the mContacts
	 */
	public List<ContactInfo> getContacts() {
		return mContacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<ContactInfo> contacts) {
		this.mContacts = contacts;
	}

	/**
	 * @return the mStatuses
	 */
	public List<StatusInfo> getStatuses() {
		return mStatuses;
	}

	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(List<StatusInfo> statuses) {
		this.mStatuses = statuses;
	}
			
	// ========== BaseClientInfo ===========
	
	/**
	 * @return the mClientId
	 */
	public long getClientId() {
		return mBaseClientInfo.getClientId();
	}
	
	public void setClientId(long id){
		mBaseClientInfo.setClientId(id);
	}

	/**
	 * @return the mClientName
	 */
	public String getClientName() {
		return mBaseClientInfo.getClientName();
	}

	/**
	 * @return the mPhotoUrl
	 */
	public String getPhotoUrl() {
		return mBaseClientInfo.getPhotoUrl();
	}

	/**
	 * @return the mBirthday
	 */
	public String getBirthday() {
		return mBaseClientInfo.getBirthday();
	}

	public void setBirthday(String birthday) {
		mBaseClientInfo.setBirthday(birthday);
		mListener.updateClientBirthday(this);
	}
	
	public void setRelationType(String mRelationType) {
		mBaseClientInfo.setRelationType(mRelationType);
		mListener.updateClientRelationType(this);
	}

	/**
	 * @return the mStatus
	 */
	public String getStatus() {
		return mBaseClientInfo.getStatus();
	}
	
	public void setStatus(String status){
		this.mBaseClientInfo.setStatus(status);
		mListener.updateClientStatus(this);
	}

	/**
	 * @return the mRelationType
	 */
	public String getRelationType() {
		return mBaseClientInfo.getRelationType();
	}

	//=======================================
	
	public void setOnStatusChangeListener(OnStatusChangeListener listener){
		this.mBaseClientInfo.setOnStatusChangeListener(listener);
	}
	
	public static interface ClientInfoListener{
		
		public void updateClientBirthday(ClientInfo clientInfo);
		
		public void updateClientProfession(ClientInfo clientInfo);
		
		public void updateClientAbout(ClientInfo clientInfo);
		
		public void updateClientStatus(ClientInfo clientInfo);
		
		public void updateClientRelationType(ClientInfo clientInfo);
		
		public List<ContactInfo> loadClientContacts(ClientInfo clientInfo);
		
		public List<StatusInfo> loadClientStatuses(ClientInfo clientInfo);
		
	}
	
	
}
