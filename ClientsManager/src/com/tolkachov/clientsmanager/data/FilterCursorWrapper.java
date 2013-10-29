package com.tolkachov.clientsmanager.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.tolkachov.clientsmanager.util.Util;

public class FilterCursorWrapper extends CursorWrapper {

	private String mFilter;
	private int[] mColumns;
	private int[] mIndex;
	private int mCount=0;
	private int mPosition=0;
	
	public FilterCursorWrapper(Cursor cursor, String filter, int[] columns) {
		super(cursor);
		this.mColumns = columns;
		if (Util.isNullOrEmpty(filter)) {
			this.mCount = super.getCount();
			this.mIndex = new int[this.mCount];
			for (int i=0;i<this.mCount;i++) {
				this.mIndex[i] = i;
			}
		} else {
			this.mFilter = filter.toLowerCase();
			this.mCount = super.getCount();
			this.mIndex = new int[this.mCount];
			for (int i=0; i<this.mCount; i++) {
				super.moveToPosition(i);
				for (int j = 0; j < this.mColumns.length; j++) {
					if (this.getString(this.mColumns[j]).toLowerCase().contains(this.mFilter)){
						this.mIndex[this.mPosition++] = i;
						break;
					}
				}
			}
			this.mCount = this.mPosition;
			this.mPosition = 0;
			super.moveToFirst();
		}
	}
	
	@Override
	public boolean move(int offset) {
		return this.moveToPosition(this.mPosition+offset);
	}
	
	@Override
	public boolean moveToNext() {
		return this.moveToPosition(this.mPosition+1);
	}
	
	@Override
	public boolean moveToPrevious() {
		return this.moveToPosition(this.mPosition-1);
	}
	
	@Override
	public boolean moveToFirst() {
		return this.moveToPosition(0);
	}
	
	@Override
	public boolean moveToLast() {
		return this.moveToPosition(this.mCount-1);
	}
	
	@Override
	public boolean moveToPosition(int position) {
		if (position >= this.mCount || position < 0)
			return false;
		return super.moveToPosition(this.mIndex[position]);
	}
	
	@Override
	public int getCount() {
		return this.mCount;
	}
	
	@Override
	public int getPosition() {
		return this.mPosition;
	}

}
