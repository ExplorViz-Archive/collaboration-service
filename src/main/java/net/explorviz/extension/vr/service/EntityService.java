package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.BaseModel;
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

    @Inject
    IdGenerationService idGenerationService;

    @PostConstruct
    public void init() {
        landscape = new LandscapeModel(idGenerationService.nextId());
    }

    /**
     * Gets the application model with the given ID or creates a model if it does
     * not exist.
     * 
     * @param appId The ID of the application to get or create.
     * @return The (created) application.
     */
    private ApplicationModel getOrCreateApp(String appId) {
        if (this.apps.containsKey(appId)) {
            return this.apps.get(appId);
        }
        final var appModel = new ApplicationModel(appId);
        this.apps.put(appId, appModel);
        return appModel;
    }

    public void openApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel = getOrCreateApp(appId);
        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
    }

    public void closeApp(String appId) {
        this.apps.remove(appId);
    }

    public boolean grabbApp(String appId, String userId) {
        ApplicationModel appModel = getOrCreateApp(appId);
        if (appModel.isGrabbed())
            return false;
        appModel.setGrabbed(true);
        appModel.setGrabbedByUser(userId);
        return true;
    }

    public void releaseApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel = getOrCreateApp(appId);
        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
        appModel.setGrabbed(false);
    }

    public void translateApp() {

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

    public void updateLandscapePosition(double[] position, double[] quaternion) {
        landscape.setPosition(position);
        landscape.setQuaternion(quaternion);
    }

    public Collection<ApplicationModel> getApps() {
        return this.apps.values();
    }

    public BaseModel getLandscape() {
        return this.landscape;
    }
}
