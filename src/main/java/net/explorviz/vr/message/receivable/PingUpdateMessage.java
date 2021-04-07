package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class PingUpdateMessage extends ReceivableMessage {

	public PingUpdateMessage() {
		super(EVENT);
	}

	public static final String EVENT = "ping_update";

	private int controllerId;

	private boolean isPinging;

	public int getControllerId() {
		return controllerId;
	}

	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}

	public boolean getIsPinging() {
		return isPinging;
	}

	public void setPinging(boolean isPinging) {
		this.isPinging = isPinging;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handlePingUpdateMessage(this, arg);
	}
}
