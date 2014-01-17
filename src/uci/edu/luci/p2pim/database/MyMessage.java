package uci.edu.luci.p2pim.database;

public class MyMessage {
	
	public static final String MESSAGE_FROM = "in";
	public static final String MESSAGE_TO = "out";
	
	private long id;
	private String direction;
	private String timeStamp;
	private String content;
	private String friendName;
	
	public MyMessage() {
		super();
	}
	
	public MyMessage(String friendName, String direction, String timeStamp, String content) {
		super();
		this.friendName = friendName;
		this.direction = direction;
		this.timeStamp = timeStamp;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirection() {
		return direction;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendName() {
		return friendName;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return content;
	}
}
