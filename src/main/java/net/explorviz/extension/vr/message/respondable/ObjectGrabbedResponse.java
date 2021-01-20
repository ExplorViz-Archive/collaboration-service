package net.explorviz.extension.vr.message.respondable;

import net.explorviz.extension.vr.message.RespondableMessage;

public class ObjectGrabbedResponse extends RespondableMessage {
    private static final String EVENT = "object_grabbed";

    private final boolean isSuccess;

    public ObjectGrabbedResponse(boolean isSuccess) {
        super(EVENT);
        this.isSuccess = isSuccess;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
