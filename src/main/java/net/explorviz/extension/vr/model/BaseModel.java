package net.explorviz.extension.vr.model;

public class BaseModel {
    // position data
    private double xPos = 0;
    private double yPos = 0;
    private double zPos = 0;
    private double xQuat, yQuat, zQuat, wQuat;

    private String id;

    public BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public double[] getPosition() {
        final double[] coordinates = { xPos, yPos, zPos };
        return coordinates;
    }

    public void setPosition(final double[] coordinates) {
        this.xPos = coordinates[0];
        this.yPos = coordinates[1];
        this.zPos = coordinates[2];
    }

    public void setDeltaPosition(final double[] coordinates) {
        this.xPos += coordinates[0];
        this.yPos += coordinates[1];
        this.zPos += coordinates[2];
    }

    public void setPosition(final double x, final double y, final double z) {
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }

    public double[] getQuaternion() {
        final double[] quaternion = { xQuat, yQuat, zQuat, wQuat };
        return quaternion;
    }

    public void setQuaternion(final double[] quaternion) {
        this.xQuat = quaternion[0];
        this.yQuat = quaternion[1];
        this.zQuat = quaternion[2];
        this.wQuat = quaternion[3];
    }

    public void setQuaternion(final double xQuat, final double yQuat, final double zQuat, final double wQuat) {
        this.xQuat = xQuat;
        this.yQuat = yQuat;
        this.zQuat = zQuat;
        this.wQuat = wQuat;
    }

}
