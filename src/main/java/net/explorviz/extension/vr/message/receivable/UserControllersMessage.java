package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class UserControllersMessage extends ReceivedMessage {

    public static final String EVENT = "user_controllers";

    private String[] connect;
    private String[] disconnect;

    public String[] getConnect() {
        return connect;
    }

    public void setConnect(String[] connect) {
        this.connect = connect;
    }

    public String[] getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(String[] disconnect) {
        this.disconnect = disconnect;
    }

    @Override
    public <T> T handleWith(ReceivedMessageHandler<T> handler) {
        return handler.handleUserControllersMessage(this);
    }
}
