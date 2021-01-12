package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.BaseModel;

@ApplicationScoped
public class EntityService {

    private final BaseModel landscape = new BaseModel(); // only containing positional information about landscape

    private final Map<String, Boolean> systemState = new ConcurrentHashMap<>(); // tells if a system (systemID) is
    // opened/closed
    private final Map<String, Boolean> nodeGroupState = new ConcurrentHashMap<>(); // tells if a nodegroup (nodegroupID)
                                                                                   // is
    // opened/closed
    private final Map<String, ApplicationModel> apps = new ConcurrentHashMap<>(); // maps applicationID to the
                                                                                  // application
    // model

    public void openApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel;
        ;

        // add app to hash map or get app from hash map
        if (this.apps.containsKey(appId)) {
            appModel = this.apps.get(appId);
        } else {
            appModel = new ApplicationModel();
            appModel.setId(appId);
            this.apps.put(appId, appModel);
        }

        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);

    }

    public void closeApp(String appId) {
        this.apps.remove(appId);
    }

    public boolean grabbApp(String appId, String userId) {
        ApplicationModel appModel = this.apps.get(appId);
        if (appModel == null || appModel.isGrabbed())
            return false;
        appModel.setGrabbed(true);
        appModel.setGrabbedByUser(userId);
        return true;
    }

    public void releaseApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel;

        // add app to hash map or get app from hash map
        if (this.apps.containsKey(appId)) {
            appModel = this.apps.get(appId);
        } else {
            appModel = new ApplicationModel();
            appModel.setId(appId);
            this.apps.put(appId, appModel);
        }

        appModel.setOpen(true);
        appModel.setPosition(position);
        appModel.setQuaternion(quaternion);
        appModel.setGrabbed(false);
    }

    public void translateApp() {

    }

    public void updateComponent(String componentId, String appId, boolean isFoundation, boolean isOpened) {
        ApplicationModel appModel = this.apps.get(appId);

        if (appModel != null) {
            if (isFoundation) {
                appModel.closeAllComponents();
            } else if (isOpened) {
                appModel.openComponent(componentId);
            } else {
                appModel.closeComponent(componentId);
            }
        }

    }

    public void updateLandscapePosition(double[] position, double[] quaternion) {
        landscape.setPosition(position);
        landscape.setQuaternion(quaternion);
    }

    public void updateSystem(String systemId, boolean systemOpened) {
        systemState.put(systemId, systemOpened);
    }

    public void updateNodegroup(String nodeGroupId, boolean nodeGroupOpened) {
        nodeGroupState.put(nodeGroupId, nodeGroupOpened);
    }

    public Set<Entry<String, Boolean>> getSystemState() {
        return this.systemState.entrySet();
    }

    public Set<Entry<String, Boolean>> getNodeGroupState() {
        return this.nodeGroupState.entrySet();
    }

    public Collection<ApplicationModel> getApps() {
        return this.apps.values();
    }

    public BaseModel getLandscape() {
        return this.landscape;
    }
}
