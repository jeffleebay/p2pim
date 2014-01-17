package uci.edu.luci.p2pim;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uci.edu.luci.p2pim.R;

import uci.edu.luci.p2pim.MessageAdapter;
//import uci.edu.luci.p2pim.MessageVo;
import uci.edu.luci.p2pim.database.MyMessage;
import uci.edu.luci.p2pim.database.MessageDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

public class ChatsActivity extends Activity {
	
	private static Handler handler;
	private MyThread updateThread;
	//for view
	private ListView list;
	private Button send;
	private ToggleButton toggle;
	private EditText edit;
	//for messages
	private MessageDataSource messageDataSource;
	private List<MyMessage> listOfMessages;
	private MessageAdapter messageAdapter = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view
        Intent intent = getIntent();
        final String friendName = intent.getStringExtra(FriendsActivity.FRIEND_NAME);
        setTitle(friendName);
        setContentView(R.layout.chat_main);
        
        //set the database
        messageDataSource = new MessageDataSource(this);
        messageDataSource.open();
        listOfMessages = messageDataSource.getAllMessages();
        for(int i=listOfMessages.size()-1;i>=0;i--){
        	if(!friendName.equals(listOfMessages.get(i).getFriendName())){
        		listOfMessages.remove(i);
        	}
        }
        messageAdapter = new MessageAdapter(this, listOfMessages);
        
        
        //initialize the detail
		initWidget();
		list.setAdapter(messageAdapter);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = edit.getText().toString();
				String sendContent;
				SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
				String time = df.format(new Date()).toString();
				System.out.println("time--------" + time);
				if (content != null
						&& (sendContent = content.trim().replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "")
								.replaceAll("\f", "")) != "")
				{
					if(toggle.isChecked())
					{
						messageDataSource.createAMessage(friendName, MyMessage.MESSAGE_FROM, time, sendContent);
						listOfMessages.add(new MyMessage(friendName, MyMessage.MESSAGE_FROM, time, sendContent));
					}
					else
					{
						messageDataSource.createAMessage(friendName, MyMessage.MESSAGE_TO, time, sendContent);
						listOfMessages.add(new MyMessage(friendName, MyMessage.MESSAGE_TO, time, sendContent));
					}
					
					messageAdapter.notifyDataSetChanged();
				}
					edit.setText("");
			}
		});
		
		handler = new Handler() {
		      @Override
			public void handleMessage(Message msg) {
				MyMessage myMessage = null;
				SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
				String direction = MyMessage.MESSAGE_FROM;
				String timeStamp = df.format(new Date()).toString();
				String content = "Feedback content message";
				messageDataSource.createAMessage("Jasmine",direction, timeStamp, content);
				listOfMessages.add(new MyMessage("Jasmine",direction, timeStamp, content));
				messageAdapter.notifyDataSetChanged();
			}
		    };
		//updateThread = (Thread) getLastNonConfigurationInstance();
		updateThread = new MyThread();
	    updateThread.start();
		
	}
	
	static public class MyThread extends Thread {
		@Override
		public void run() {
			try {
				new Thread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Updates the user interface
			handler.sendEmptyMessage(0);
		}
	  }
	
	public void initWidget()
	{
		list = (ListView)findViewById(R.id.list);
		send = (Button)findViewById(R.id.send);
		edit = (EditText)findViewById(R.id.edit);
		toggle = (ToggleButton)findViewById(R.id.toggle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_MsgGenerater:
			//To Do Something
			break;
		}

		return true;
	}
}
