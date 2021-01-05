package net.explorviz.extension.vr.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LandscapePositionMessage extends VRMessage {

    public static final String EVENT = "landscape_position";

    // TODO send new position instead of delta and offset
    private double[] deltaPosition;
    private double[] offset;
    private double[] quaternion;

    @JsonCreator
    public LandscapePositionMessage(@JsonProperty("deltaPosition") double[] deltaPosition,
            @JsonProperty("offset") double[] offset, @JsonProperty("quaternion") double[] quaternion) {
        super(EVENT);
        this.deltaPosition = deltaPosition;
        this.offset = offset;
        this.quaternion = quaternion;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(deltaPosition);
        result = prime * result + Arrays.hashCode(offset);
        result = prime * result + Arrays.hashCode(quaternion);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LandscapePositionMessage other = (LandscapePositionMessage) obj;
        if (!Arrays.equals(deltaPosition, other.deltaPosition)) {
            return false;
        }
        if (!Arrays.equals(offset, other.offset)) {
            return false;
        }
        if (!Arrays.equals(quaternion, other.quaternion)) {
            return false;
        }
        return true;
    }
}
