package net.explorviz.extension.vr.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserControllersMessage extends VRMessage {

    public static final String EVENT = "user_controllers";

    private String[] connect;
    private String[] disconnect;

    @JsonCreator
    public UserControllersMessage(@JsonProperty("connect") String[] connect,
            @JsonProperty("disconnect") String[] disconnect) {
        super(EVENT);
        this.connect = connect;
        this.disconnect = disconnect;
    }

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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(connect);
        result = prime * result + Arrays.hashCode(disconnect);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserControllersMessage other = (UserControllersMessage) obj;
        if (!Arrays.equals(connect, other.connect)) {
            return false;
        }
        if (!Arrays.equals(disconnect, other.disconnect)) {
            return false;
        }
        return true;
    }
}
