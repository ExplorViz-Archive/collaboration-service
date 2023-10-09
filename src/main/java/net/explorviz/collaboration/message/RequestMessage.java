package net.explorviz.collaboration.message;

/**
 * Super class for all messages the frontend expects a response for.
 *
 * Every request of a client is uniquely identified by a {@code nonce}. The backend includes the
 * same {@code nonce} in the corresponding {@link ResponseMessage}.
 */
public abstract class RequestMessage extends ReceivableMessage {
  private int nonce;

  public RequestMessage(final String event) {
    super(event);
  }

  public int getNonce() {
    return this.nonce;
  }

  public void setNonce(final int nonce) {
    this.nonce = nonce;
  }

  public void sendResponse(final RespondableMessage responseBody) {
    final var senderSession = this.getSenderSession();
    if (senderSession != null) {
      final var response = new ResponseMessage(this.nonce, responseBody);
      senderSession.send(response);
    }
  }
}
