package net.explorviz.vr.message.sendable;

import java.awt.Color;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.explorviz.vr.message.BroadcastableMessage;
import net.explorviz.vr.message.encoder.ColorSerializer;

public class UserConnectedMessage extends BroadcastableMessage {
	public static final String EVENT = "user_connected";

	private String id;
	private String name;

	@JsonSerialize(using = ColorSerializer.class)
	private Color color;

	private double[] position;
	private double[] quaternion;

	public UserConnectedMessage() {
		super(EVENT);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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
