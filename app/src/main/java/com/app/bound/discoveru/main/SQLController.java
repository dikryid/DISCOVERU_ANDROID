package com.app.bound.discoveru.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

	private DBhelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new DBhelper(ourcontext);
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	public long insertData(String first_name, String last_name, String email_address, String password, int age, String gender) {
		database = dbhelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(DBhelper.FIRST_NAME, first_name);
		cv.put(DBhelper.LAST_NAME, last_name);
		cv.put(DBhelper.EMAIL_ADDRESS, email_address);
		cv.put(DBhelper.PASSWORD, password);
		cv.put(DBhelper.AGE, age);
		cv.put(DBhelper.GENDER, gender);
		long result = database.insert(DBhelper.TABLE_MEMBER, null, cv);

		return result;
	}

	public Cursor readData() {
		database = dbhelper.getReadableDatabase();

		String[] allColumns = new String[] { DBhelper.ID,
				DBhelper.FIRST_NAME, DBhelper.LAST_NAME, DBhelper.EMAIL_ADDRESS, DBhelper.PASSWORD,
				DBhelper.AGE, DBhelper.GENDER };
		Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public long searchDatabyEmail(String email_address) {
		database = dbhelper.getReadableDatabase();
		long id = -1;

		try {
			String[] allColumns = new String[]{DBhelper.ID,
					DBhelper.FIRST_NAME, DBhelper.LAST_NAME, DBhelper.EMAIL_ADDRESS, DBhelper.PASSWORD,
					DBhelper.AGE, DBhelper.GENDER};
			Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, DBhelper.EMAIL_ADDRESS + " like '%" + email_address + "%'" ,
					null, null, null, null);
			if (c != null) {
				c.moveToFirst();
				id = c.getLong(0);
			}
		}catch (Exception e){
			return -1;
		}

		return id;
	}


	public Cursor getDatabyId(long id) {
		database = dbhelper.getReadableDatabase();

		try {
			String[] allColumns = new String[]{DBhelper.ID,
					DBhelper.FIRST_NAME, DBhelper.LAST_NAME, DBhelper.EMAIL_ADDRESS, DBhelper.PASSWORD,
					DBhelper.AGE, DBhelper.GENDER};
			Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, DBhelper.ID + " = " + String.valueOf(id) ,
					null, null, null, null);
			if (c != null) {
				c.moveToFirst();
				return c;
			}
		}catch (Exception e){
			return null;
		}

		return null;
	}

	public long searchUserbyEmail(String email_address, String password) {
		database = dbhelper.getReadableDatabase();
		long id = -1;

		try {
			String[] allColumns = new String[]{DBhelper.ID,
					DBhelper.FIRST_NAME, DBhelper.LAST_NAME, DBhelper.EMAIL_ADDRESS, DBhelper.PASSWORD,
					DBhelper.AGE, DBhelper.GENDER};
			Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, DBhelper.EMAIL_ADDRESS + " like '%" + email_address + "%'" ,
					null, null, null, null);
			if (c != null) {
				c.moveToFirst();
				id = c.getLong(0);
				String pass = c.getString(4);

				String passString = AES192.decrypt(pass);

				if(password.equals(passString)){
					return id;
				} else {
					return -1;
				}
			}
		}catch (Exception e){
			return -1;
		}

		return id;
	}

	public int updateData(long id, String first_name, String last_name, String email_address, String password, int age, String gender) {
		database = dbhelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(DBhelper.FIRST_NAME, first_name);
		cv.put(DBhelper.LAST_NAME, last_name);
		cv.put(DBhelper.EMAIL_ADDRESS, email_address);
		cv.put(DBhelper.AGE, age);
		cv.put(DBhelper.GENDER, gender);
		int i = database.update(DBhelper.TABLE_MEMBER, cv,
				DBhelper.ID + " = " + id, null);
		return i;
	}

	public void deleteData(long id) {
		database = dbhelper.getWritableDatabase();

		database.delete(DBhelper.TABLE_MEMBER, DBhelper.ID + "="
				+ id, null);
	}

}// outer class end
