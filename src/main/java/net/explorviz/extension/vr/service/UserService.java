package net.explorviz.extension.vr.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.model.UserModel.State;

@ApplicationScoped
public class UserService {

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();

    public void updateUserPosition() {

    }

    public void updateUserControllers(String userId, Controllers connect, Controllers disconnect) {
        UserModel user = this.users.get(userId);
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
        UserModel user = this.users.get(userId);
        if (user != null) {
            user.setState(isSpectating ? State.SPECTATING : State.CONNECTED);

        }
    }

    public void updateHighlighting(String userId, String appId, String entityId, String entityType,
            boolean isHighlighted) {
        UserModel user = this.users.get(userId);
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

    public String addUser() {
    	UserModel userModel = new UserModel();
        this.users.put(userModel.getId(), userModel);
        return userModel.getId();
    }

    public void removeUser(String userId) {
        this.users.remove(userId);
    }

}
