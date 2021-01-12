package net.explorviz.extension.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.App;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.HighlightingObject;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.LandscapePosition;
import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.BaseModel;
import net.explorviz.extension.vr.model.HighlightingModel;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.service.EntityService;
import net.explorviz.extension.vr.service.UserService;

@ApplicationScoped
public class SendLandscapeMessageFactory {

    @Inject
    EntityService entityService;

    @Inject
    UserService userService;

    public SendLandscapeMessage makeMessage() {

        // apps
        ArrayList<App> appArray = new ArrayList<>();
        for (final ApplicationModel app : this.entityService.getApps()) {

            App appObj = new App();
            appObj.setId(app.getId());
            appObj.setPosition(app.getPosition());
            appObj.setQuaternion(app.getQuaternion());

            ArrayList<String> componentArray = new ArrayList<>();
            for (final String componentID : app.getOpenComponents()) {
                componentArray.add(componentID);
            }

            ArrayList<HighlightingObject> componentHighlightedArray = new ArrayList<>();
            for (final UserModel user : this.userService.getUsers()) {
                if (user.hasHighlightedEntity()) {
                    final HighlightingModel highlighted = user.getHighlightedEntity();
                    HighlightingObject highlightingObj = new HighlightingObject();
                    highlightingObj.setUserID(user.getId());
                    highlightingObj.setAppID(highlighted.getHighlightedApp());
                    highlightingObj.setEntityType(highlighted.getEntityType());
                    highlightingObj.setEntityID(highlighted.getHighlightedEntity());
                    highlightingObj.setHighlighted(true);
                    componentHighlightedArray.add(highlightingObj);
                }
            }

            appObj.setOpenComponents((String[]) componentArray.toArray());
            appObj.setHighlightedComponents((HighlightingObject[]) componentHighlightedArray.toArray());
            appArray.add(appObj);

        }

        // landscape position
        LandscapePosition landscapePosObj = new LandscapePosition();
        final BaseModel landscape = this.entityService.getLandscape();
        landscapePosObj.setPosition(landscape.getPosition());
        landscapePosObj.setQuaternion(landscape.getQuaternion());

        SendLandscapeMessage message = new SendLandscapeMessage();
        message.setOpenApps((App[]) appArray.toArray());
        message.setLandscape(landscapePosObj);

        return message;
    }

}
