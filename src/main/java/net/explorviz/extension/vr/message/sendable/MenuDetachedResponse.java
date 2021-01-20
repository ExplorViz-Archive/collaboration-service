package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.ResponseMessage;

public class MenuDetachedResponse extends ResponseMessage {
    private static final String EVENT = "menu_detached";

    private final String objectId;

    public MenuDetachedResponse(int nonce, String objectId) {
        super(EVENT, nonce);
        this.objectId = objectId;
    }
    
    public String getObjectId() {
        return objectId;
    }

}
