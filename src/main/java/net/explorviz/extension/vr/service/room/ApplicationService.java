package net.explorviz.extension.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.explorviz.extension.vr.model.ApplicationModel;

public class ApplicationService {
    private final Map<String, ApplicationModel> openApplications = new ConcurrentHashMap<>();

    private final GrabService grabService;
	
	public ApplicationService(GrabService grabService) {
        this.grabService = grabService;
    }

    public Collection<ApplicationModel> getApplications() {
        return openApplications.values();
    }

    public void openApplication(String appId, double[] position, double[] quaternion, double[] scale) {
        ApplicationModel appModel = getOrCreateApplication(appId);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
        appModel.setScale(scale);
    }

    public boolean closeApplication(String appId) {
        if (!grabService.isGrabbed(appId)) {
            var app = openApplications.get(appId);
            if (app != null) {
                openApplications.remove(appId);
                grabService.removeGrabbableObject(app);
                return true;
            }
        }
        return false;
    }
    
    public void closeAllApplications() {
        for (var app : openApplications.values() ) {
            grabService.removeGrabbableObject(app);
        }
        openApplications.clear();
    }

    public void updateComponent(String componentId, String appId, boolean isFoundation, boolean isOpened) {
        ApplicationModel appModel = getOrCreateApplication(appId);
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
    private ApplicationModel getOrCreateApplication(String appId) {
        if (openApplications.containsKey(appId)) {
            return openApplications.get(appId);
        }

        final var appModel = new ApplicationModel(appId);
        openApplications.put(appId, appModel);
        grabService.addGrabbableObject(appModel);
        return appModel;
    }
}
