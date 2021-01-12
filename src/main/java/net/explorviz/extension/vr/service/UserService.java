package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.model.UserModel.State;

@ApplicationScoped
public class UserService {

    @Inject
    Event<UserConnectedEvent> userConnectedEvent;

    @Inject
    Event<UserDisconnectedEvent> userDisconnectedEvent;

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();

    /**
     * Gets the models of all currently connected users.
     * 
     * @return
     */
    public Iterable<UserModel> getUserModels() {
        return users.values();
    }

    public void updateUserPosition() {

    }

    public void updateUserControllers(String userId, Controllers connect, Controllers disconnect) {
        UserModel user = users.get(userId);
        if (user != null) {
            if (connect != null) {
                if (connect.getController1() != null) {
                    user.getController1().setName(connect.getController1());
                }
                if (connect.getController2() != null) {
                    user.getController2().setName(connect.getController2());
                }
            }
            if (disconnect != null) {
                if (disconnect.getController1() != null) {
                    user.getController1().setName(null);
                }
                if (disconnect.getController2() != null) {
                    user.getController2().setName(null);
                }
            }
        }
    }

    public void updateSpectating(String userId, boolean isSpectating) {
        UserModel user = users.get(userId);
        if (user != null) {
            user.setState(isSpectating ? State.SPECTATING : State.CONNECTED);

        }
    }

    public void updateHighlighting(String userId, String appId, String entityId, String entityType,
            boolean isHighlighted) {
        UserModel user = users.get(userId);
        if (!isHighlighted) {
            user.setHighlighted(false);
            return;
        }

        // Overwrite highlighting of other users (if they highlighted same entity)
        for (final UserModel otherUser : this.users.values()) {
            if (otherUser.hasHighlightedEntity() && otherUser.getHighlightedEntity().getHighlightedApp().equals(appId)
                    && otherUser.getHighlightedEntity().getHighlightedEntity().equals(entityId)) {
                otherUser.setHighlighted(false);
            }
        }
        user.setHighlightedEntity(isHighlighted, appId, entityType, entityId);
    }

    public String addUser(String userName) {
        UserModel userModel = new UserModel();
        userModel.setUserName(userName);
        users.put(userModel.getId(), userModel);
        userConnectedEvent.fireAsync(new UserConnectedEvent(userModel));
        return userModel.getId();
    }

    public void removeUser(String userId) {
        UserModel userModel = users.remove(userId);
        if (userModel != null) {
            userDisconnectedEvent.fireAsync(new UserDisconnectedEvent(userModel));
        }
    }

    public Collection<UserModel> getUsers() {
        return this.users.values();
    }

}
