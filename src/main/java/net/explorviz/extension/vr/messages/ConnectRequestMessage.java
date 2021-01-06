package net.explorviz.extension.vr.messages;

public class ConnectRequestMessage extends VRMessage {

	public static final String EVENT = "connect_request";
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
