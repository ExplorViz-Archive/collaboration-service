package net.explorviz.extension.vr.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppOpenedMessage extends VRMessage {

    public static final String EVENT = "app_opened";

    private String id;
    private double[] position;
    private double[] quaternion;

    @JsonCreator
    public AppOpenedMessage(@JsonProperty("id") String id, @JsonProperty("position") double[] position,
            @JsonProperty("quaternion") double[] quaternion) {
        super(EVENT);
        this.id = id;
        this.position = position;
        this.quaternion = quaternion;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Arrays.hashCode(position);
        result = prime * result + Arrays.hashCode(quaternion);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppOpenedMessage other = (AppOpenedMessage) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (!Arrays.equals(position, other.position))
            return false;
        if (!Arrays.equals(quaternion, other.quaternion))
            return false;
        return true;
    }
}
