package net.explorviz.vr.model;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserModel extends BaseModel {

    private String userName;
    private final Map<Integer, ControllerModel> controllers;
    private State state;
    private long timeOfLastMessage;
    private Color color;
    private boolean hasHighlightedEntity;
    private HighlightingModel highlightedEntity;

    public enum State {
        CONNECTING, CONNECTED, SPECTATING
    };

    public UserModel(String id, String userName, Color color) {
        super(id);
        this.userName = userName;
        this.controllers = new HashMap<>();
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

    public Collection<ControllerModel> getControllers() {
    	return controllers.values();
    }

    public ControllerModel getController(int controllerId) {
    	return controllers.get(controllerId);
    }

    public void addController(ControllerModel controllerModel) {
    	controllers.put(controllerModel.getControllerId(), controllerModel);
    }

    public void removeController(int controllerId) {
    	controllers.remove(controllerId);
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
