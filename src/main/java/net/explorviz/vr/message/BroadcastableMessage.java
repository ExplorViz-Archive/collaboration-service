package net.explorviz.vr.message;

/**
 * Super class for all messages that are allowed to be broadcasted to all
 * connected clients.
 */
public class BroadcastableMessage extends SendableMessage {
	public BroadcastableMessage(String event) {
		super(event);
	}
}
