package net.explorviz.extension.vr.messages;

public class AppReleasedMessage extends VRMessage {

    public static final String EVENT = "app_released";

    private String id;
    private double[] position;
    private double[] quaternion;

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

    @Override
    public <T> T handleWith(VRMessageHandler<T> handler) {
        return handler.handleAppReleasedMessage(this);
    }
}
