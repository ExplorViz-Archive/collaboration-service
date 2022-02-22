package net.explorviz.collaboration.message;

/**
 * Super class for all messages that are allowed to be broadcasted to all connected clients.
 */
public class BroadcastableMessage extends SendableMessage {
  public BroadcastableMessage(final String event) {
    super(event);
  }
}
