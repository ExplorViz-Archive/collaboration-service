package net.explorviz.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.vr.message.sendable.InitialLandscapeMessage;
import net.explorviz.vr.message.sendable.InitialLandscapeMessage.App;
import net.explorviz.vr.message.sendable.InitialLandscapeMessage.DetachedMenu;
import net.explorviz.vr.message.sendable.InitialLandscapeMessage.HighlightingObject;
import net.explorviz.vr.message.sendable.InitialLandscapeMessage.Landscape;
import net.explorviz.vr.model.ApplicationModel;
import net.explorviz.vr.model.DetachedMenuModel;
import net.explorviz.vr.model.HighlightingModel;
import net.explorviz.vr.model.LandscapeModel;
import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.service.Room;

@ApplicationScoped
public class InitialLandscapeMessageFactory {

    public InitialLandscapeMessage makeMessage(Room room) {
        // apps
        ArrayList<App> appArray = new ArrayList<>();
        for (final ApplicationModel app : room.getApplicationService().getApplications()) {

            App appObj = new App();
            appObj.setId(app.getId());
            appObj.setPosition(app.getPosition());
            appObj.setQuaternion(app.getQuaternion());
            appObj.setScale(app.getScale());

            ArrayList<String> componentArray = new ArrayList<>();
            for (final String componentId : app.getOpenComponents()) {
                componentArray.add(componentId);
            }

            ArrayList<HighlightingObject> componentHighlightedArray = new ArrayList<>();
            for (final UserModel user : room.getUserService().getUsers()) {
                if (user.hasHighlightedEntity()) {
                    final HighlightingModel highlighted = user.getHighlightedEntity();
                    HighlightingObject highlightingObj = new HighlightingObject();
                    highlightingObj.setUserId(user.getId());
                    highlightingObj.setAppId(highlighted.getHighlightedApp());
                    highlightingObj.setEntityType(highlighted.getEntityType());
                    highlightingObj.setEntityId(highlighted.getHighlightedEntity());
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
        landscapeObj.setLandscapeToken(landscape.getLandscapeToken());
        landscapeObj.setTimestamp(landscape.getTimestamp());
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

        InitialLandscapeMessage message = new InitialLandscapeMessage();
        message.setOpenApps(appArray.toArray(n -> new App[n]));
        message.setLandscape(landscapeObj);
        message.setDetachedMenus(detachedMenusArray.toArray(n -> new DetachedMenu[n]));
        return message;
    }

}
