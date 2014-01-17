package uci.edu.luci.p2pim;

import java.util.List;

import uci.edu.luci.p2pim.database.MyMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter{
	protected static final String TAG = "MessageAdapter";
	private Context context;
	private List<MyMessage> listOfMessage;
	

	public MessageAdapter(Context context, List<MyMessage> listOfMessage) {
		super();
		this.context = context;
		this.listOfMessage = listOfMessage;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listOfMessage.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listOfMessage.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		MyMessage myMessage = listOfMessage.get(position);
		if(convertView == null || (holder = (ViewHolder)convertView.getTag()).flag != myMessage.getDirection())
		{
			holder = new ViewHolder();
			
			//Log.w(MessageAdapter.class.getName(),message.getDirection());
			if(myMessage.getDirection().endsWith(MyMessage.MESSAGE_FROM))
			{
				holder.flag = MyMessage.MESSAGE_FROM;
				convertView = LayoutInflater.from(context).inflate(R.layout.item_from, null);
			}
			else 
			{
				holder.flag = MyMessage.MESSAGE_TO;
				convertView = LayoutInflater.from(context).inflate(R.layout.item_to, null);
			}
			holder.content = (TextView)convertView.findViewById(R.id.content);
			holder.time = (TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}
		holder.content.setText(myMessage.getContent());
		holder.time.setText(myMessage.getTimeStamp());
		return convertView;
	}
	
	static class ViewHolder
	{
		String flag;
		TextView content;
		TextView time;
	}

}
