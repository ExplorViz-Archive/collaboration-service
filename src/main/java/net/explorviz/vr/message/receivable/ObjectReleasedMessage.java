package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class ObjectReleasedMessage extends ReceivableMessage {

	public static final String EVENT = "object_released";

	private String objectId;

	public ObjectReleasedMessage() {
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
		return handler.handleObjectReleasedMessage(this, arg);
	}
}
