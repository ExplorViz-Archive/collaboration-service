package net.explorviz.vr.event;

import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.service.Room;

public class UserConnectedEvent {

	private UserModel userModel;

	private Room room;

	public UserConnectedEvent(UserModel userModel, Room room) {
		this.userModel = userModel;
		this.room = room;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public Room getRoom() {
		return room;
	}

}
