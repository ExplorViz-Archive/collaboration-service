package net.explorviz.extension.vr.message;

public class ResponseMessage extends SendableMessage {
    public ResponseMessage(String event, int nonce) {
        super(event);
        this.nonce = nonce;
    }

    private int nonce;

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    
}
