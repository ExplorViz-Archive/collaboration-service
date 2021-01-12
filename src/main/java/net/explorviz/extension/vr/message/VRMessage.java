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
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.UserDisconnectedMessage;

/**
 * Base class for all messages that are exchanged via the websocket connection
 * between the frontend and VR service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event", visible = true)
@JsonSubTypes({
        // Receivable messages.
        @Type(value = AppClosedMessage.class, name = AppClosedMessage.EVENT),
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
        @Type(value = UserPositionsMessage.class, name = UserPositionsMessage.EVENT),
        // Forwarded messages.
        @Type(value = ForwardedMessage.class, name = ForwardedMessage.EVENT),
        // Sendable messages.
        @Type(value = SelfConnectedMessage.class, name = SelfConnectedMessage.EVENT),
        @Type(value = UserConnectedMessage.class, name = UserConnectedMessage.EVENT),
        @Type(value = UserDisconnectedMessage.class, name = UserDisconnectedMessage.EVENT),
        @Type(value = SendLandscapeMessage.class, name = SendLandscapeMessage.EVENT)})
public abstract class VRMessage {
    @JsonTypeId
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
