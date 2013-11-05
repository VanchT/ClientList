package com.tolkachov.clientsmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.tolkachov.clientsmanager.util.Logger;


public class DatabaseConnector {

	private static final String TAG = "DatabaseWorkerLog";
	
	private static DatabaseConnector mInstance;
	private SQLiteDatabase mDatabase;
	private DatabaseHelper mDatabaseHelper;
	
	private DatabaseConnector(Context context){
		Logger.registerTag(TAG, true);
		mDatabaseHelper = DatabaseHelper.getInstance(context);
	}
		
	//Private methods
	
	//Public methods
	
	public static DatabaseConnector getInstance(Context context){
		if (mInstance == null){
			mInstance = new DatabaseConnector(context);
		}
		return mInstance;
	}
	
	/**
	 * Tries to open the database and returns the instance of the database.
	 * @return Instance of database.
	 */
	public SQLiteDatabase getDatabase() {
		openDatabase();
		return mDatabase;
	}
	
	/**
	 * Opens connection with database.
	 */
	public synchronized void openDatabase(){
		if (mDatabase == null || !mDatabase.isOpen()){
			try {
				mDatabase = mDatabaseHelper.getWritableDatabase();
				Logger.d(TAG, "DB was opened.");
			} catch (SQLiteException e) {
				Logger.e(TAG, "Error with opening of db.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Closes the current instance of database.
	 */
	public synchronized void closeDatabase(){
		if (mDatabase != null && mDatabase.isOpen()) {
			mDatabase.close();
			Logger.d(TAG, "DB was closed.");
		}
	}
	
}
