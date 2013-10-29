package com.tolkachov.clientsmanager.data;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.tolkachov.clientsmanager.model.ClientInfo;
import com.tolkachov.clientsmanager.model.ContactInfo;
import com.tolkachov.clientsmanager.model.ModelBaseInterface;
import com.tolkachov.clientsmanager.model.StatusInfo;

public class DatabaseWorker implements ModelBaseInterface, StatusInfo.StatusInfoListener,
		ContactInfo.ContactInfoListener, ClientInfo.ClientInfoListener{

	private DatabaseConnector mDbConnector;
	private DatabaseCursorHelper mCursorHalper;
	
	public DatabaseWorker(Context context){
		mDbConnector = new DatabaseConnector(context);
		mCursorHalper = new DatabaseCursorHelper();
	}
	
	public void closeDatabase(){
		mDbConnector.closeDatabase();
	}

	// ModelBaseInterface methods
	
	@Override
	public Cursor loadClientsList(FilterParameters[] params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addClient(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteClient(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}
	
	// StatusInfoListener methods
	
	@Override
	public void updateStatusState(StatusInfo statusInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIsMineState(StatusInfo statusInfo) {
		// TODO Auto-generated method stub
		
	}
	
	// ContactInfoListener methods

	@Override
	public void updateContactInfo(ContactInfo contactInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContactInfo(ContactInfo contactInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContactInfo(ContactInfo contactInfo) {
		// TODO Auto-generated method stub
		
	}
	
	// ClientInfoListener methods

	@Override
	public void updateClientBirthday(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientProfession(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClientAbout(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatelientStatus(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateClientRelationType(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ContactInfo> loadClientContacts(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatusInfo> loadClientStatuses(ClientInfo clientInfo) {
		// TODO Auto-generated method stub
		return null;
	}


}
