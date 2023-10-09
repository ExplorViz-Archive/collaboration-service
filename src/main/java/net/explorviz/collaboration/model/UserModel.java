package net.explorviz.collaboration.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserModel extends BaseModel {

  private final String userName;
  private final Map<Integer, ControllerModel> controllers;
  private State state;
  private long timeOfLastMessage;
  private final Color color;
  private boolean hasHighlightedEntity;
  private final ArrayList<HighlightingModel> highlightedEntities; // NOPMD


  public enum State {
    CONNECTING, CONNECTED, SPECTATING, SYNCHRONIZED
  }

  public UserModel(final String id, final String userName, final Color color) {
    super(id);
    this.userName = userName;
    this.controllers = new HashMap<>();
    this.highlightedEntities = new ArrayList<>();
    this.color = color;
  }

  public Color getColor() {
    return this.color;
  }

  public State getState() {
    return this.state;
  }

  public void setState(final State state) {
    this.state = state;
  }

  public String getUserName() {
    return this.userName;
  }

  public Collection<ControllerModel> getControllers() {
    return this.controllers.values();
  }

  public ControllerModel getController(final int controllerId) {
    return this.controllers.get(controllerId);
  }

  public void addController(final ControllerModel controllerModel) {
    this.controllers.put(controllerModel.getControllerId(), controllerModel);
  }

  public void removeController(final int controllerId) {
    this.controllers.remove(controllerId);
  }

  public long getTimeOfLastMessage() {
    return this.timeOfLastMessage;
  }

  public void setTimeOfLastMessage(final long time) {
    this.timeOfLastMessage = time;
  }

  public boolean containsHighlightedEntity() {
    return this.hasHighlightedEntity;
  }

  public void setHighlighted(final boolean isHighlighted) {
    this.hasHighlightedEntity = isHighlighted;
  }

  public void setHighlightedEntity(final String appId, final String entityType,
      final String entityId) {
    this.setHighlighted(true);
    this.highlightedEntities.add(new HighlightingModel(appId, entityId, entityType));
  }

  public void removeHighlightedEntity(final String entityId) {

    this.highlightedEntities.removeIf(
        highlightedEntity -> highlightedEntity.getEntityId().equals(entityId));

    if (this.highlightedEntities.isEmpty()) {
      this.setHighlighted(false);
    }
  }

  public void removeAllHighlightedEntities() {
    this.highlightedEntities.clear();
  }


  public ArrayList<HighlightingModel> getHighlightedEntities() { // NOPMD
    return this.highlightedEntities;
  }
}
