package net.explorviz.extension.vr.message;

/**
 * Base class for all messages that can be received from the frontend.
 */
public abstract class ReceivedMessage extends VRMessage {
    /**
     * Invokes the correct {@code handle*} method of the given handler.
     * 
     * @param <T> The return type of the handeler's {@code handle*} methods.
     * @param handler The handler whose {@code handle*} method to invoke..
     * @return The return value of the {@code handle*} method.
     */
    public abstract <T> T handleWith(ReceivedMessageHandler<T> handler);
}
