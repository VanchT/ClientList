package com.tolkachov.clientsmanager.model;

import android.database.Cursor;

import com.tolkachov.clientsmanager.data.FilterParameters;

public interface ModelBaseInterface {

	public Cursor loadClientsList(FilterParameters[] params);
	
	public void addClient(ClientInfo clientInfo);
	
	public void deleteClient(ClientInfo clientInfo);
}
