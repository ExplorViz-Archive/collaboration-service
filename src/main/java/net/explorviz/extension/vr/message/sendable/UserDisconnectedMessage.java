package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.SendableMessage;

public class UserDisconnectedMessage extends SendableMessage {
    public static final String EVENT = "user_disconnect";

    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
