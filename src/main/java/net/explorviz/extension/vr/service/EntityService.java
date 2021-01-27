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

    private final Map<String, DetachedMenuModel> detachedMenus = new ConcurrentHashMap<>();

    private final Map<String, GrabbableObject> grabbableObjects = new ConcurrentHashMap<>();

    @Inject
    IdGenerationService idGenerationService;

    @Inject
    UserService userService;

    @PostConstruct
    public void init() {
        landscape = new LandscapeModel(idGenerationService.nextId());
        landscape.setQuaternion(- Math.sqrt(0.5), 0, 0, Math.sqrt(0.5));
        landscape.setScale(new double[] {0.1, 0.1, 0.1});
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

    public void openApp(String appId, double[] position, double[] quaternion, double[] scale) {
        ApplicationModel appModel = getOrCreateApp(appId);
        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
        appModel.setScale(scale);
    }

    public boolean closeApp(String appId) {
        var app = apps.get(appId);
        if (app != null && !app.isGrabbed()) {
            apps.remove(appId);
            grabbableObjects.remove(appId);
            return true;
        }
        return false;
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
        if (object != null && userId.equals(object.isGrabbedByUser())) {
            object.setGrabbed(false);
            object.setGrabbedByUser(null);
            userService.userReleasedObject(userId, objectId);
        }
    }

    public boolean moveObject(String userId, String objectId, double[] position, double[] quaternion, double[] scale) {
        GrabbableObject object = grabbableObjects.get(objectId);
        if (object == null || !userId.equals(object.isGrabbedByUser())) {
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
        return apps.values();
    }

    public Collection<DetachedMenuModel> getDetachedMenus() {
        return detachedMenus.values();
    }

    public LandscapeModel getLandscape() {
        return this.landscape;
    }

    public String detachMenu(String detachId, String entityType, double[] position, double[] quaternion, double[] scale) {
        var objectId = idGenerationService.nextId();
        var menu = new DetachedMenuModel(detachId, entityType, objectId);
        menu.setPosition(position);
        menu.setQuaternion(quaternion);
        menu.setScale(scale);
        detachedMenus.put(objectId, menu);
        grabbableObjects.put(objectId, menu);
        return objectId;
    }

    public boolean closeDetachedMenu(String menuId) {
        var menu = detachedMenus.get(menuId);
        if (menu != null && !menu.isGrabbed()) {
            detachedMenus.remove(menuId);
            grabbableObjects.remove(menuId);
            return true;
        }
        return false;
    }

}
