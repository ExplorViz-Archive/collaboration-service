package net.explorviz.vr.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a user
 * wants to join a room. Contains all information to create the user.
 */
public class JoinLobbyPayload {
	private String userName;
	private double[] position;
	private double[] quaternion;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getQuaternion() {
		return quaternion;
	}

	public void setQuaternion(double[] quaternion) {
		this.quaternion = quaternion;
	}
}
