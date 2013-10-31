package com.tolkachov.clientsmanager.model;

import android.database.Cursor;

import com.tolkachov.clientsmanager.util.QueryBuilder;

public interface ModelBaseInterface {
	
	public Cursor loadClientsList(QueryBuilder.QueryParams[] params, String[] columns);
	
	public Cursor loadFilteredClientsList(String[] columns, String filter);
	
	public ClientInfo addClient(ClientInfo clientInfo);
	
	public void deleteClient(ClientInfo clientInfo);
}
