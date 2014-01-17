package uci.edu.luci.p2pim.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MessageDataSource {
	// Database fields
		private SQLiteDatabase database;
		private MySQLiteHelper dbHelper;
		private String[] allColumns = { 
				MySQLiteHelper.TABLE_MESSAGE_COLUMN_ID,
				MySQLiteHelper.TABLE_MESSAGE_COLUMN_DIRECTION, 
				MySQLiteHelper.TABLE_MESSAGE_COLUMN_TIMESTAMP, 
				MySQLiteHelper.TABLE_MESSAGE_COLUMN_CONTENT,
				MySQLiteHelper.TABLE_MESSAGE_COLUMN_FRIENDNAME };

		public MessageDataSource(Context context) {
			dbHelper = new MySQLiteHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public MyMessage createAMessage(String friendName, String direction, String timeStamp, String content) {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.TABLE_MESSAGE_COLUMN_DIRECTION, direction);
			values.put(MySQLiteHelper.TABLE_MESSAGE_COLUMN_TIMESTAMP, timeStamp);
			values.put(MySQLiteHelper.TABLE_MESSAGE_COLUMN_CONTENT, content);
			values.put(MySQLiteHelper.TABLE_MESSAGE_COLUMN_FRIENDNAME, friendName);
			long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGE, null,values);
			Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGE, allColumns, 
					MySQLiteHelper.TABLE_MESSAGE_COLUMN_ID + " = " + insertId, null,null, null, null);
			cursor.moveToFirst();
			MyMessage newMessage = cursorToMessage(cursor);
			cursor.close();
			
			
			String strFilter = MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_NAME + "=" + "'" + friendName + "'";

			ContentValues valueC = new ContentValues();

			valueC.put(MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_CHAT, content);

			database.update(MySQLiteHelper.TABLE_FRIENDLIST, valueC, strFilter, null);
			//System.out.printf("");

			//database.execSQL("UPDATE "+ MySQLiteHelper.TABLE_FRIENDLIST + " SET "+ MySQLiteHelper.TABLE_FRIENDLIST_COLUMN_CHAT
//		 	+ " = " + content + " WHERE " + MySQLiteHelper.TABLE_MESSAGE_COLUMN_FRIENDNAME
//			+ " = '" + friendName +"'", null);
						
			return newMessage;
		}

		public void deleteAMessage(MyMessage myMessage) {
			long id = myMessage.getId();
			System.out.println("A message deleted with id: " + id);
			database.delete(MySQLiteHelper.TABLE_MESSAGE,
					MySQLiteHelper.TABLE_MESSAGE_COLUMN_ID + " = " + id, null);
		}

		public List<MyMessage> getAllMessages() {
			List<MyMessage> listOfMessages = new ArrayList<MyMessage>();

			Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGE,allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				MyMessage myMessage = cursorToMessage(cursor);
				listOfMessages.add(myMessage);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return listOfMessages;
		}

		private MyMessage cursorToMessage(Cursor cursor) {
			MyMessage myMessage = new MyMessage();
			myMessage.setId(cursor.getLong(0));
			myMessage.setDirection(cursor.getString(1));
			myMessage.setTimeStamp(cursor.getString(2));
			myMessage.setContent(cursor.getString(3));
			myMessage.setFriendName(cursor.getString(4));
			return myMessage;
		}
}
