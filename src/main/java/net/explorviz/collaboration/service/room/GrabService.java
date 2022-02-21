package net.explorviz.collaboration.service.room;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.explorviz.collaboration.model.GrabbableObjectModel;

public class GrabService {

  private final Map<String, GrabbableObjectModel> grabbableObjects = new ConcurrentHashMap<>();

  private final Map<String, String> grabbedObjectToUser = new ConcurrentHashMap<>();

  private final Map<String, ArrayList<String>> userToGrabbedObjects = new ConcurrentHashMap<>();

  public void addGrabbableObject(final GrabbableObjectModel object) {
    this.grabbableObjects.put(object.getGrabId(), object);
  }

  public void removeGrabbableObject(final GrabbableObjectModel object) {
    this.grabbableObjects.remove(object.getGrabId());
  }

  public boolean grabObject(final String userId, final String objectId) {
    if (!this.isGrabbed(objectId)) {
      this.grabbedObjectToUser.put(objectId, userId);
      this.getGrabbedObjectsByUser(userId).add(objectId);
      return true;
    }
    return false;
  }

  public boolean moveObject(final String userId, final String objectId, final double[] position,
      final double[] quaternion, final double[] scale) {
    if (this.isGrabbedByUser(objectId, userId)) {
      final var object = this.grabbableObjects.get(objectId);
      if (object != null) {
        object.setPosition(position);
        object.setQuaternion(quaternion);
        object.setScale(scale);
        return true;
      }
    }
    return false;
  }

  public void releaseObject(final String userId, final String objectId) {
    if (this.isGrabbedByUser(objectId, userId)) {
      this.getGrabbedObjectsByUser(userId).remove(objectId);
      this.grabbedObjectToUser.remove(objectId);
    }

  }

  public boolean isGrabbedByUser(final String objectId, final String userId) {
    if (userId == null) {
      return false;
    }
    return userId.equals(this.grabbedObjectToUser.get(objectId));
  }

  public boolean isGrabbed(final String objectId) {
    return this.grabbedObjectToUser.containsKey(objectId);
  }

  public void releaseAllGrabbedObjectsByUser(final String userId) {
    for (final var objectId : this.getGrabbedObjectsByUser(userId)) {
      this.grabbedObjectToUser.remove(objectId);
    }
    this.userToGrabbedObjects.remove(userId);
  }

  private ArrayList<String> getGrabbedObjectsByUser(final String userId) {
    var objects = this.userToGrabbedObjects.get(userId);
    if (objects == null) {
      objects = new ArrayList<>();
      this.userToGrabbedObjects.put(userId, objects);
    }
    return objects;
  }
}
