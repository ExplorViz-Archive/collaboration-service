package net.explorviz.collaboration.model;

public class HighlightingModel {

  private final String highlightedApp;
  private final String highlightedEntityId;
  private final String entityType;

  public HighlightingModel(final String appId, final String entityId, final String entityType) {
    this.highlightedApp = appId;
    this.highlightedEntityId = entityId;
    this.entityType = entityType;
  }

  public String getHighlightedApp() {
    return this.highlightedApp;
  }

  public String getHighlightedEntityId() {
    return this.highlightedEntityId;
  }

  public String getEntityType() {
    return this.entityType;
  }

}
