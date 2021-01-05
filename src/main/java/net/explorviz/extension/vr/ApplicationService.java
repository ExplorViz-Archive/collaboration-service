package net.explorviz.extension.vr;

import java.util.HashMap;

import net.explorviz.extension.vr.messages.AppClosedMessage;
import net.explorviz.extension.vr.messages.AppGrabbedMessage;
import net.explorviz.extension.vr.messages.AppOpenedMessage;
import net.explorviz.extension.vr.model.ApplicationModel;

public class ApplicationService {

    private final HashMap<String, ApplicationModel> apps = new HashMap<>(); // maps applicationID to the application
                                                                            // model

    public void onAppOpened(final AppOpenedMessage message) {
        ApplicationModel appModel;
        String id = message.getId();

        // add app to hash map or get app from hash map
        if (this.apps.containsKey(id)) {
            appModel = this.apps.get(id);
        } else {
            appModel = new ApplicationModel();
            appModel.setId(id);
            this.apps.put(id, appModel);
        }

        appModel.setOpen(true);
        appModel.setPosition(message.getPosition());
        appModel.setQuaternion(message.getQuaternion());

    }

    public void onAppClosed(final AppClosedMessage message) {
        this.apps.remove(message.getAppID());
    }

    public boolean onAppGrabbed(final AppGrabbedMessage message, String userId) {
        ApplicationModel appModel = this.apps.get(message.getAppID());
        if (appModel == null || appModel.isGrabbed())
            return false;
        appModel.setGrabbed(true);
        appModel.setGrabbedByUser(userId);
        return true;
    }

    // TODO onAppReleased, onAppTranslated

}
