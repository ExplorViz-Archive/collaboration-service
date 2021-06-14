package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class HighlightingUpdateMessage extends ReceivableMessage {

	public static final String EVENT = "highlighting_update";

	private String appId;
	private String entityType;
	private String entityId;
	private boolean isHighlighted;

	public HighlightingUpdateMessage() {
		super(EVENT);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public boolean getIsHighlighted() {
		return isHighlighted;
	}

	public void setIsHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	public String toString() {
		return "appId: " + appId + ", entityId: " + entityId +
				", entityType: " + entityType + ", isHighlighted: " + isHighlighted;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleHighlightingUpdateMessage(this, arg);
	}
}
