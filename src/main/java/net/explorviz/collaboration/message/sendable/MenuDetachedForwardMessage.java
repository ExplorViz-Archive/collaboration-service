package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.BroadcastableMessage;

public class MenuDetachedForwardMessage extends BroadcastableMessage {
  private static final String EVENT = "menu_detached";

  private final String objectId;
  private final String userId;
  private final String entityType;
  private final String detachId;
  private double[] position;
  private double[] quaternion;
  private double[] scale;

  public MenuDetachedForwardMessage(final String objectId, final String userId,
      final String entityType, final String detachId, final double[] position,
      final double[] quaternion, final double[] scale) {
    super(EVENT);
    this.objectId = objectId;
    this.userId = userId;
    this.entityType = entityType;
    this.detachId = detachId;
    this.position = position.clone();
    this.quaternion = quaternion.clone();
    this.scale = scale.clone();
  }

  public String getUserId() {
    return userId;
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

  public String getObjectId() {
    return this.objectId;
  }

  public String getDetachId() {
    return this.detachId;
  }

  public String getEntityType() {
    return this.entityType;
  }

  public double[] getScale() {
    return this.scale.clone();
  }

  public void setScale(final double[] scale) {
    this.scale = scale.clone();
  }

}
