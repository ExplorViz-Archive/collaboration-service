package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class AppClosedMessage extends ReceivableMessage {

    public static final String EVENT = "app_closed";

    private String appID;

    public AppClosedMessage() {
        super(EVENT);
    }

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
