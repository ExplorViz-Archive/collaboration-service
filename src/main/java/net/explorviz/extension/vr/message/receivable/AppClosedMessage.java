package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class AppClosedMessage extends ReceivedMessage {

    public static final String EVENT = "app_closed";

    private String appID;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleAppClosedMessage(this, arg);
    }
}
