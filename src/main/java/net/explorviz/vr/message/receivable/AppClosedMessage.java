package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessageHandler;
import net.explorviz.vr.message.RequestMessage;

public class AppClosedMessage extends RequestMessage {

	public static final String EVENT = "app_closed";

	private String appId;

	public AppClosedMessage() {
		super(EVENT);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleAppClosedMessage(this, arg);
	}
}
