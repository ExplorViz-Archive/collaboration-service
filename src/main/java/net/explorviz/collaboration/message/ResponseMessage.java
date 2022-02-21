package net.explorviz.collaboration.message;

public class ResponseMessage extends SendableMessage {
  public static final String EVENT = "response";

  private final int nonce;
  private final RespondableMessage response;

  public ResponseMessage(final int nonce, final RespondableMessage response) {
    super(EVENT);
    this.nonce = nonce;
    this.response = response;
  }

  public int getNonce() {
    return this.nonce;
  }

  public RespondableMessage getResponse() {
    return this.response;
  }
}
