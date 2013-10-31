package com.tolkachov.clientsmanager.model;

import com.tolkachov.clientsmanager.AppManager;

public class ContactInfo {

	private long mContactId;
	private long mClientId;
	private String mContactType;
	private String mContactValue;
	
	private ContactInfoListener mListener;
	
	public ContactInfo(long id, long clientId, String contactType, String contactValue) {
		super();
		this.mContactId = id;
		this.mClientId = clientId;
		this.mContactType = contactType;
		this.mContactValue = contactValue;
		
		mListener = AppManager.getDatabaseWorker();
	}
	
	/**
	 * @return the mContactId
	 */
	public long getContactId() {
		return mContactId;
	}
	
	public void setContactId(long id){
		this.mClientId = id;
	}

	public long getClientId(){
		return mClientId;
	}
	
	/**
	 * @return the mContactType
	 */
	public String getContactType() {
		return mContactType;
	}
	
	/**
	 * @param mContactType the mContactType to set
	 */
	public void setContactType(String mContactType) {
		this.mContactType = mContactType;
		mListener.updateContactType(this);
	}

	/**
	 * @return the mContactValue
	 */
	public String getContactValue() {
		return mContactValue;
	}

	/**
	 * @param mContactValue the mContactValue to set
	 */
	public void setContactValue(String mContactValue) {
		this.mContactValue = mContactValue;
		mListener.updateContactValue(this);
	}
	
	public void onAddContactInfo(){
		mListener.addContactInfo(this);
	}
	
	public void onDeteteContactInfo(){
		mListener.deleteContactInfo(this);
	}
	
	public static interface ContactInfoListener{
		
		public void updateContactValue(ContactInfo contactInfo);
		
		public void updateContactType(ContactInfo contactInfo);
				
		public void addContactInfo(ContactInfo contactInfo);
		
		public void deleteContactInfo(ContactInfo contactInfo);
		
	}
}
