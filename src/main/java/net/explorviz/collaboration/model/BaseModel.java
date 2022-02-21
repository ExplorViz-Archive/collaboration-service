package net.explorviz.collaboration.model;

public class BaseModel {
  // position data
  private double xPos;
  private double yPos;
  private double zPos;
  private double xQuat;
  private double yQuat;
  private double zQuat;
  private double wQuat;

  private String id;

  public BaseModel(final String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public double[] getPosition() {
    return new double[] {this.xPos, this.yPos, this.zPos};
  }

  public void setPosition(final double[] coordinates) {
    this.xPos = coordinates[0];
    this.yPos = coordinates[1];
    this.zPos = coordinates[2];
  }

  public void setDeltaPosition(final double[] coordinates) {
    this.xPos += coordinates[0];
    this.yPos += coordinates[1];
    this.zPos += coordinates[2];
  }

  public void setPosition(final double x, final double y, final double z) {
    this.xPos = x;
    this.yPos = y;
    this.zPos = z;
  }

  public double[] getQuaternion() {
    return new double[] {this.xQuat, this.yQuat, this.zQuat, this.wQuat};
  }

  public void setQuaternion(final double[] quaternion) {
    this.xQuat = quaternion[0];
    this.yQuat = quaternion[1];
    this.zQuat = quaternion[2];
    this.wQuat = quaternion[3];
  }

  public void setQuaternion(final double xQuat, final double yQuat, final double zQuat,
      final double wQuat) {
    this.xQuat = xQuat;
    this.yQuat = yQuat;
    this.zQuat = zQuat;
    this.wQuat = wQuat;
  }

}
