package com.tolkachov.clientsmanager.widget;

import com.tolkachov.clientsmanager.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LinearIndicator extends LinearLayout {

	private View mView;
	private ImageView mFirstColumn;
	private ImageView mSecondColumn;
	private ImageView mThirdColumn;
	private ImageView mFourthColumn;
	private ImageView mFifthColumn;
	private ImageView mSixthColumn;
	
	public LinearIndicator(Context context) {
		super(context);
		this.init();
	}
	
	public LinearIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}
	
	public LinearIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}
	
	private void init(){
		mView = LayoutInflater.from(getContext()).inflate(R.layout.widget_linear_indicator, this, true);
		
		mFirstColumn = (ImageView)mView.findViewById(R.id.indicator_first);
		mSecondColumn = (ImageView)mView.findViewById(R.id.indicator_second);
		mThirdColumn = (ImageView)mView.findViewById(R.id.indicator_third);
		mFourthColumn = (ImageView)mView.findViewById(R.id.indicator_fourth);
		mFifthColumn = (ImageView)mView.findViewById(R.id.indicator_fifth);
		mSixthColumn = (ImageView)mView.findViewById(R.id.indicator_sixth);
	}
	
	public void setFirstColumnBackground(int resid){
		mFirstColumn.setBackgroundResource(resid);
	}
	
	public void setSecondColumnBackground(int resid){
		mSecondColumn.setBackgroundResource(resid);
	}

	public void setThirdColumnBackground(int resid){
		mThirdColumn.setBackgroundResource(resid);
	}

	public void setFourthColumnBackground(int resid){
		mFourthColumn.setBackgroundResource(resid);
	}

	public void setFifthColumnBackground(int resid){
		mFifthColumn.setBackgroundResource(resid);
	}

	public void setSixthColumnBackground(int resid){
		mSixthColumn.setBackgroundResource(resid);
	}
	
	public void setFirstColumnImage(int resId){
		mFirstColumn.setImageResource(resId);
	}
	
	public void setSecondColumnImage(int resId){
		mSecondColumn.setImageResource(resId);
	}
	
	public void setThirdColumnImage(int resId){
		mThirdColumn.setImageResource(resId);
	}
	
	public void setFourthColumnImage(int resId){
		mFourthColumn.setImageResource(resId);
	}
	
	public void setFifthColumnImage(int resId){
		mFifthColumn.setImageResource(resId);
	}
	
	public void setSixthColumnImage(int resId){
		mSixthColumn.setImageResource(resId);
	}

	public void setColumnImage(int column, int resId){
		switch (column) {
		case 1:
			setFirstColumnImage(resId);
			break;
			
		case 2:
			setSecondColumnImage(resId);
			break;

		case 3:
			setThirdColumnImage(resId);
			break;	
		
		case 4:
			setFourthColumnImage(resId);
			break;	
		
		case 5:
			setFifthColumnImage(resId);
			break;	
		
		case 6:
			setSixthColumnImage(resId);
			break;	
			
		default:
			break;
		}
	}
}
