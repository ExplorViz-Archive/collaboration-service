package net.explorviz.extension.vr.message.respondable;

import net.explorviz.extension.vr.message.RespondableMessage;

public class ObjectClosedResponse extends RespondableMessage {
    private static final String EVENT = "object_closed";

    private final boolean isSuccess;

    public ObjectClosedResponse(boolean isSuccess) {
        super(EVENT);
        this.isSuccess = isSuccess;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
