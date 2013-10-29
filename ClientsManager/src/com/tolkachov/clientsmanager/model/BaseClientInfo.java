package com.tolkachov.clientsmanager.model;

public class BaseClientInfo {

	protected long mClientId;
	protected String mClientName;
	protected String mPhotoUrl;
	protected String mBirthday;
	private int mStatus;
	private String mRelationType;

	public BaseClientInfo(long clientId, String clientName, String photoUrl,
			String birthday, int status, String relationType) {
		super();
		this.mClientId = clientId;
		this.mClientName = clientName;
		this.mPhotoUrl = photoUrl;
		this.mBirthday = birthday;
		this.mStatus = status;
		this.setRelationType(relationType);
	}

	/**
	 * @return the mClientId
	 */
	public long getClientId() {
		return mClientId;
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
	public int getStatus() {
		return mStatus;
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

}