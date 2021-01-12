package net.explorviz.extension.vr.message.sendable;

import java.awt.Color;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.explorviz.extension.vr.message.SendableMessage;
import net.explorviz.extension.vr.message.encoder.ColorSerializer;

public class UserConnectedMessage extends SendableMessage {
    public static final String EVENT = "user_connected";

    private String id;
    private String name;

    @JsonSerialize(using = ColorSerializer.class)
    private Color color;

    public UserConnectedMessage() {
        super(EVENT);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
