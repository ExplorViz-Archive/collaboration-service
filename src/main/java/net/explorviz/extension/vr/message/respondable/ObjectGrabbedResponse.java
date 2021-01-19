package net.explorviz.extension.vr.message.respondable;

import net.explorviz.extension.vr.message.RespondableMessage;

public class ObjectGrabbedResponse extends RespondableMessage {
    private static final String EVENT = "object_grabbed";

    private final String id;
    private final boolean success;

    public ObjectGrabbedResponse(String id, boolean success) {
        super(EVENT);
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
