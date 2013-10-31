package com.tolkachov.clientsmanager.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseCursorHelper {

	public FilterCursorWrapper getFilteredCursor(SQLiteDatabase db, String query, String filter, String[] columnNames) {
		Cursor cursor = db.rawQuery(query, null);
		return getFilteredCursor(cursor, filter, columnNames);
	}
	
	public FilterCursorWrapper getFilteredCursor(Cursor cursor, String filter, String[] columnNames) {
		int[] columns = new int[columnNames.length];
		for (int i = 0; i < columnNames.length; i++){
			columns[i] = cursor.getColumnIndex(columnNames[i]);
		}
		FilterCursorWrapper cursorWrapper = new FilterCursorWrapper(cursor, filter, columns);
		return cursorWrapper;
	}
	
	public Cursor getCursorRawQuery(SQLiteDatabase db, String query){
		return db.rawQuery(query, null);
	}
	
}
