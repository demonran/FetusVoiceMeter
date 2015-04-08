package com.example.fetusvoicemeter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	 private final static String DB_NAME ="fetusvoicemeter.db";//数据库名
	 private final static int VERSION = 1;//版本号

	 //为了每次构造时不用传入dbName和版本号，自己得新定义一个构造方法
	 public DBHelper(Context cxt){
	  this(cxt, DB_NAME, null, VERSION);//调用上面的构造方法
	 }

	 //版本变更时
	 public DBHelper(Context cxt,int version) {
	  this(cxt,DB_NAME,null,version);
	 }

	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  String sql  = "update student ....";//自己的Update操作
		  db.execSQL(sql);
	}
	

}
