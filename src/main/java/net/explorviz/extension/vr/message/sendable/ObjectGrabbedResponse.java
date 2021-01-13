package net.explorviz.extension.vr.message.sendable;

import net.explorviz.extension.vr.message.ResponseMessage;

public class ObjectGrabbedResponse extends ResponseMessage {
    private static final String EVENT = "object_grabbed";

    private final String id;
    private final boolean success;

    public ObjectGrabbedResponse(int nonce, String id, boolean success) {
        super(EVENT, nonce);
        this.id = id;
        this.success = success;
    }
    
    public String getId() {
        return id;
    }

    public boolean getIsSuccess() {
        return success;
    }
}
