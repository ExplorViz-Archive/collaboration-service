package net.explorviz.extension.vr.message;

/**
 * Base class for all messages that can be received from the frontend.
 */
public abstract class ReceivableMessage extends VRMessage {
    public ReceivableMessage(String event) {
        super(event);
    }

    /**
     * Invokes the correct {@code handle*} method of the given handler.
     * 
     * @param <R>     The return type of the handeler's {@code handle*} methods.
     * @param <A>     The type for additional arguments of the handeler's
     *                {@code handle*} methods.
     * @param handler The handler whose {@code handle*} method to invoke.
     * @param arg     An additional argument to pass to the handlers {@code handle*}
     *                method.
     * @return The return value of the {@code handle*} method.
     */
    public abstract <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg);
}
