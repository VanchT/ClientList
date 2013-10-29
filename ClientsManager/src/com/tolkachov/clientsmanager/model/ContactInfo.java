package com.tolkachov.clientsmanager.model;

import com.tolkachov.clientsmanager.AppManager;

public class ContactInfo {

	private long mContactId;
	private String mContactType;
	private String mContactValue;
	
	private ContactInfoListener mListener;
	
	public ContactInfo(long id, String contactType, String contactValue) {
		super();
		this.mContactId = id;
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
	}

	public void onUpdateContactInfo(){
		mListener.updateContactInfo(this);
	}
	
	public void onAddContactInfo(){
		mListener.addContactInfo(this);
	}
	
	public void onDeteteContactInfo(){
		mListener.deleteContactInfo(this);
	}
	
	public static interface ContactInfoListener{
		
		public void updateContactInfo(ContactInfo contactInfo);
		
		public void addContactInfo(ContactInfo contactInfo);
		
		public void deleteContactInfo(ContactInfo contactInfo);
		
	}
}
