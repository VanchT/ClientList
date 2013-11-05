package com.tolkachov.clientsmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tolkachov.clientsmanager.data.DatabaseConnector;
import com.tolkachov.clientsmanager.data.DatabaseWorker;
import com.tolkachov.clientsmanager.model.ClientInfo;
import com.tolkachov.clientsmanager.util.Logger;

public class AppManager {

	private static Context mAppContext;
	private static int mLoaderId = 0;
	private static ClientInfo mCurrentSelectedClient;
	private static DatabaseWorker mDBWorker;
	
	// Private methods
	
	// Public methods
	
	/**
	 * 
	 * @param context
	 */
	public static void init(Context context){
		mAppContext = context;
		Logger.init();
		Logger.setLogMode(false);
		mDBWorker = new DatabaseWorker(context);
	}
	
	public static DatabaseWorker getDatabaseWorker(){
		return mDBWorker;
	}
	
	/**
	 * Call it before stopping of the application for release resources.
	 */
	public static void onStopApplication(){
		DatabaseConnector.getInstance(mAppContext).closeDatabase();
	}
		
	public static String getStringResource(int resId){
		return mAppContext.getString(resId);
	}

	public Context getApplicationContext(){
		return mAppContext;
	}
	
	public static Bitmap getBitmapResource(int resId){
		return BitmapFactory.decodeResource(mAppContext.getResources(), resId);
	}
	
	public static int getActualLoaderId(){
		return mLoaderId++;
	}

	public static int getResourceId(String resName, String resType){
		return mAppContext.getResources().getIdentifier(resName, resType, mAppContext.getPackageName());
	}

	
	// Special methods
	
	/**
	 * @return the mCurrentSelectedClient
	 */
	public static ClientInfo getCurrentSelectedClient() {
		return mCurrentSelectedClient;
	}

	/**
	 * @param mCurrentSelectedClient the mCurrentSelectedClient to set
	 */
	public static void setCurrentSelectedClient(ClientInfo currentSelectedClient) {
		AppManager.mCurrentSelectedClient = currentSelectedClient;
	}
}
