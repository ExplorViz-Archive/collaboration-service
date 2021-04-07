package net.explorviz.vr.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.explorviz.vr.VrSession;

/**
 * Base class for all messages that can be received from the frontend.
 */
public abstract class ReceivableMessage extends VrMessage {
    /**
     * The session of the user who sent this message to the backend.
     */
    @JsonIgnore
    private VrSession senderSession;
	
    public ReceivableMessage(String event) {
        super(event);
    }

    public VrSession getSenderSession() {
		return senderSession;
	}

	public void setSenderSession(VrSession senderSession) {
		this.senderSession = senderSession;
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
    public abstract <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg);
}
