package net.explorviz.vr.message;

/**
 * Super class for all messages that are allowed to be sent from the backend to
 * the frontend.
 */
public abstract class SendableMessage extends VrMessage {
	public SendableMessage(String event) {
		super(event);
	}
}
