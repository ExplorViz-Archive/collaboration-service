package net.explorviz.extension.vr.model;

import java.awt.Color;

public class UserModel extends BaseModel {

    private String userName;
    private final ControllerModel controller1;
    private final ControllerModel controller2;
    private State state;
    private long timeOfLastMessage;
    private Color color;
    private boolean hasHighlightedEntity;
    private HighlightingModel highlightedEntity;

    public enum State {
        CONNECTING, CONNECTED, SPECTATING
    };

    public UserModel(String id, String userName, ControllerModel controller1, ControllerModel controller2,
            Color color) {
        super(id);
        this.userName = userName;
        this.controller1 = controller1;
        this.controller2 = controller2;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public ControllerModel getController1() {
        return this.controller1;
    }

    public ControllerModel getController2() {
        return this.controller2;
    }

    public long getTimeOfLastMessage() {
        return this.timeOfLastMessage;
    }

    public void setTimeOfLastMessage(final long time) {
        this.timeOfLastMessage = time;
    }

    public boolean hasHighlightedEntity() {
        return hasHighlightedEntity;
    }

    public void setHighlighted(final boolean isHighlighted) {
        this.hasHighlightedEntity = isHighlighted;
    }

    public void setHighlightedEntity(final boolean isHighlighted, final String appId, final String entityType,
            final String entityId) {
        this.hasHighlightedEntity = isHighlighted;
        highlightedEntity = new HighlightingModel(isHighlighted, appId, entityId, entityType);
    }

    public HighlightingModel getHighlightedEntity() {
        return this.highlightedEntity;
    }

}
