package com.tolkachov.clientsmanager.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseCursorHelper {

	public FilterCursorWrapper getFilteredCursor(SQLiteDatabase db, String query, String filter, String[] columnNames) {
		Cursor cursor = db.rawQuery(query, null);
		int[] columns = new int[columnNames.length];
		for (int i = 0; i < columnNames.length; i++){
			columns[i] = cursor.getColumnIndex(columnNames[i]);
		}
		FilterCursorWrapper cursorWrapper = new FilterCursorWrapper(cursor, filter, columns);
		return cursorWrapper;
	}
	
	public Cursor getCursor(SQLiteDatabase db, String query){
		return db.rawQuery(query, null);
	}
	
}
