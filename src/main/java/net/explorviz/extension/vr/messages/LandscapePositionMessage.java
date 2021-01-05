package net.explorviz.extension.vr.messages;

public class LandscapePositionMessage extends VRMessage {

    public static final String EVENT = "landscape_position";

    // TODO send new position instead of delta and offset
    private double[] deltaPosition;
    private double[] offset;
    private double[] quaternion;

    public double[] getDeltaPosition() {
        return deltaPosition;
    }

    public void setDeltaPosition(double[] deltaPosition) {
        this.deltaPosition = deltaPosition;
    }

    public double[] getOffset() {
        return offset;
    }

    public void setOffset(double[] offset) {
        this.offset = offset;
    }

    public double[] getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(double[] quaternion) {
        this.quaternion = quaternion;
    }
}
