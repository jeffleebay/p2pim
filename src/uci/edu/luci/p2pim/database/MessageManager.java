package uci.edu.luci.p2pim.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class MessageManager extends ListActivity {
	
	String[] fNames = new String[] { 
			"Jasmine", "Mike", "Peter", "Chris", "Amy", "Susan", "Victor", "Ken",
			"Yao", "Cindy", "Jackson", "Albert", "Elainee", "Henry", "Vanessa", "Linda", "Brian"};
	String[] fStatus = new String[] { 
			"Hi!!!", "Lady Gaga is coming!!","wait a minute", "then I'll give him ur mail ...",
			"just link me to some ", "nice! Thanks!","how much was it?", "sounds great!",
			"This week", "Follow it on Twitter","Today is important ...", "so what?",
			"who's next", "glad to see you","are we going tomorrow?", "really?", "OK"};
	
	private MessageDataSource messageDataSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_msg);

		messageDataSource = new MessageDataSource(this);
		messageDataSource.open();

		List<MyMessage> listOfMessages = messageDataSource.getAllMessages();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<MyMessage> messageAdapter = new ArrayAdapter<MyMessage>(this,android.R.layout.simple_list_item_1, listOfMessages);
		setListAdapter(messageAdapter);
	}

	// Will be called via the onClick attribute of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<MyMessage> messageAdapter = (ArrayAdapter<MyMessage>) getListAdapter();
		MyMessage myMessage = null;
		switch (view.getId()) {
		case R.id.createMessages:
			SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
			for(int i=0;i<fStatus.length;i++){
				String direction = (i%2==0 ? MyMessage.MESSAGE_FROM:MyMessage.MESSAGE_TO);
				String timeStamp = df.format(new Date()).toString();
				String content = fStatus[i];
				myMessage = messageDataSource.createAMessage("Jasmine", direction, timeStamp, content);
				messageAdapter.add(myMessage);		
			}
			break;
		case R.id.deleteAllMessages:
			while (getListAdapter().getCount() > 0) {
				myMessage = (MyMessage) getListAdapter().getItem(0);
				messageDataSource.deleteAMessage(myMessage);
				messageAdapter.remove(myMessage);
			}
			break;
		}
		messageAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		messageDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		messageDataSource.close();
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