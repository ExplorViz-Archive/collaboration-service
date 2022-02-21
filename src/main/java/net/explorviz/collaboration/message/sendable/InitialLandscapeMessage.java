package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.SendableMessage;

public class InitialLandscapeMessage extends SendableMessage {

  public static final String EVENT = "landscape";

  public static class Landscape {
    private String landscapeToken;
    private long timestamp;
    private double[] position;
    private double[] quaternion;
    private double[] scale;

    public String getLandscapeToken() {
      return this.landscapeToken;
    }

    public void setLandscapeToken(final String landscapeToken) {
      this.landscapeToken = landscapeToken;
    }

    public long getTimestamp() {
      return this.timestamp;
    }

    public void setTimestamp(final long timestamp) {
      this.timestamp = timestamp;
    }

    public double[] getPosition() {
      return this.position;
    }

    public void setPosition(final double[] position) {
      this.position = position;
    }

    public double[] getQuaternion() {
      return this.quaternion;
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion;
    }

    public double[] getScale() {
      return this.scale;
    }

    public void setScale(final double[] scale) {
      this.scale = scale;
    }
  }

  public static class DetachedMenu {
    private String objectId;
    private String entityType;
    private String entityId;
    private double[] position;
    private double[] quaternion;
    private double[] scale;

    public String getObjectId() {
      return this.objectId;
    }

    public void setObjectId(final String objectId) {
      this.objectId = objectId;
    }

    public String getEntityType() {
      return this.entityType;
    }

    public void setEntityType(final String entityType) {
      this.entityType = entityType;
    }

    public String getEntityId() {
      return this.entityId;
    }

    public void setEntityId(final String entityId) {
      this.entityId = entityId;
    }

    public double[] getPosition() {
      return this.position;
    }

    public void setPosition(final double ... position) {
      this.position = position;
    }

    public double[] getQuaternion() {
      return this.quaternion;
    }

    public void setQuaternion(final double ... quaternion) {
      this.quaternion = quaternion;
    }

    public double[] getScale() {
      return this.scale;
    }

    public void setScale(final double ... scale) {
      this.scale = scale;
    }

  }

  public static class HighlightingObject {
    private String userId;
    private String appId;
    private String entityType;
    private String entityId;
    private boolean highlighted;

    public String getUserId() {
      return this.userId;
    }

    public void setUserId(final String userId) {
      this.userId = userId;
    }

    public String getAppId() {
      return this.appId;
    }

    public void setAppId(final String appId) {
      this.appId = appId;
    }

    public String getEntityType() {
      return this.entityType;
    }

    public void setEntityType(final String entityType) {
      this.entityType = entityType;
    }

    public String getEntityId() {
      return this.entityId;
    }

    public void setEntityId(final String entityId) {
      this.entityId = entityId;
    }

    public boolean isHighlighted() {
      return this.highlighted;
    }

    public void setHighlighted(final boolean isHighlighted) {
      this.highlighted = isHighlighted;
    }

  }

  public static class App {
    private String id;
    private double[] position;
    private double[] quaternion;
    private double[] scale;
    private String[] openComponents;
    private HighlightingObject[] highlightedComponents;

    public HighlightingObject[] getHighlightedComponents() {
      return this.highlightedComponents;
    }

    public void setHighlightedComponents(final HighlightingObject[] highlightedComponents) {
      this.highlightedComponents = highlightedComponents;
    }

    public String getId() {
      return this.id;
    }

    public void setId(final String id) {
      this.id = id;
    }

    public double[] getPosition() {
      return this.position;
    }

    public void setPosition(final double[] position) {
      this.position = position;
    }

    public double[] getQuaternion() {
      return this.quaternion;
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion;
    }

    public double[] getScale() {
      return this.scale;
    }

    public void setScale(final double[] scale) {
      this.scale = scale;
    }

    public String[] getOpenComponents() {
      return this.openComponents;
    }

    public void setOpenComponents(final String[] openComponents) {
      this.openComponents = openComponents;
    }
  }

  private App[] openApps;
  private Landscape landscape;
  private DetachedMenu[] detachedMenus;

  public InitialLandscapeMessage() {
    super(EVENT);
  }

  public DetachedMenu[] getDetachedMenus() {
    return this.detachedMenus;
  }

  public void setDetachedMenus(final DetachedMenu[] detachedMenus) {
    this.detachedMenus = detachedMenus;
  }

  public App[] getOpenApps() {
    return this.openApps;
  }

  public void setOpenApps(final App[] openApps) {
    this.openApps = openApps;
  }

  public Landscape getLandscape() {
    return this.landscape;
  }

  public void setLandscape(final Landscape landscape) {
    this.landscape = landscape;
  }

}
