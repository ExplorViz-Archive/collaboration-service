package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class UserControllerDisconnectMessage extends ReceivableMessage {
	public static final String EVENT = "user_controller_disconnect";

	private int controllerId;

	public UserControllerDisconnectMessage() {
		super(EVENT);
	}

	public int getControllerId() {
		return controllerId;
	}

	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleUserControllerDisconnectMessage(this, arg);
	}
}
