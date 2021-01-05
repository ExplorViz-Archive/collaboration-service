package net.explorviz.extension.vr.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppTranslatedMessage extends VRMessage {

    public static final String EVENT = "app_translated";

    private String appId;
    private double[] direction;
    private double length;
    
    @JsonCreator
    public AppTranslatedMessage(@JsonProperty("appId") String appId, @JsonProperty("direction") double[] direction, @JsonProperty("length") double length) {
        super();
        this.appId = appId;
        this.direction = direction;
        this.length = length;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public double[] getDirection() {
        return direction;
    }

    public void setDirection(double[] direction) {
        this.direction = direction;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + Arrays.hashCode(direction);
        long temp;
        temp = Double.doubleToLongBits(length);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        AppTranslatedMessage other = (AppTranslatedMessage) obj;
        if (appId == null) {
            if (other.appId != null) {
                return false;
            }
        } else if (!appId.equals(other.appId)) {
            return false;
        }
        if (!Arrays.equals(direction, other.direction)) {
            return false;
        }
        if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        return true;
    }
}
