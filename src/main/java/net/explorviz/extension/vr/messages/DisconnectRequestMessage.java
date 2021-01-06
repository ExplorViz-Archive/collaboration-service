package net.explorviz.extension.vr.messages;

public class DisconnectRequestMessage extends VRMessage {

	public static final String EVENT = "disconnect_request";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
