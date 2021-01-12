package net.explorviz.extension.vr.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.HightlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.NodegroupUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SystemUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;

/**
 * Base class for all messages that are exchanged via the websocket connection
 * between the frontend and VR service.
 * 
 * There should be a {@like Type} entry in the {@link JsonSubTypes} annotation
 * for every receivable message that maps the message's event name to its class
 * object.
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

    /**
     * Creates a new message of the given type.
     * 
     * Every subclass should define a static {@code EVENT} attribute and call
     * {@code super(EVENT)} in its default constructor.
     * 
     * @param event The type identifier of the message.
     */
    public VRMessage(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
