package net.explorviz.collaboration.model;

public class HighlightingModel {

  private final String appId;
  private final String entityId;
  private final String entityType;

  public HighlightingModel(final String appId, final String entityId, final String entityType) {
    this.appId = appId;
    this.entityId = entityId;
    this.entityType = entityType;
  }

  public String getAppId() {
    return this.appId;
  }

  public String getEntityId() {
    return this.entityId;
  }

  public String getEntityType() {
    return this.entityType;
  }

}
