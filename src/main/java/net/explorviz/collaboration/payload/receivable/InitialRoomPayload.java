package net.explorviz.collaboration.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a new room is created.
 * Contains all information to initialize a room.
 */
public class InitialRoomPayload {

  private Landscape landscape;
  private App[] openApps;
  private DetachedMenu[] detachedMenus;

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

  public static class App {
    private String id;
    private double[] position;
    private double[] quaternion;
    private String[] openComponents;
    private double[] scale;

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

    public String[] getOpenComponents() {
      return this.openComponents;
    }

    public void setOpenComponents(final String[] openComponents) {
      this.openComponents = openComponents;
    }

    public double[] getScale() {
      return this.scale;
    }

    public void setScale(final double[] scale) {
      this.scale = scale;
    }
  }

  public static class DetachedMenu {
    private String entityId;
    private String entityType;
    private double[] position;
    private double[] quaternion;
    private double[] scale;

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

  public Landscape getLandscape() {
    return this.landscape;
  }

  public void setLandscape(final Landscape landscape) {
    this.landscape = landscape;
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
}
