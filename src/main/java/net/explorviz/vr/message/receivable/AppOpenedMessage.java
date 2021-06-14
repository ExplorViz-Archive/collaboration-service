package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessage;
import net.explorviz.vr.message.ReceivableMessageHandler;

public class AppOpenedMessage extends ReceivableMessage {

	public static final String EVENT = "app_opened";

	private String id;
	private double[] position;
	private double[] quaternion;
	private double[] scale;

	public AppOpenedMessage() {
		super(EVENT);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public double[] getScale() {
		return scale;
	}

	public void setScale(double[] scale) {
		this.scale = scale;
	}

	public String toString() {
		return "id: " + id;
	}

	@Override
	public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
		return handler.handleAppOpenedMessage(this, arg);
	}
}
