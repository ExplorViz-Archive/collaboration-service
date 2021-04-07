package net.explorviz.vr.message.sendable;

import java.awt.Color;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.explorviz.vr.message.SendableMessage;
import net.explorviz.vr.message.encoder.ColorSerializer;

public class SelfConnectedMessage extends SendableMessage {
	public static final String EVENT = "self_connected";

	public static class Controller {
		private int controllerId;
		private String assetUrl;
		private double[] position;
		private double[] quaternion;
		private double[] intersection;

		public int getControllerId() {
			return controllerId;
		}

		public void setControllerId(int controllerId) {
			this.controllerId = controllerId;
		}

		public String getAssetUrl() {
			return assetUrl;
		}

		public void setAssetUrl(String assetUrl) {
			this.assetUrl = assetUrl;
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

		public double[] getIntersection() {
			return intersection;
		}

		public void setIntersection(double[] intersection) {
			this.intersection = intersection;
		}
	}

	public static class User {
		private String id;
		private String name;

		@JsonSerialize(using = ColorSerializer.class)
		private Color color;

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
	}

	public static class OtherUser extends User {
		private Controller[] controllers;
		private double[] position;
		private double[] quaternion;

		public Controller[] getControllers() {
			return controllers;
		}

		public void setControllers(Controller[] controllers) {
			this.controllers = controllers;
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

	private User self;
	private OtherUser[] users;

	public SelfConnectedMessage() {
		super(EVENT);
	}

	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

	public OtherUser[] getUsers() {
		return users;
	}

	public void setUsers(OtherUser[] users) {
		this.users = users;
	}
}
