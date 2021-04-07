package net.explorviz.vr.message.sendable;

import net.explorviz.vr.message.BroadcastableMessage;

public class UserDisconnectedMessage extends BroadcastableMessage {
	public static final String EVENT = "user_disconnect";

	private String id;

	public UserDisconnectedMessage() {
		super(EVENT);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
