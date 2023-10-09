package net.explorviz.collaboration.model;

/**
 * Model for projectorconfigurations of Synchronization feature. Data for the ARENA2's projectors
 * lay in ressources.
 */
public class ProjectorConfigurations {
  private String id;
  private YawPitchRoll yawPitchRoll;
  private ProjectorAngles projectorAngles;

  public void setId(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setYawPitchRoll(final YawPitchRoll yawPitchRoll) {
    this.yawPitchRoll = yawPitchRoll;
  }

  public YawPitchRoll getYawPitchRoll() {
    return this.yawPitchRoll;
  }

  public void setProjectorAngles(final ProjectorAngles projectorAngles) {
    this.projectorAngles = projectorAngles;
  }

  public ProjectorAngles getProjectorAngles() {
    return this.projectorAngles;
  }

  /**
   * Projector's Euler's angles: yaw pitch roll
   */
  public static class YawPitchRoll {
    private double yaw;
    private double pitch;
    private double roll;

    public YawPitchRoll() {

    }

    public double getYaw() {
      return yaw;
    }

    public void setYaw(final double yaw) {
      this.yaw = yaw;
    }

    public double getPitch() {
      return pitch;
    }

    public void setPitch(final double pitch) {
      this.pitch = pitch;
    }

    public double getRoll() {
      return roll;
    }

    public void setRoll(final double roll) {
      this.roll = roll;
    }

  }


  /**
   * Angles how the projector is set up at the dome.
   */
  public static class ProjectorAngles {
    private double up;
    private double down;
    private double left;
    private double right;

    public ProjectorAngles() {
    }

    public double getUp() {
      return up;
    }

    public void setUp(final double up) {
      this.up = up;
    }

    public double getDown() {
      return down;
    }

    public void setDown(final double down) {
      this.down = down;
    }

    public double getLeft() {
      return left;
    }

    public void setLeft(final double left) {
      this.left = left;
    }

    public double getRight() {
      return right;
    }

    public void setRight(final double right) {
      this.right = right;
    }

  }
}
