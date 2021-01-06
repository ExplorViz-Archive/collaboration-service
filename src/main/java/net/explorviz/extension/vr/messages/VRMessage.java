package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for all messages that are exchanged via the websocket connection
 * between the frontend and VR service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event", visible = true)
@JsonSubTypes({ @Type(value = AppClosedMessage.class, name = AppClosedMessage.EVENT),
        @Type(value = AppGrabbedMessage.class, name = AppGrabbedMessage.EVENT),
        @Type(value = AppOpenedMessage.class, name = AppOpenedMessage.EVENT),
        @Type(value = AppReleasedMessage.class, name = AppReleasedMessage.EVENT),
        @Type(value = AppTranslatedMessage.class, name = AppTranslatedMessage.EVENT),
        @Type(value = ComponentUpdateMessage.class, name = ComponentUpdateMessage.EVENT),
        @Type(value = HightlightingUpdateMessage.class, name = HightlightingUpdateMessage.EVENT),
        @Type(value = LandscapePositionMessage.class, name = LandscapePositionMessage.EVENT),
        @Type(value = NodegroupUpdateMessage.class, name = NodegroupUpdateMessage.EVENT),
        @Type(value = SpectatingUpdateMessage.class, name = SpectatingUpdateMessage.EVENT),
        @Type(value = SystemUpdateMessage.class, name = SystemUpdateMessage.EVENT),
        @Type(value = UserControllersMessage.class, name = UserControllersMessage.EVENT),
        @Type(value = UserPositionsMessage.class, name = UserPositionsMessage.EVENT) })
public abstract class VRMessage {
    @JsonTypeId
    private String event;

    public VRMessage() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * Invokes the correct {@code handle*} method of the given handler.
     * 
     * @param <T> The return type of the handeler's {@code handle*} methods.
     * @param handler The handler whose {@code handle*} method to invoke..
     * @return The return value of the {@code handle*} method.
     */
    public abstract <T> T handleWith(VRMessageHandler<T> handler);
}
