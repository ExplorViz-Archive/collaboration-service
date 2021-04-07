package net.explorviz.vr.message.respondable;

import net.explorviz.vr.message.RespondableMessage;

public class ObjectClosedResponse extends RespondableMessage {
	private static final String EVENT = "object_closed";

	private final boolean isSuccess;

	public ObjectClosedResponse(boolean isSuccess) {
		super(EVENT);
		this.isSuccess = isSuccess;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}
}
