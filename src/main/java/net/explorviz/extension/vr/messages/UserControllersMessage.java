package net.explorviz.extension.vr.messages;

public class UserControllersMessage extends VRMessage {

	public static final String EVENT = "user_controllers";

	public static class Controllers {
		private String controller1;
		private String controller2;

		public String getController1() {
			return controller1;
		}

		public void setController1(String controller1) {
			this.controller1 = controller1;
		}

		public String getController2() {
			return controller2;
		}

<<<<<<< HEAD
		public void setController2(String controller2) {
			this.controller2 = controller2;
		}

	}

	private Controllers connect;
	private Controllers disconnect;

	public Controllers getConnect() {
		return connect;
	}

	public void setConnect(Controllers connect) {
		this.connect = connect;
	}

	public Controllers getDisconnect() {
		return disconnect;
	}

	public void setDisconnect(Controllers disconnect) {
		this.disconnect = disconnect;
	}
=======
    public void setDisconnect(String[] disconnect) {
        this.disconnect = disconnect;
    }

    @Override
    public <T> T handleWith(VRMessageHandler<T> handler) {
        return handler.handleUserControllersMessage(this);
    }
>>>>>>> 070f39770c67cc6dc27f4dfe1f9ba84d8e65b088
}
