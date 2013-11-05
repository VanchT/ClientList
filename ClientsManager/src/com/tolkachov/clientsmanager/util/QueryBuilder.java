package com.tolkachov.clientsmanager.util;


public class QueryBuilder{
	
	private String mWhere = "";
	private String mOrderBy = "";
	private String mGroupBy = "";
	private String mHeaving = "";
	private String mLimit = "";
	private String mOffset = "";
	private String mDefaultGroupBy = "";
	
	private short mWhereCount = 0;
	private short mGroupByCount = 0;
	private short mLikeCount = 0;
	
	public QueryBuilder(){
		super();
	};
	
	public QueryBuilder(String defaultGroupByColumn){
		super();
		this.mDefaultGroupBy = " group by " + defaultGroupByColumn + " ";
	};
	
	private void handleParameter(QueryParams params){
		switch (params.getParamType()) {
		
		case WHERE:
			if (Util.isNullOrEmpty(mWhere)) mWhere = " where ";
			if (mWhereCount > 0) {
				mWhere += params.mLogicOperation + " " + params.getColumn() + "=" + params.getParamValue();
			} else {
				mWhere += params.getColumn() + "=" + params.getParamValue();
			}
			mWhere += " ";
			mWhereCount++;
			break;
		
		case ORDER_BY:
			if (Util.isNullOrEmpty(mOrderBy)) mOrderBy = " order by ";
			mOrderBy += params.getColumn() + " ";
			break;

		case GROUP_BY:
			if (Util.isNullOrEmpty(mGroupBy)) mGroupBy = " group by ";
			if (mGroupByCount > 0) {
				mGroupBy += ", " + params.getColumn();
			} else {
				mGroupBy += params.getColumn();
			}
			mGroupBy += " ";
			mGroupByCount++;
			break;
			
		case LIMIT:
			if (Util.isNullOrEmpty(mLimit)) mLimit = " limit ";
			mLimit += params.getParamValue() + " ";
			break;
			
		case OFFSET:
			if (Util.isNullOrEmpty(mOffset)) mOffset = " offset ";
			mOffset += params.getParamValue() + " ";
			break;
			
		case LIKE:
			if (Util.isNullOrEmpty(mWhere)) mWhere = " where ";
			if (mLikeCount > 0) {
				mWhere += " " + params.getLogicOperation() + " " 
						+ params.mColumn + " like " + params.getParamValue();
			} else {
				mWhere += params.mColumn + " like " + params.getParamValue();
			}
			mWhere += " ";
			mLikeCount++;
			break;
			
		case HEAVING:
			if (Util.isNullOrEmpty(mHeaving)) mHeaving = " heaving ";
			mHeaving += params.mColumn;
			mHeaving += " ";
			break;
			
		default:
			break;
		}
	}
	
	public String build(String fromTable, String[] columns, QueryParams[] queryParams){
		String result = "";
		if (queryParams != null) {
			for (QueryParams params : queryParams) {
				handleParameter(params);
			}
		}
		result += "select ";
		for (int i = 0; i < columns.length; i++){
			result += columns[i];
			if (i < columns.length - 1) result += ", ";
		}
		result += " from " + fromTable;
		if (Util.isNullOrEmpty(mGroupBy) && !Util.isNullOrEmpty(mDefaultGroupBy)){
			mGroupBy = mDefaultGroupBy;
		}
		result += mWhere + mGroupBy + mHeaving + mOrderBy + mLimit + mOffset;
		return result;
	}	
	
	public static class QueryParams {

		public static final String LOGIC_NOT = " not ";
		public static final String LOGIC_AND = " and ";
		public static final String LOGIC_OR = " or ";
		public static final String LOGIC_OR_NOT = " or not ";
		public static final String LOGIC_AND_NOT = " and not ";
		
		private String mParamValue;
		private String mColumn;
		private ParameterType mParamType;
		private String mLogicOperation;
		
		public enum ParameterType {
			ORDER_BY, GROUP_BY, HEAVING, WHERE, LIKE, LIMIT, OFFSET
		}
		
		public QueryParams(String paramValue, String column, ParameterType paramType) {
			super();
			this.setColumn(column);
			this.setParamValue(paramValue);
			this.setParamType(paramType);
		}
		
		public QueryParams(String paramValue, String column, ParameterType paramType,
				String logicOperation) {
			super();
			this.setColumn(column);
			this.setParamValue(paramValue);
			this.setParamType(paramType);
			this.setLogicOperation(logicOperation);
		}

		/**
		 * @return the mParamValue
		 */
		public String getParamValue() {
			return mParamValue;
		}

		/**
		 * @param mParamValue the paramValue to set
		 */
		public void setParamValue(String paramValue) {
			this.mParamValue = paramValue;
		}

		/**
		 * @return the mColumn
		 */
		public String getColumn() {
			return mColumn;
		}

		/**
		 * @param mColumn the column to set
		 */
		public void setColumn(String column) {
			this.mColumn = column;
		}

		/**
		 * @return the mParamType
		 */
		public ParameterType getParamType() {
			return mParamType;
		}

		/**
		 * @param mParamType the paramType to set
		 */
		public void setParamType(ParameterType paramType) {
			this.mParamType = paramType;
		}

		/**
		 * @return the mLogicOperation
		 */
		public String getLogicOperation() {
			return mLogicOperation;
		}

		/**
		 * @param mLogicOperation the logicOperation to set
		 */
		public void setLogicOperation(String logicOperation) {
			this.mLogicOperation = logicOperation;
		}

	}
	
}

