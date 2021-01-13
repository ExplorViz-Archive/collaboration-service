package net.explorviz.extension.vr.message;

public abstract class RequestMessage extends ReceivableMessage {
    public RequestMessage(String event) {
        super(event);
    }

    private int nonce;
    
    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

}
