package net.explorviz.extension.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.App;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.DetachedMenu;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.HighlightingObject;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage.Landscape;
import net.explorviz.extension.vr.model.ApplicationModel;
import net.explorviz.extension.vr.model.DetachedMenuModel;
import net.explorviz.extension.vr.model.HighlightingModel;
import net.explorviz.extension.vr.model.LandscapeModel;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.service.Room;

@ApplicationScoped
public class SendLandscapeMessageFactory {

    public SendLandscapeMessage makeMessage(Room room) {
        // apps
        ArrayList<App> appArray = new ArrayList<>();
        for (final ApplicationModel app : room.getApplicationService().getApps()) {

            App appObj = new App();
            appObj.setId(app.getId());
            appObj.setPosition(app.getPosition());
            appObj.setQuaternion(app.getQuaternion());
            appObj.setScale(app.getScale());

            ArrayList<String> componentArray = new ArrayList<>();
            for (final String componentID : app.getOpenComponents()) {
                componentArray.add(componentID);
            }

            ArrayList<HighlightingObject> componentHighlightedArray = new ArrayList<>();
            for (final UserModel user : room.getUserService().getUsers()) {
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

            appObj.setOpenComponents(componentArray.toArray(n -> new String[n]));
            appObj.setHighlightedComponents(componentHighlightedArray.toArray(n -> new HighlightingObject[n]));
            appArray.add(appObj);

        }

        // landscape position
        Landscape landscapeObj = new Landscape();
        final LandscapeModel landscape = room.getLandscapeService().getLandscape();
        landscapeObj.setId(landscape.getId());
        landscapeObj.setPosition(landscape.getPosition());
        landscapeObj.setQuaternion(landscape.getQuaternion());
        landscapeObj.setScale(landscape.getScale());

        // detached menus
        ArrayList<DetachedMenu> detachedMenusArray = new ArrayList<>();
        for (final DetachedMenuModel menu : room.getDetachedMenuService().getDetachedMenus()) {
            DetachedMenu menuObj = new DetachedMenu();
            menuObj.setObjectId(menu.getId());
            menuObj.setEntityId(menu.getDetachId());
            menuObj.setPosition(menu.getPosition());
            menuObj.setQuaternion(menu.getQuaternion());
            menuObj.setEntityType(menu.getEntityType());
            menuObj.setScale(menu.getScale());
            detachedMenusArray.add(menuObj);
        }

        SendLandscapeMessage message = new SendLandscapeMessage();
        message.setOpenApps(appArray.toArray(n -> new App[n]));
        message.setLandscape(landscapeObj);
        message.setDetachedMenus(detachedMenusArray.toArray(n -> new DetachedMenu[n]));
        return message;
    }

}
