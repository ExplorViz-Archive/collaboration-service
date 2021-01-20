package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.RespondableMessage;

public class MenuDetachedResponse extends RespondableMessage {
    private static final String EVENT = "menu_detached";

    private final String objectId;

    public MenuDetachedResponse(String objectId) {
        super(EVENT);
        this.objectId = objectId;
    }
    
    public String getObjectId() {
        return objectId;
    }

}
