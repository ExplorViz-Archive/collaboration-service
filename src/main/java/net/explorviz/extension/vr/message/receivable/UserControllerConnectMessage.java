package net.explorviz.extension.vr.message.receivable;

import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;

public class UserControllerConnectMessage extends ReceivableMessage {
    public static final String EVENT = "user_controller_connect";

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

    private Controller controller;

    public UserControllerConnectMessage() {
        super(EVENT);
    }

    public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleUserControllerConnectMessage(this, arg);
    }
}
