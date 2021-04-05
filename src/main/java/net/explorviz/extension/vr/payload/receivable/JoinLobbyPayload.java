package net.explorviz.extension.vr.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a user
 * wants to join a room. Contains all information to create the user.
 */
public class JoinLobbyPayload {
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
