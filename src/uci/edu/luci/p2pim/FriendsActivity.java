package uci.edu.luci.p2pim;

import java.util.List;

import uci.edu.luci.p2pim.R;
import uci.edu.luci.p2pim.database.ContactManager;
import uci.edu.luci.p2pim.database.FriendManager;
import uci.edu.luci.p2pim.database.MessageManager;
import uci.edu.luci.p2pim.database.Friend;
import uci.edu.luci.p2pim.database.FriendDataSource;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FriendsActivity extends Activity {

	public final static String FRIEND_NAME = "uci.edu.luci.p2pim.MESSAGECARRIER";

	int resIds = R.drawable.head;

	private static Handler handler;
	private MyThread updateThread;
	private FriendDataSource friendDatasource;
	List<Friend> listOfFriendList;
	FriendAdapter adapter = null;

	ListView friendlistView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		friendDatasource = new FriendDataSource(this);
		friendDatasource.open();
		listOfFriendList = friendDatasource.getAllFriends();

		// set the view
		setContentView(R.layout.activity_main);
		friendlistView = (ListView) findViewById(R.id.listViewOnMainActivity);
		adapter = new FriendAdapter(this);
		friendlistView.setAdapter(adapter);
		friendlistView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String item = listOfFriendList.get(position)
								.getFriendName();
						StartChatRoom(item);
						// Toast.makeText(getApplicationContext(),"You selected : "
						// + item,Toast.LENGTH_SHORT).show();
					}
				});
		
//		handler = new Handler() {
//		      @Override
//		      public void handleMessage(Message msg) {
//		    	friendDatasource.open();
//		  		listOfFriendList = friendDatasource.getAllFriends();
//		  		friendlistView.setAdapter(adapter);
//		      }
//		    };
//		//updateThread = (Thread) getLastNonConfigurationInstance();
//	    updateThread.start();
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		friendDatasource.open();
		listOfFriendList = friendDatasource.getAllFriends();
		friendlistView.setAdapter(adapter);
	}
	
	static public class MyThread extends Thread {
		@Override
		public void run() {
			try {
				new Thread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Updates the user interface
			handler.sendEmptyMessage(0);
		}
	  }

	public void StartChatRoom(String friendsName) {
		Intent intentI = new Intent(this, ChatsActivity.class);
		intentI.putExtra(FRIEND_NAME, friendsName);
		startActivity(intentI);
	}

	public class FriendAdapter extends BaseAdapter {
		private LayoutInflater myInflater;

		public FriendAdapter(Context c) {
			myInflater = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			return listOfFriendList.size();
		}

		@Override
		public Object getItem(int position) {
			return listOfFriendList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = myInflater.inflate(R.layout.friend_template, null);
			ImageView imgLogo = (ImageView) convertView
					.findViewById(R.id.imgLogo);
			TextView txtName = ((TextView) convertView
					.findViewById(R.id.txtName));
			TextView txtStatus = ((TextView) convertView
					.findViewById(R.id.txtStatus));

			imgLogo.setImageResource(resIds);
			txtName.setText(listOfFriendList.get(position).getFriendName());
			txtStatus.setText(listOfFriendList.get(position).getChatLog());
			return convertView;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_FrndMngr:
			Intent intentI = new Intent(this, FriendManager.class);
			startActivity(intentI);
			break;
		case R.id.action_MsgMngr:
			Intent intentJ = new Intent(this, MessageManager.class);
			startActivity(intentJ);
			break;
		case R.id.action_ContMngr:
			Intent intentK = new Intent(this, ContactManager.class);
			startActivity(intentK);
			break;
		}

		return true;
	}

}
