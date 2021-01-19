package net.explorviz.extension.vr.message;

public class ResponseMessage extends SendableMessage {
    public static final String EVENT = "response";

    private int nonce;
    private RespondableMessage response;
    
    public ResponseMessage(int nonce, RespondableMessage response) {
        super(EVENT);
        this.nonce = nonce;
        this.response = response;
    }

    public int getNonce() {
        return nonce;
    }

    public RespondableMessage getResponse() {
        return response;
    }
}
