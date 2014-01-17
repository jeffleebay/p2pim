package uci.edu.luci.p2pim.database;

public class Friend {
	private long id;
	private String friendName;
	private String chatLog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendName() {
		return friendName;
	}

	public void setChatLog(String chatLog) {
		this.chatLog = chatLog;
	}
	public String getChatLog() {
		return chatLog;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return friendName;
	}
}
