package com.tolkachov.clientsmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "ClientsManagerDB";
	private static final int DATABASE_VERSION = 1;
	//Table names
	private static final String TABLE_METADATA = "android_metadata";
	public static final String TABLE_CLIENTS = "Clients";
	public static final String TABLE_CONTACTS = "Contacts";
	public static final String TABLE_STATUSES = "Statuses";
	public static final String TABLE_CLIENTS_STATUSES = "Clients_Statuses";
	public static final String TABLE_CONTACT_TYPES = "Contact_Types";
	public static final String TABLE_RELATION_TYPES = "Relation_Types";
	//Common
	private static final String METADATA_LOCALE = "locale";
	public static final String ID = "_id";
	public static final String TYPE_NAME = "type_name";
	//Table Statuses
	public static final String STATUS_ID = "status_id";
	public static final String STATUS_NAME = "status_name";
	public static final String STATUS_WEIGHT = "status_weight";
	//Table Clients
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_NAME = "client_name";
	public static final String CLIENT_PHOTO_LINK = "client_photo_link";
	public static final String CLIENT_BIRTHDAY = "client_birthday";
	public static final String CLIENT_PROFESSION = "client_profession";
	public static final String CLIENT_ABOUT = "client_about";
	public static final String CLIENT_STATUS_SUM = "client_status_sum";
	//Table Clients_Statuses
	public static final String STATUS_STATE = "status_state";
	public static final String CLIENT_IS_MINE = "client_is_mine";
	//Table Contact_Types
	public static final String TYPE_ID = "type_id";
	//Table Contacts
	public static final String CONTACT_VALUE = "contact_value";
	//Table Relation_Types
	public static final String RELATIOIN_ID = "relation_id";
	
	//Creation
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	private static final String CREATE_METADATA_TABLE_STRING = "(" 
			+ METADATA_LOCALE + " text default 'ru_RU');";

	
	private static final String CREATE_RELATION_TYPES_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ TYPE_NAME + " text not null unique"
			+");";
	
	private static final String CREATE_CONTACT_TYPES_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ TYPE_NAME + " text not null unique"
			+");";
	
	private static final String CREATE_STATUSES_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ STATUS_NAME + " text not null unique, "
			+ STATUS_WEIGHT + " integer not null"
			+ ");";
	
	private static final String CREATE_CLIENTS_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ CLIENT_NAME + " text not null, "
			+ CLIENT_BIRTHDAY + " text, "
			+ CLIENT_PROFESSION + " text, "
			+ CLIENT_ABOUT + " text, "
			+ CLIENT_STATUS_SUM + " text not null, "
			+ RELATIOIN_ID + " integer, "
			+ CLIENT_PHOTO_LINK + " text, "
			+ "foreign key (" + RELATIOIN_ID + ") references " + TABLE_RELATION_TYPES + "(" + ID + "), "
			+ ");";
	
	private static final String CREATE_CONTACTS_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ CLIENT_ID + " integer not null, "
			+ CONTACT_VALUE + " text not null, "
			+ TYPE_ID + " integer not null, "
			+ "foreign key (" + CLIENT_ID + ") references " + TABLE_CLIENTS + "(" + ID + "), "
			+ "foreign key (" + TYPE_ID + ") references " + TABLE_CONTACT_TYPES + "(" + ID + "), "
			+ ");";
	
	private static final String CREATE_CLIENTS_STATUSES_TABLE_STRING = "("
			+ ID + " integer primary key autoincrement, "
			+ CLIENT_ID + " integer not null, "
			+ STATUS_ID + " integer not null, "
			+ STATUS_STATE + " integer not null, "		//boolean
			+ CLIENT_IS_MINE + " integer not null, "	//boolean
			+ "foreign key (" + CLIENT_ID + ") references " + TABLE_CLIENTS + "(" + ID + "), "
			+ "foreign key (" + STATUS_ID + ") references " + TABLE_STATUSES + "(" + ID + "), "
			+ ");";
	
	private static DatabaseHelper mInstance;
		
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static DatabaseHelper getInstance(Context context){
		if (mInstance == null) {
			mInstance = new DatabaseHelper(context);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE + TABLE_METADATA + CREATE_METADATA_TABLE_STRING);
		db.execSQL(CREATE_TABLE + TABLE_RELATION_TYPES + CREATE_RELATION_TYPES_TABLE_STRING);
		db.execSQL(CREATE_TABLE + TABLE_CONTACT_TYPES + CREATE_CONTACT_TYPES_TABLE_STRING);
		db.execSQL(CREATE_TABLE + TABLE_STATUSES + CREATE_STATUSES_TABLE_STRING); 
		db.execSQL(CREATE_TABLE + TABLE_CLIENTS + CREATE_CLIENTS_TABLE_STRING);
		db.execSQL(CREATE_TABLE + TABLE_CONTACTS + CREATE_CONTACTS_TABLE_STRING);
		db.execSQL(CREATE_TABLE + TABLE_CLIENTS_STATUSES + CREATE_CLIENTS_STATUSES_TABLE_STRING); 
		
		insertData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	
	/**
	 * Inserts information about categories into database.
	 * @param db The database.
	 */
	private void insertData(SQLiteDatabase db){
		
		db.execSQL("insert into " + TABLE_METADATA + " values ('ru-RU');");
		
		db.execSQL("insert into  " + TABLE_RELATION_TYPES + " values ('Близкий');");
		db.execSQL("insert into  " + TABLE_RELATION_TYPES + " values ('Знакомый');");
		db.execSQL("insert into  " + TABLE_RELATION_TYPES + " values ('Холодный');");
		
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('Моб. телефон:');");
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('Дом. телефон:');");
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('Раб. телефон:');");
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('Факс:');");
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('E-mail:');");
		db.execSQL("insert into  " + TABLE_CONTACT_TYPES + " values ('Skype:');");
		
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Терминизация', 10);");
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Тренинг', 10);");
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Личная встреча', 10);");
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Презентация', 10);");
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Контракт', 50);");
		db.execSQL("insert into  " + TABLE_STATUSES + " values ('Семинар', 100);");
		
	}

}
