package net.explorviz.extension.vr.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event", visible = true)
@JsonSubTypes({ @Type(value = TestMessage.class, name = TestMessage.EVENT),
        @Type(value = ExampleMessage.class, name = ExampleMessage.EVENT) })
public abstract class VRMessage {
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}