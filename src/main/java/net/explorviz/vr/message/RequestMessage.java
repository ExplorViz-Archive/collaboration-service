package net.explorviz.vr.message;

/**
 * Super class for all messages the frontend expects a response for.
 * 
 * Every request of a client is uniquely identified by a {@code nonce}. The
 * backend includes the same {@code nonce} in the corresponding
 * {@link ResponseMessage}.
 */
public abstract class RequestMessage extends ReceivableMessage {
	private int nonce;

	public RequestMessage(String event) {
		super(event);
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public int getNonce() {
		return nonce;
	}

	public void sendResponse(RespondableMessage responseBody) {
		final var senderSession = getSenderSession();
		if (senderSession != null) {
			final var response = new ResponseMessage(nonce, responseBody);
			senderSession.send(response);
		}
	}
}
