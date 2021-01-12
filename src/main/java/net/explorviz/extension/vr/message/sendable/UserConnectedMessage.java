package net.explorviz.extension.vr.message.sendable;

import java.awt.Color;

import net.explorviz.extension.vr.message.SendableMessage;

public class UserConnectedMessage extends SendableMessage {
    public static final String EVENT = "user_connecting";

    private String id;
    private String name;
    private Color color;
    
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
