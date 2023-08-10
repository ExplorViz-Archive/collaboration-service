package net.explorviz.collaboration.message.sendable; // NOPMD

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.SendableMessage;

public class InitialLandscapeMessage extends SendableMessage {

  public static final String EVENT = "landscape";

  private App[] openApps;
  private Landscape landscape;
  private DetachedMenu[] detachedMenus;

  public static class Landscape {
    private String landscapeToken;
    private long timestamp;

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
  }

  public static class DetachedMenu {
    private String objectId;
    private String userId;
    private String detachId;
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

    public String getUserId() {
      return this.userId;
    }

    public void setUserId(final String userId) {
      this.userId = userId;
    }

    public String getDetachId() {
      return this.detachId;
    }

    public void setDetachId(final String detachId) {
      this.detachId = detachId;
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
      return this.position.clone();
    }

    public void setPosition(final double...position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double...quaternion) {
      this.quaternion = quaternion.clone();
    }

    public double[] getScale() {
      return this.scale.clone();
    }

    public void setScale(final double...scale) {
      this.scale = scale.clone();
    }

  }

  public static class HighlightingObject {
    private String userId;
    private float[] color;
    private String appId;
    private String entityType;
    private String entityId;
    private boolean highlighted; // NOPMD NOCS


    public String getUserId() {
      return this.userId;
    }

    public void setUserId(final String userId) {
      this.userId = userId;
    }

    public float[] getColor() {
      return this.color;
    }

    public void setColor(final Color color) {
      this.color = new float[]{color.getRed(), color.getGreen(), color.getBlue()};
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

    @JsonProperty("isHighlighted")
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
      return this.highlightedComponents.clone();
    }

    public void setHighlightedComponents(final HighlightingObject[] highlightedComponents) {
      this.highlightedComponents = highlightedComponents.clone();
    }

    public String getId() {
      return this.id;
    }

    public void setId(final String id) {
      this.id = id;
    }

    public double[] getPosition() {
      return this.position.clone();
    }

    public void setPosition(final double[] position) {
      this.position = position.clone();
    }

    public double[] getQuaternion() {
      return this.quaternion.clone();
    }

    public void setQuaternion(final double[] quaternion) {
      this.quaternion = quaternion.clone();
    }

    public double[] getScale() {
      return this.scale.clone();
    }

    public void setScale(final double[] scale) {
      this.scale = scale.clone();
    }

    public String[] getOpenComponents() {
      return this.openComponents.clone();
    }

    public void setOpenComponents(final String[] openComponents) {
      this.openComponents = openComponents.clone();
    }
  }

  public InitialLandscapeMessage() {
    super(EVENT);
  }

  public DetachedMenu[] getDetachedMenus() {
    return this.detachedMenus.clone();
  }

  public void setDetachedMenus(final DetachedMenu[] detachedMenus) {
    this.detachedMenus = detachedMenus.clone();
  }

  public App[] getOpenApps() {
    return this.openApps.clone();
  }

  public void setOpenApps(final App[] openApps) {
    this.openApps = openApps.clone();
  }

  public Landscape getLandscape() {
    return this.landscape;
  }

  public void setLandscape(final Landscape landscape) {
    this.landscape = landscape;
  }

}
