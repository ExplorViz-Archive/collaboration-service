package net.explorviz.vr.message;

/**
 * Super class of all messages that can be sent as a response to a
 * {@link RequestMessage}.
 * 
 * Responses are must be embedded in a {@link ResponseMessage} in order to be
 * sent to a client.
 */
public abstract class RespondableMessage extends VrMessage {
	public RespondableMessage(String event) {
		super(event);
	}
}
