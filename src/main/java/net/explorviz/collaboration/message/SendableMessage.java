package net.explorviz.collaboration.message;

/**
 * Super class for all messages that are allowed to be sent from the backend to the frontend.
 */
public abstract class SendableMessage extends VrMessage {
  public SendableMessage(final String event) {
    super(event);
  }
}
