package net.explorviz.collaboration.model;

/**
 * Model for the landscape entity.
 */
public class LandscapeModel extends ScalableBaseModel implements GrabbableObjectModel {
  private String landscapeToken;
  private long timestamp;

  public LandscapeModel(final String id) {
    super(id);
  }

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

  @Override
  public String getGrabId() {
    return this.landscapeToken;
  }
}
