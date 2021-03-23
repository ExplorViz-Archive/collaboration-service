package net.explorviz.extension.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.explorviz.extension.vr.model.ApplicationModel;

public class ApplicationService {
    private final Map<String, ApplicationModel> apps = new ConcurrentHashMap<>();

    private final GrabService grabService;
	
	public ApplicationService(GrabService grabService) {
        this.grabService = grabService;
    }

    public Collection<ApplicationModel> getApps() {
        return apps.values();
    }

    public void openApp(String appId, double[] position, double[] quaternion, double[] scale) {
        ApplicationModel appModel = getOrCreateApp(appId);
        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
        appModel.setScale(scale);
    }

    public boolean closeApp(String appId) {
        if (!grabService.isGrabbed(appId)) {
            var app = apps.get(appId);
            if (app != null) {
                apps.remove(appId);
                grabService.removeGrabbableObject(app);
                return true;
            }
        }
        return false;
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
        grabService.addGrabbableObject(appModel);
        return appModel;
    }
}
