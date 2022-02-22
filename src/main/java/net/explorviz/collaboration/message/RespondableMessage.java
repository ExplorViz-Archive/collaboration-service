package net.explorviz.collaboration.message;

/**
 * Super class of all messages that can be sent as a response to a {@link RequestMessage}.
 *
 * Responses are must be embedded in a {@link ResponseMessage} in order to be sent to a client.
 */
public abstract class RespondableMessage extends VrMessage {
  public RespondableMessage(final String event) {
    super(event);
  }
}
