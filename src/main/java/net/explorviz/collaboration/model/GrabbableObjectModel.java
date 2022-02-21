package net.explorviz.collaboration.model;

public interface GrabbableObjectModel {

  String getGrabId();

  double[] getPosition();

  void setPosition(double[] position);

  double[] getQuaternion();

  void setQuaternion(double[] quaternion);

  double[] getScale();

  void setScale(double[] scale);

}
