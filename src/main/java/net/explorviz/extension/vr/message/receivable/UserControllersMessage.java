package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;

public class UserControllersMessage extends ReceivedMessage {

    public static final String EVENT = "user_controllers";

    public static class Controllers {
        private String controller1;
        private String controller2;

        public String getController1() {
            return controller1;
        }

        public void setController1(String controller1) {
            this.controller1 = controller1;
        }

        public String getController2() {
            return controller2;
        }

        public void setController2(String controller2) {
            this.controller2 = controller2;
        }

    }

    private Controllers connect;
    private Controllers disconnect;

    public Controllers getConnect() {
        return connect;
    }

    public void setConnect(Controllers connect) {
        this.connect = connect;
    }

    public Controllers getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(Controllers disconnect) {
        this.disconnect = disconnect;
    }

    @Override
    public <R, A> R handleWith(ReceivedMessageHandler<R, A> handler, A arg) {
        return handler.handleUserControllersMessage(this, arg);
    }
}
