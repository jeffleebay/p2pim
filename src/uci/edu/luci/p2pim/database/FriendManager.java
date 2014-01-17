package uci.edu.luci.p2pim.database;

import java.util.List;
import java.util.Random;

import uci.edu.luci.p2pim.R;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

@SuppressLint("SimpleDateFormat")
public class FriendManager extends ListActivity {
	
	String[] fNames = new String[] { 
			"Jasmine", "Mike", "Peter", "Chris", "Amy", "Susan", "Victor", "Ken",
			"Yao", "Cindy", "Jackson", "Albert", "Elainee", "Henry", "Vanessa", "Linda", "Brian"};
	String[] fStatus = new String[] { 
			"Hi!!!", "Lady Gaga is coming!!","wait a minute", "then I'll give him ur mail ...",
			"just link me to some ", "nice! Thanks!","how much was it?", "sounds great!",
			"This week", "Follow it on Twitter","Today is important ...", "so what?",
			"who's next", "glad to see you","are we going tomorrow?", "really?", "OK"};
	
	private FriendDataSource friendDataSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_frnd);

		friendDataSource = new FriendDataSource(this);
		friendDataSource.open();


		List<Friend> listOfFriends = friendDataSource.getAllFriends();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<Friend> friendAdapter = new ArrayAdapter<Friend>(this,android.R.layout.simple_list_item_1, listOfFriends);
		setListAdapter(friendAdapter);
	}

	// Will be called via the onClick attribute of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Friend> friendAdapter = (ArrayAdapter<Friend>) getListAdapter();
		Friend friend = null;

		switch (view.getId()) {
		case R.id.addAFriend:
			int nextInt = new Random().nextInt(fNames.length);
			int nextInt2 = new Random().nextInt(fStatus.length);
			friend = friendDataSource.createAFriend(fNames[nextInt],"");
			friendAdapter.add(friend);
			//Toast.makeText(getApplicationContext(),"You selected : " + friendName.getChatLog(),Toast.LENGTH_SHORT).show();
			break;
		case R.id.deleteAFriend:
			if (getListAdapter().getCount() > 0) {
				friend = (Friend) getListAdapter().getItem(0);
				friendDataSource.deleteAFriend(friend);
				friendAdapter.remove(friend);
			}
			break;
		case R.id.addLotsfriends:
			for(int i=0;i<fNames.length;i++){
				//friend = friendDataSource.createAFriend(fNames[i], fStatus[i]);
				friend = friendDataSource.createAFriend(fNames[i], "");	
				friendAdapter.add(friend);
			}
			break;
		case R.id.deleteAllFriends:
			while (getListAdapter().getCount() > 0) {
				friend = (Friend) getListAdapter().getItem(0);
				friendDataSource.deleteAFriend(friend);
				friendAdapter.remove(friend);
			}
			break;
		
		}
		friendAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		friendDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		friendDataSource.close();
		super.onPause();
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