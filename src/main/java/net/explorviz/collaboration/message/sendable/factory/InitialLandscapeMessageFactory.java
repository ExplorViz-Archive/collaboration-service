package net.explorviz.collaboration.message.sendable.factory;

import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.App;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.DetachedMenu;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.HighlightingObject;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.Landscape;
import net.explorviz.collaboration.model.ApplicationModel;
import net.explorviz.collaboration.model.DetachedMenuModel;
import net.explorviz.collaboration.model.HighlightingModel;
import net.explorviz.collaboration.model.LandscapeModel;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

@ApplicationScoped
public class InitialLandscapeMessageFactory {

  public InitialLandscapeMessage makeMessage(final Room room) { // NOPMD
    final ArrayList<HighlightingObject> externCommunicationLinks = new ArrayList<>();
    // apps
    final ArrayList<App> appArray = new ArrayList<>();
    for (final ApplicationModel app : room.getApplicationService().getApplications()) {

      final App appObj = new App(); // NOPMD
      appObj.setId(app.getId());
      appObj.setPosition(app.getPosition());
      appObj.setQuaternion(app.getQuaternion());
      appObj.setScale(app.getScale());

      final ArrayList<String> componentArray = new ArrayList<>(); // NOPMD
      for (final String componentId : app.getOpenComponents()) {
        componentArray.add(componentId);
      }

      final ArrayList<HighlightingObject> componentHighlightedArray = new ArrayList<>(); // NOPMD
      for (final UserModel user : room.getUserService().getUsers()) {
        if (user.containsHighlightedEntity()) {
          final HighlightingModel[] highlighted =  user.getHighlightedEntities()
          .toArray(n -> new HighlightingModel[n]); // NOPMD
  
          for (final HighlightingModel highlightedEntity : highlighted) {

            if (highlightedEntity.getAppId().equals(appObj.getId())) {

              final HighlightingObject highlightingObj = new HighlightingObject(); // NOPMD
              highlightingObj.setUserId(user.getId());
              highlightingObj.setColor(user.getColor());

              highlightingObj.setAppId(highlightedEntity.getAppId());
              highlightingObj.setEntityType(highlightedEntity.getEntityType());
              highlightingObj.setEntityId(highlightedEntity.getEntityId());
              highlightingObj.setHighlighted(true);
              componentHighlightedArray.add(highlightingObj);
            } else if ("".equals(highlightedEntity.getAppId())) { // extern communication line
              final HighlightingObject highlightingObj = new HighlightingObject(); // NOPMD
              highlightingObj.setUserId(user.getId());
              highlightingObj.setColor(user.getColor());

              highlightingObj.setAppId(highlightedEntity.getAppId());
              highlightingObj.setEntityType(highlightedEntity.getEntityType());
              highlightingObj.setEntityId(highlightedEntity.getEntityId());
              highlightingObj.setHighlighted(true);

              boolean isEntityIdInList = false;
              String id = highlightingObj.getEntityId();

              for (final HighlightingObject externCommunicationLink : externCommunicationLinks) { 
                // TODO: use a HashSet instead of ArrayList for constant access time
                if (externCommunicationLink.getEntityId().equals(id)) {
                  isEntityIdInList = true;
                  break;
                }
              }

              if (!isEntityIdInList) {
                externCommunicationLinks.add(highlightingObj);
              }
            }
          }
        }
      }


      appObj.setOpenComponents(componentArray.toArray(n -> new String[n])); // NOPMD
      appObj.setHighlightedComponents(
          componentHighlightedArray.toArray(n -> new HighlightingObject[n])); // NOPMD

      System.out.println(appObj.getHighlightedComponents());
      appArray.add(appObj);

    }

    // landscape position
    final Landscape landscapeObj = new Landscape();
    final LandscapeModel landscape = room.getLandscapeService().getLandscape();
    landscapeObj.setLandscapeToken(landscape.getLandscapeToken());
    landscapeObj.setTimestamp(landscape.getTimestamp());

    // detached menus
    final ArrayList<DetachedMenu> detachedMenusArray = new ArrayList<>();
    for (final DetachedMenuModel menu : room.getDetachedMenuService().getDetachedMenus()) {
      final DetachedMenu menuObj = new DetachedMenu(); // NOPMD
      menuObj.setObjectId(menu.getId());
      menuObj.setUserId(menu.getUserId());
      menuObj.setDetachId(menu.getDetachId());
      menuObj.setEntityId(menu.getDetachId());
      menuObj.setPosition(menu.getPosition());
      menuObj.setQuaternion(menu.getQuaternion());
      menuObj.setEntityType(menu.getEntityType());
      menuObj.setScale(menu.getScale());
      detachedMenusArray.add(menuObj);
    }

    final InitialLandscapeMessage message = new InitialLandscapeMessage();
    message.setOpenApps(appArray.toArray(n -> new App[n]));
    message.setLandscape(landscapeObj);
    message.setDetachedMenus(detachedMenusArray.toArray(n -> new DetachedMenu[n]));

    // highlighting for extern communication links only
    message.setHighlightedExternCommunicationLinks(externCommunicationLinks
        .toArray(n -> new HighlightingObject[n])); 
    return message;
  }

}
