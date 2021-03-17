package net.explorviz.extension.vr.service.room;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GrabService {

    private final Map<String, String> grabbedObjectToUser =  new ConcurrentHashMap<>();
    
    private final Map<String, ArrayList<String>> userToGrabbedObjects = new ConcurrentHashMap<>();
    
  
    public boolean grabObject(String userId, String objectId) {
        if (!isGrabbed(objectId)) {
            grabbedObjectToUser.put(objectId, userId);
            getGrabbedObjectsByUser(userId).add(objectId);
            return true;
        }
        return false;
    }
    
    public void releaseObject(String objectId) {
        if (grabbedObjectToUser.containsKey(objectId)) {
            var userId = grabbedObjectToUser.get(objectId);
            getGrabbedObjectsByUser(userId).remove(objectId);
            grabbedObjectToUser.remove(objectId);
        }
        
    }
    
    public boolean isGrabbedByUser(String objectId, String userId) {
        if (userId == null) return false;
        return userId.equals(grabbedObjectToUser.get(objectId));
    }
    
    public boolean isGrabbed(String objectId) {
        return grabbedObjectToUser.containsKey(objectId);
    }
    
    public void releaseAllGrabbedObjectsByUser(String userId) {
        for (var objectId : getGrabbedObjectsByUser(userId)) {
            grabbedObjectToUser.remove(objectId);
        }
        userToGrabbedObjects.remove(userId);
    }
    
    private ArrayList<String> getGrabbedObjectsByUser(String userId) {
        var objects = userToGrabbedObjects.get(userId);
        if (objects == null) {
            objects = new ArrayList<String>();
            userToGrabbedObjects.put(userId, objects);
        }
        return objects;
    }
    
    
}
