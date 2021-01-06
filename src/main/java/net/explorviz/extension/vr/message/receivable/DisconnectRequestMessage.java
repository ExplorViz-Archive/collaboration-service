package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class DisconnectRequestMessage extends ReceivedMessage {

    public static final String EVENT = "disconnect_request";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleDisconnectRequestMessage(this, arg);
    }

}
