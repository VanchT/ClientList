package com.tolkachov.clientsmanager.data;

public class FilterParameters {

	private String mFilter;
	private String[] mColumns;
	
	public FilterParameters(String filter, String[] columns) {
		super();
		this.setFilter(filter);
		this.setColumns(columns);
	}

	/**
	 * @return the mFilter
	 */
	public String getFilter() {
		return mFilter;
	}

	/**
	 * @param mFilter the mFilter to set
	 */
	public void setFilter(String filter) {
		this.mFilter = filter;
	}

	/**
	 * @return the mColumns
	 */
	public String[] getColumns() {
		return mColumns;
	}

	/**
	 * @param mColumns the mColumns to set
	 */
	public void setColumns(String[] columns) {
		this.mColumns = columns;
	}
	
	public int count(){
		return mColumns.length;
	}
	
	public String getColumn(int index){
		if (index > mColumns.length - 1) {
			throw new IndexOutOfBoundsException();
		} else {
			return mColumns[index];
		}
	}
}
