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
    public <T> T handleWith(ReceivedMessageHandler<T> handler) {
        return handler.handleAppClosedMessage(this);
    }
}
