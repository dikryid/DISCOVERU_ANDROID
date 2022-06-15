package com.app.bound.discoveru.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

	// TABLE INFORMATTION
		public static final String TABLE_MEMBER = "DISCOVERU";
		public static final String ID = "id";
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String EMAIL_ADDRESS = "email_address";
		public static final String PASSWORD = "password";
		public static final String AGE = "age";
	    public static final String GENDER = "gender";

		// DATABASE INFORMATION
		static final String DB_NAME = "DISCOVERUX.DB";
		static final int DB_VERSION = 1;
		
	// TABLE CREATION STATEMENT
	private static final String CREATE_TABLE = "create table "
			+ TABLE_MEMBER + "(" + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FIRST_NAME + " VARCHAR(50) NOT NULL, "
			+ LAST_NAME + " VARCHAR(50), "
			+ EMAIL_ADDRESS + " VARCHAR(100) NOT NULL, "
			+ PASSWORD + " VARCHAR(50) NOT NULL, "
			+ AGE + " INT(2) DEFAULT '0', "
			+ GENDER + " VARCHAR(6));";

	public DBhelper(Context context) {
		super(context, DB_NAME, null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
		onCreate(db);
	}
}