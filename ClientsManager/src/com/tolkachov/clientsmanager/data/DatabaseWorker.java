package com.tolkachov.clientsmanager.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.YuvImage;

import com.tolkachov.clientsmanager.data.QueryBuilder.QueryParams;
import com.tolkachov.clientsmanager.data.QueryBuilder.QueryParams.ParameterType;
import com.tolkachov.clientsmanager.model.ClientInfo;
import com.tolkachov.clientsmanager.model.ContactInfo;
import com.tolkachov.clientsmanager.model.ModelBaseInterface;
import com.tolkachov.clientsmanager.model.StatusInfo;
import com.tolkachov.clientsmanager.util.Util;

public class DatabaseWorker implements ModelBaseInterface, StatusInfo.StatusInfoListener,
		ContactInfo.ContactInfoListener, ClientInfo.ClientInfoListener{

	private static final String QUERY_CLIENT_STATUSES = "select S." + DatabaseHelper.ID + ", CS." + DatabaseHelper.CLIENT_ID 
			+ ", S." + DatabaseHelper.STATUS_NAME + ", S." + DatabaseHelper.STATUS_WEIGHT
			+ ", CS." + DatabaseHelper.STATUS_STATE + ", CS." + DatabaseHelper.CLIENT_IS_MINE
			+ " from " + DatabaseHelper.TABLE_STATUSES + " as S inner join " + DatabaseHelper.TABLE_CLIENTS_STATUSES
			+ " as CS on S." + DatabaseHelper.ID + "=CS." + DatabaseHelper.STATUS_ID 
			+ " where CS." + DatabaseHelper.CLIENT_ID + "=%s";
	
	private static final String QUERY_CLIENT_CONTACTS = "select C." + DatabaseHelper.ID + ", C." + DatabaseHelper.CLIENT_ID
			+ ", T." + DatabaseHelper.TYPE_NAME + ", C." + DatabaseHelper.CONTACT_VALUE 
			+ " from " + DatabaseHelper.TABLE_CONTACTS + " as C inner join " + DatabaseHelper.TABLE_CONTACT_TYPES
			+ " as T on C." + DatabaseHelper.TYPE_ID + "=T." + DatabaseHelper.ID
			+ " where " + DatabaseHelper.CLIENT_ID + "=%s";
	
	private DatabaseConnector mDbConnector;
	private DatabaseCursorHelper mCursorHalper;
	private Cursor mCachedCursor;
	private String mCachedFilter;
	private String[] mCachedFilterColumns;
	private String mCachedQuery;
	
	private String[] mDefaultColumns = {
			DatabaseHelper.TABLE_CLIENTS + "." + DatabaseHelper.ID,
			DatabaseHelper.TABLE_CLIENTS + "." + DatabaseHelper.CLIENT_NAME,
			DatabaseHelper.TABLE_CLIENTS + "." + DatabaseHelper.CLIENT_PHOTO_LINK,
			DatabaseHelper.TABLE_CLIENTS + "." + DatabaseHelper.CLIENT_BIRTHDAY,
			DatabaseHelper.TABLE_CLIENTS + "." + DatabaseHelper.CLIENT_STATUS_SUM,
			DatabaseHelper.TABLE_RELATION_TYPES + "." + DatabaseHelper.TYPE_NAME
		};
	
	public DatabaseWorker(Context context){
		mDbConnector = new DatabaseConnector(context);
		mCursorHalper = new DatabaseCursorHelper();
	}
	
	public  void closeDatabase(){
		mDbConnector.closeDatabase();
	}
	
	// ========== private methods ==========
	
	private synchronized StatusInfo addStatus(StatusInfo statusInfo) {
		String[] columns = new String[] {DatabaseHelper.ID};
		String selection = DatabaseHelper.STATUS_NAME + "=" + statusInfo.getStatusName();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor = db.query(DatabaseHelper.TABLE_STATUSES, columns, selection, null, null, null, null);
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.CLIENT_ID, statusInfo.getClientId());
			values.put(DatabaseHelper.STATUS_ID, cursor.getLong(0));			
			values.put(DatabaseHelper.STATUS_STATE, statusInfo.getStatusState() ? 1 : 0);
			values.put(DatabaseHelper.CLIENT_IS_MINE, statusInfo.isMine() ? 1 : 0);
			long id = mDbConnector.getDatabase().insert(DatabaseHelper.TABLE_CLIENTS_STATUSES, null, values);
			if (id == -1) {
				throw new Exception("The Status was not added!");
			} else {
				statusInfo.setStatusInfoId(id);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return statusInfo;
	}
	
	private synchronized void deleteStatus(StatusInfo statusInfo) {
		String whereClause = DatabaseHelper.ID + "=" + statusInfo.getStatusInfoId();
		mDbConnector.getDatabase().delete(DatabaseHelper.TABLE_CLIENTS_STATUSES, whereClause, null);
	}
	
	private synchronized void loadAndCacheClientsList(String query){
		Cursor cursor = mDbConnector.getDatabase().rawQuery(query, null);
		mCachedCursor = cursor;
	}
	
	private void clearFilterCache(){
		this.mCachedFilter = null;
		this.mCachedFilterColumns = null;
	}
	
	private void clearCursorCache(){
		this.mCachedCursor.close();
		this.mCachedCursor = null;
	}
	
	// ========== public methods ==========

	public void clearCache(){
		clearCursorCache();
		clearFilterCache();
	}
	
	// ModelBaseInterface methods
	
	@Override
	public synchronized Cursor loadClientsList(QueryBuilder.QueryParams[] params, String[] columns) {
		QueryBuilder queryBuilder = new QueryBuilder();
		if (columns == null || columns.length == 0){
			columns = mDefaultColumns;
		}
		String fromTable = DatabaseHelper.TABLE_CLIENTS + " as C inner join "
				+ DatabaseHelper.TABLE_RELATION_TYPES + " as R on C." 
				+ DatabaseHelper.RELATIOIN_ID + "=T." + DatabaseHelper.ID;
		String query = queryBuilder.build(fromTable, columns, params);
		this.mCachedQuery = query;
		Cursor result = null;
		loadAndCacheClientsList(query);
		if (!Util.isNullOrEmpty(mCachedFilter) && mCachedFilterColumns != null){
			result = mCursorHalper.getFilteredCursor(mCachedCursor, mCachedFilter, mCachedFilterColumns);
		}  else {
			result = mCachedCursor; 
		}
		return result;
	}
	
	public Cursor loadFilteredClientsList(String[] columns, String filter){
		Cursor result = null;
		this.mCachedFilter = filter;
		this.mCachedFilterColumns = columns;
		if (mCachedCursor == null) {
			loadAndCacheClientsList(mCachedQuery);
		}
		result = mCursorHalper.getFilteredCursor(mCachedCursor, mCachedFilter, mCachedFilterColumns);
		return result;
	}
	
	@Override
	public synchronized ClientInfo addClient(ClientInfo clientInfo) {
		clearCursorCache();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor = db.query(DatabaseHelper.TABLE_STATUSES, 
					new String[] {DatabaseHelper.STATUS_NAME, DatabaseHelper.STATUS_WEIGHT},
					null, null, null, null, null);
			List<StatusInfo> statuses = new ArrayList<StatusInfo>();
			while (cursor.moveToNext()){
				StatusInfo statusInfo = new StatusInfo(-1, clientInfo.getClientId(),
						cursor.getString(0), cursor.getInt(1), false, false);
				statusInfo = this.addStatus(statusInfo);
				statuses.add(statusInfo);
			}
			clientInfo.setStatuses(statuses);
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.CLIENT_NAME, clientInfo.getClientName());
			values.put(DatabaseHelper.CLIENT_STATUS_SUM, clientInfo.getStatus());
			long id = db.insert(DatabaseHelper.TABLE_CLIENTS, null, values);
			if (id == -1) {
				throw new Exception("The Client was not added!");
			} else {
				clientInfo.setClientId(id);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return clientInfo;
	}
	
	@Override
	public synchronized void deleteClient(ClientInfo clientInfo) {
		clearCursorCache();
		String whereClause = DatabaseHelper.ID + "=" + clientInfo.getClientId();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		try {
			List<ContactInfo> contacts = clientInfo.getContacts();
			for (ContactInfo contactInfo : contacts) {
				this.deleteContactInfo(contactInfo);
			}
			List<StatusInfo> statuses = clientInfo.getStatuses();
			for (StatusInfo statusInfo : statuses) {
				this.deleteStatus(statusInfo);
			}
			db.delete(DatabaseHelper.TABLE_CLIENTS, whereClause, null);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	// StatusInfoListener methods
	
	@Override
	public synchronized void updateStatusState(StatusInfo statusInfo) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.STATUS_STATE, statusInfo.getStatusState() ? 1 : 0);
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(statusInfo.getStatusInfoId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS_STATUSES, values, whereClause, null);		
	}

	@Override
	public synchronized void updateIsMineState(StatusInfo statusInfo) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CLIENT_IS_MINE, statusInfo.isMine() ? 1 : 0);
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(statusInfo.getStatusInfoId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS_STATUSES, values, whereClause, null);			
	}
	
	// ContactInfoListener methods

	@Override
	public synchronized void updateContactType(ContactInfo contactInfo) {
		String[] columns = new String[] {DatabaseHelper.ID};
		String selection = DatabaseHelper.TYPE_NAME + "=" + contactInfo.getContactType();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor = db.query(DatabaseHelper.TABLE_CONTACT_TYPES,
					columns, selection, null, null, null, null);
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.TYPE_ID, cursor.getLong(0));
			String whereClause = DatabaseHelper.ID + "=" + String.valueOf(contactInfo.getContactId());
			db.update(DatabaseHelper.TABLE_CONTACTS, values, whereClause, null);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	@Override
	public synchronized void updateContactValue(ContactInfo contactInfo) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CONTACT_VALUE, contactInfo.getContactValue());
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(contactInfo.getContactId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CONTACTS, values, whereClause, null);	
	}

	@Override
	public synchronized void addContactInfo(ContactInfo contactInfo) {
		String[] columns = new String[] {DatabaseHelper.ID};
		String selection = DatabaseHelper.TYPE_NAME + "=" + contactInfo.getContactType();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor = db.query(DatabaseHelper.TABLE_CONTACT_TYPES,
					columns, selection, null, null, null, null);
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.CLIENT_ID, contactInfo.getClientId());
			values.put(DatabaseHelper.CONTACT_VALUE, contactInfo.getContactValue());
			values.put(DatabaseHelper.TYPE_ID, cursor.getLong(0));			
			mDbConnector.getDatabase().insert(DatabaseHelper.TABLE_CONTACTS, null, values);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public synchronized void deleteContactInfo(ContactInfo contactInfo) {
		String whereClause = DatabaseHelper.ID + "=" + contactInfo.getContactId();
		mDbConnector.getDatabase().delete(DatabaseHelper.TABLE_CONTACTS, whereClause, null);
	}
	
	// ClientInfoListener methods

	@Override
	public synchronized void updateClientBirthday(ClientInfo clientInfo) {
		clearCursorCache();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CLIENT_BIRTHDAY, clientInfo.getBirthday());
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(clientInfo.getClientId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS, values, whereClause, null);		
	}

	@Override
	public synchronized void updateClientProfession(ClientInfo clientInfo) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CLIENT_PROFESSION, clientInfo.getClientProfession());
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(clientInfo.getClientId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS, values, whereClause, null);
	}

	@Override
	public synchronized void updateClientAbout(ClientInfo clientInfo) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CLIENT_ABOUT, clientInfo.getClientAbout());
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(clientInfo.getClientId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS, values, whereClause, null);
	}

	@Override
	public synchronized void updateClientStatus(ClientInfo clientInfo) {
		clearCursorCache();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.CLIENT_STATUS_SUM, clientInfo.getStatus());
		String whereClause = DatabaseHelper.ID + "=" + String.valueOf(clientInfo.getClientId());
		mDbConnector.getDatabase().update(DatabaseHelper.TABLE_CLIENTS, values, whereClause, null);
	}
	
	@Override
	public synchronized void updateClientRelationType(ClientInfo clientInfo) {
		clearCursorCache();
		String[] columns = new String[] {DatabaseHelper.ID};
		String selection = DatabaseHelper.TYPE_NAME + "=" + clientInfo.getRelationType();
		SQLiteDatabase db = mDbConnector.getDatabase();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor = db.query(DatabaseHelper.TABLE_RELATION_TYPES,
					columns, selection, null, null, null, null);
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.RELATIOIN_ID, cursor.getLong(0));
			String whereClause = DatabaseHelper.ID + "=" + String.valueOf(clientInfo.getClientId());
			db.update(DatabaseHelper.TABLE_CLIENTS, values, whereClause, null);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public synchronized List<ContactInfo> loadClientContacts(ClientInfo clientInfo) {
		List<ContactInfo> result = new ArrayList<ContactInfo>();
		String query = String.format(QUERY_CLIENT_CONTACTS, String.valueOf(clientInfo.getClientId()));
		Cursor cursor = mDbConnector.getDatabase().rawQuery(query, null);
		while (cursor.moveToNext()){
			ContactInfo info = new ContactInfo(
					cursor.getLong(0), 
					cursor.getLong(1), 
					cursor.getString(2), 
					cursor.getString(3));
			result.add(info);
		}
		cursor.close();
		return result;
	}

	@Override
	public synchronized List<StatusInfo> loadClientStatuses(ClientInfo clientInfo) {
		List<StatusInfo> result = new ArrayList<StatusInfo>();
		String query = String.format(QUERY_CLIENT_STATUSES, String.valueOf(clientInfo.getClientId()));
		Cursor cursor = mDbConnector.getDatabase().rawQuery(query, null);
		while (cursor.moveToNext()){
			StatusInfo info = new StatusInfo(
					cursor.getLong(0),
					cursor.getLong(1),
					cursor.getString(2), 
					cursor.getInt(3), 
					cursor.getInt(4) == 1 ? true : false,
					cursor.getInt(5) == 1 ? true : false);
			result.add(info);
		}
		cursor.close();
		return result;
	}


}
