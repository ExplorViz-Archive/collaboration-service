package net.explorviz.extension.vr.messages;

public class UserControllersMessage extends VRMessage {

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

}
