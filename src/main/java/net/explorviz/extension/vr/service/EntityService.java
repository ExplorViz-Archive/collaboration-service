package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.BaseModel;
import net.explorviz.extension.vr.model.DetachedMenuModel;
import net.explorviz.extension.vr.model.GrabbableObject;
import net.explorviz.extension.vr.model.LandscapeModel;

@ApplicationScoped
public class EntityService {

    /**
     * Positional information for the landscape.
     */
    private LandscapeModel landscape;

    /**
     * Maps applicationID to the application model.
     */
    private final Map<String, ApplicationModel> apps = new ConcurrentHashMap<>();

    private final Map<String, GrabbableObject> grabbableObjects = new ConcurrentHashMap<>();

    @Inject
    IdGenerationService idGenerationService;

    @Inject
    UserService userService;

    @PostConstruct
    public void init() {
        landscape = new LandscapeModel(idGenerationService.nextId());
        grabbableObjects.put(landscape.getId(), landscape);
    }

    /**
     * Gets the application model with the given ID or creates a model if it does
     * not exist.
     * 
     * @param appId The ID of the application to get or create.
     * @return The (created) application.
     */
    private ApplicationModel getOrCreateApp(String appId) {
        if (apps.containsKey(appId)) {
            return apps.get(appId);
        }
        final var appModel = new ApplicationModel(appId);
        apps.put(appId, appModel);
        grabbableObjects.put(appId, appModel);

        return appModel;
    }

    public void openApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel = getOrCreateApp(appId);
        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
    }

    public void closeApp(String appId) {
        apps.remove(appId);
        grabbableObjects.remove(appId);
    }

    public boolean grabbObject(String userId, String objectId) {
        GrabbableObject object = grabbableObjects.get(objectId);
        if (object == null || object.isGrabbed())
            return false;
        object.setGrabbed(true);
        object.setGrabbedByUser(userId);
        userService.userGrabbedObject(userId, objectId);
        return true;
    }

    public void releaseObject(String userId, String objectId) {
        GrabbableObject object = grabbableObjects.get(objectId);
        if (object != null && object.isGrabbedByUser().equals(userId)) {
            object.setGrabbed(false);
            object.setGrabbedByUser(null);
            userService.userReleasedObject(userId, objectId);
        }
    }

    public boolean moveObject(String userId, String objectId, double[] position, double[] quaternion) {
        GrabbableObject object = grabbableObjects.get(objectId);
        if (object == null || userId != object.isGrabbedByUser()) {
            return false;
        }
        object.setPosition(position);
        object.setQuaternion(quaternion);
        return true;
    }

    public void updateComponent(String componentId, String appId, boolean isFoundation, boolean isOpened) {
        ApplicationModel appModel = getOrCreateApp(appId);
        if (isFoundation) {
            appModel.closeAllComponents();
        } else if (isOpened) {
            appModel.openComponent(componentId);
        } else {
            appModel.closeComponent(componentId);
        }

    }

    public Collection<ApplicationModel> getApps() {
        return this.apps.values();
    }

    public BaseModel getLandscape() {
        return this.landscape;
    }

    public String detachMenu(String detachId, String entityType, double[] position, double[] quaternion) {
        var objectId = idGenerationService.nextId();
        var menu = new DetachedMenuModel(detachId, entityType, objectId);
        menu.setPosition(position);
        menu.setQuaternion(quaternion);
        grabbableObjects.put(objectId, menu);
        return objectId;
    }

}
