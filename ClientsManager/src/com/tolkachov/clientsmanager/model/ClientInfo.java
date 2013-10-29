package com.tolkachov.clientsmanager.model;

import java.util.List;

import com.tolkachov.clientsmanager.AppManager;

public class ClientInfo extends BaseClientInfo {

	private String mClientProfession;
	private String mClientAbout;
	private List<ContactInfo> mContacts;
	private List<StatusInfo> mStatuses;
	
	private ClientInfoListener mListener;
	
	public ClientInfo(long clientId, String clientName, String photoUrl,
			String birthday, String clientProfession, String clientAbout,
			int status, String relationType) {
		super(clientId, clientName, photoUrl, birthday, status, relationType);
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

	@Override
	public void setBirthday(String birthday) {
		super.setBirthday(birthday);
		mListener.updateClientBirthday(this);
	}
	
	@Override
	public void setRelationType(String mRelationType) {
		super.setRelationType(mRelationType);
		mListener.updateClientRelationType(this);
	}
	
	public void onUpdateClientProfession(){
		mListener.updateClientProfession(this);
	}
	
	public void onUpdateClientAbout(){
		mListener.updateClientAbout(this);
	}
		
	public static interface ClientInfoListener{
		
		public void updateClientBirthday(ClientInfo clientInfo);
		
		public void updateClientProfession(ClientInfo clientInfo);
		
		public void updateClientAbout(ClientInfo clientInfo);
		
		public void updatelientStatus(ClientInfo clientInfo);
		
		public void updateClientRelationType(ClientInfo clientInfo);
		
		public List<ContactInfo> loadClientContacts(ClientInfo clientInfo);
		
		public List<StatusInfo> loadClientStatuses(ClientInfo clientInfo);
		
	}
	
}
