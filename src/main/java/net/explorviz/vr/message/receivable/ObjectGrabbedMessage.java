package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessageHandler;
import net.explorviz.vr.message.RequestMessage;

public class ObjectGrabbedMessage extends RequestMessage {

	public static final String EVENT = "object_grabbed";

	private String objectId;

	public ObjectGrabbedMessage() {
		super(EVENT);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleObjectGrabbedMessage(this, arg);
	}
}
