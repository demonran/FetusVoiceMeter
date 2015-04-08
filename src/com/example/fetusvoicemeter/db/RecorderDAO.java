package com.example.fetusvoicemeter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fetusvoicemeter.entity.RecorderEntity;
import com.example.fetusvoicemeter.utils.Utils;

public class RecorderDAO {

	private static RecorderDAO instance;

	private DBHelper helper;

	private RecorderDAO(Context cxt) {
		helper = new DBHelper(cxt);
		craeteTable();

	}

	public static RecorderDAO createChatMsgDAO(Context cxt) {
		if (instance == null) {
			instance = new RecorderDAO(cxt);
		}
		return instance;
	}

	/*
	 * private String name;
	 * 
	 * private Integer[] beatValues;
	 * 
	 * private Float[] beatTimes;
	 * 
	 * private long durationTime;
	 * 
	 * private long startTime;
	 */
	public void craeteTable() {
		SQLiteDatabase db = helper.getReadableDatabase();
		String check = "SELECT COUNT(*) FROM sqlite_master where type='table' and name=?";

		Cursor cursor = db.rawQuery(check,
				new String[] { "record" });
		if (cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count > 0) {
				return;
			}
		}

		db = helper.getWritableDatabase();
		String sql = "create table record(" + "name varchar(20),"
				+ "beatValues varchar(20)," + "beatTimes varchar(20),"
				+ "durationTime timestamp," + "startTime timestamp)";
		db.execSQL(sql);

	}

	// 插入操作
	public void insertData(RecorderEntity entity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", entity.getName());
		values.put("beatValues", Utils.arrayToString(entity.getBeatValues()));
		values.put("beatTimes", Utils.arrayToString(entity.getBeatTimes()));
		values.put("durationTime", entity.getDurationTime());
		values.put("startTime", entity.getStartTime());
		db.insert("record", null, values);
	}

	// 插入操作
	public RecorderEntity getData(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("record", new String[] { "beatValues",
				"beatTimes", "durationTime", "startTime" }, "name=?",
				new String[] { name }, null, null, null);
		if(cursor.moveToNext())
		{
			String beatValues = cursor.getString(0);
			String beatTimes = cursor.getString(1);
			long durationTime = cursor.getLong(2);
			long startTime = cursor.getLong(3);
			return new RecorderEntity(name, Utils.stringToIntegerArray(beatValues),
					Utils.stringToFloatArray(beatTimes), durationTime, startTime);
		}
		
		return null;
	}
}
