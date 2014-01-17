package uci.edu.luci.p2pim.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "p2pim.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_FRIENDLIST = "friendList";
	public static final String TABLE_FRIENDLIST_COLUMN_ID = "_id";
	public static final String TABLE_FRIENDLIST_COLUMN_NAME = "name";
	public static final String TABLE_FRIENDLIST_COLUMN_CHAT = "chat";
	public static final String TABLE_FRIENDLIST_COLUMN_QUEUE = "queue";
	

	private static final String DATABASE_CREATE_TABLE_FRIENDLIST = "CREATE TABLE "
			+ TABLE_FRIENDLIST
			+ "("
			+ TABLE_FRIENDLIST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TABLE_FRIENDLIST_COLUMN_NAME + " TEXT NOT NULL, " 
			+ TABLE_FRIENDLIST_COLUMN_CHAT + " TEXT NULL, "
			+ TABLE_FRIENDLIST_COLUMN_QUEUE + " INTEGER);";
	
	public static final String TABLE_MESSAGE = "message";
	public static final String TABLE_MESSAGE_COLUMN_ID = "_id";
	public static final String TABLE_MESSAGE_COLUMN_DIRECTION = "direction";
	public static final String TABLE_MESSAGE_COLUMN_TIMESTAMP = "timeStamp";
	public static final String TABLE_MESSAGE_COLUMN_CONTENT = "content";
	public static final String TABLE_MESSAGE_COLUMN_FRIENDNAME = "friendName";
	
	private static final String DATABASE_CREATE_TABLE_MESSAGE = "CREATE TABLE "
			+ TABLE_MESSAGE
			+ "("
			+ TABLE_MESSAGE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TABLE_MESSAGE_COLUMN_DIRECTION + " TEXT NOT NULL, " 
			+ TABLE_MESSAGE_COLUMN_TIMESTAMP + " TEXT NOT NULL, "
			+ TABLE_MESSAGE_COLUMN_CONTENT + " TEXT NOT NULL, "
			+ TABLE_MESSAGE_COLUMN_FRIENDNAME + " TEXT NOT NULL);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TABLE_FRIENDLIST);
		database.execSQL(DATABASE_CREATE_TABLE_MESSAGE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDLIST);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_TABLE_MESSAGE);
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public synchronized void close() {
		super.close();
	}

}
