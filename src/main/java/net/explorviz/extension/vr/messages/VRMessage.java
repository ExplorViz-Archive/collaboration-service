package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event", visible = true)
@JsonSubTypes({ 
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
        @Type(value = UserPositionsMessage.class, name = UserPositionsMessage.EVENT)})
public abstract class VRMessage {
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}