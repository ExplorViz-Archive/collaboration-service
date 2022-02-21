package net.explorviz.collaboration.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a user wants to join a room.
 * Contains all information to create the user.
 */
public class JoinLobbyPayload {
  private String userName;
  private double[] position;
  private double[] quaternion;

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
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
}
