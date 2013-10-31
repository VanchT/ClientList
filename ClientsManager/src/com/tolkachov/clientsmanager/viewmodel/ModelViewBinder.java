package com.tolkachov.clientsmanager.viewmodel;

import android.view.View;

public abstract class ModelViewBinder<T> {

	private View mView;
	private T mDataSource;
	
	protected ModelViewBinder(View view, T dataSource){
		this.mView = view;
		this.mDataSource = dataSource;
	}
	
	public abstract void bind();
	
	public void bind(T newDataSource){
		this.mDataSource = newDataSource;
		bind();	
	}
	
	protected View getView(){
		return this.mView;
	}
	
	protected T getDataSource(){
		return this.mDataSource;
	}
	
	protected void setDataSource(T newDataSource){
		mDataSource = newDataSource;
	}
}
