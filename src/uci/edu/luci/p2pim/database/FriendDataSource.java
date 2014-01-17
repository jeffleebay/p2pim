package uci.edu.luci.p2pim.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FriendDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { 
			MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_ID,
			MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_NAME, 
			MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_CHAT, 
			MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_QUEUE };

	public FriendDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Friend createAFriend(String friendName, String chatLog) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_NAME, friendName);
		values.put(MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_CHAT, chatLog);
		//values.put(MySQLiteHelper.COLUMN_QUEUE, chatLog);
		long insertId = database.insert(MySQLiteHelper.TABLE_FRIENDLIST, null,values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_FRIENDLIST, allColumns, 
				MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_ID + " = " + insertId, null,null, null, null);
		cursor.moveToFirst();
		Friend newFriend = cursorToFriend(cursor);
		cursor.close();
		return newFriend;
	}

	public void deleteAFriend(Friend friend) {
		long id = friend.getId();
		System.out.println("A friend deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_FRIENDLIST,
				MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_ID + " = " + id, null);
	}

	public List<Friend> getAllFriends() {
		List<Friend> listOfFriends = new ArrayList<Friend>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_FRIENDLIST,allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Friend friend = cursorToFriend(cursor);
			listOfFriends.add(friend);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return listOfFriends;
	}

	private Friend cursorToFriend(Cursor cursor) {
		Friend friend = new Friend();
		friend.setId(cursor.getLong(0));
		friend.setFriendName(cursor.getString(1));
		friend.setChatLog(cursor.getString(2));
		//friend.setQueue(cursor.getInt(3));
		return friend;
	}
}
