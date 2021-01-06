package net.explorviz.extension.vr;

import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.model.ApplicationModel;

@ApplicationScoped
public class ApplicationService {

    private final HashMap<String, ApplicationModel> apps = new HashMap<>(); // maps applicationID to the application
                                                                            // model

    public void openApp(String appId, double[] position, double[] quaternion) {
        ApplicationModel appModel;;

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


}
