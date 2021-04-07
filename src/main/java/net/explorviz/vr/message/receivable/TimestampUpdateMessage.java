package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class TimestampUpdateMessage extends ReceivableMessage {
	public static final String EVENT = "timestamp_update";

	private long timestamp;

	public TimestampUpdateMessage() {
		super(EVENT);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleTimestampUpdateMessage(this, arg);
	}
}
