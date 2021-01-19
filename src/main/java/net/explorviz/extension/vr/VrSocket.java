package net.explorviz.extension.vr;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.message.ForwardedMessage;
import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageDecoder;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;
import net.explorviz.extension.vr.message.ResponseMessage;
import net.explorviz.extension.vr.message.SendableMessageEncoder;
import net.explorviz.extension.vr.message.ForwardedMessage.ShouldForward;
import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.HighlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.ObjectGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectMovedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectReleasedMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;
import net.explorviz.extension.vr.message.respondable.ObjectGrabbedResponse;
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.factory.SelfConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.SendLandscapeMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserDisconnectedMessageFactory;
import net.explorviz.extension.vr.service.BroadcastService;
import net.explorviz.extension.vr.service.EntityService;
import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.SessionRegistry;
import net.explorviz.extension.vr.service.UserService;

@ServerEndpoint(value = "/v2/vr", decoders = { ReceivableMessageDecoder.class }, encoders = {
        SendableMessageEncoder.class })
@ApplicationScoped
public class VrSocket implements ReceivableMessageHandler<ShouldForward, Session> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VrSocket.class);

    @Inject
    BroadcastService broadcastService;

    @Inject
    SessionRegistry sessionRegistry;

    @Inject
    UserService userService;

    @Inject
    EntityService entityService;

    @Inject
    IdGenerationService idGenerationService;

    @Inject
    SelfConnectedMessageFactory selfConnectedMessageFactory;

    @Inject
    UserConnectedMessageFactory userConnectedMessageFactory;

    @Inject
    UserDisconnectedMessageFactory userDisconnectedMessageFactory;

    @Inject
    SendLandscapeMessageFactory sendLandscapeMessageFactory;

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.debug("opened websocket");
        final var userModel = userService.makeUserModel();
        sessionRegistry.register(userModel.getId(), session);
        userService.addUser(userModel);
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("closed websocket");
        final String userId = sessionRegistry.lookupId(session);
        sessionRegistry.unregister(userId);
        userService.removeUser(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("websocket error", throwable);
    }

    @OnMessage
    public void onMessage(ReceivableMessage message, Session senderSession) {
        // Process the message.
        final var shouldForward = message.handleWith(this, senderSession);

        // Optionally forward the message.
        if (shouldForward == ShouldForward.FORWARD) {
            final var userId = sessionRegistry.lookupId(senderSession);
            final var forwardedMessage = new ForwardedMessage(userId, message);
            broadcastService.broadcastExcept(forwardedMessage, senderSession);
        }
    }

    @Override
    public ShouldForward handleAppClosedMessage(AppClosedMessage message, Session senderSession) {
        this.entityService.closeApp(message.getAppID());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleObjectGrabbedMessage(ObjectGrabbedMessage message, Session senderSession) {
        // Try to grab object and respond whether the operation was successful.
        final var success = this.entityService.grabbObject(sessionRegistry.lookupId(senderSession),
                message.getObjectId());
        final var response = new ResponseMessage(
            message.getNonce(),
            new ObjectGrabbedResponse(message.getObjectId(), success)
        );
        broadcastService.sendTo(response, senderSession);
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleAppOpenedMessage(AppOpenedMessage message, Session senderSession) {
        this.entityService.openApp(message.getId(), message.getPosition(), message.getQuaternion());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleObjectReleasedMessage(ObjectReleasedMessage message, Session senderSession) {
        this.entityService.releaseObject(sessionRegistry.lookupId(senderSession), message.getObjectId());
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleObjectMovedMessage(ObjectMovedMessage message, Session senderSession) {
        final var forward = (this.entityService.moveObject(sessionRegistry.lookupId(senderSession),
                message.getObjectId(), message.getPosition(), message.getQuaternion()));
        if (forward) {
            return ShouldForward.FORWARD;
        }
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleComponentUpdateMessage(ComponentUpdateMessage message, Session senderSession) {
        this.entityService.updateComponent(message.getComponentID(), message.getAppID(), message.getIsFoundation(),
                message.getIsOpened());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleHightlightingUpdateMessage(HighlightingUpdateMessage message, Session senderSession) {
        this.userService.updateHighlighting(sessionRegistry.lookupId(senderSession), message.getAppID(),
                message.getEntityID(), message.getEntityType(), message.getIsHighlighted());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleSpectatingUpdateMessage(SpectatingUpdateMessage message, Session senderSession) {
        this.userService.updateSpectating(sessionRegistry.lookupId(senderSession), message.getIsSpectating());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserControllersMessage(UserControllersMessage message, Session senderSession) {
        this.userService.updateUserControllers(sessionRegistry.lookupId(senderSession), message.getConnect(),
                message.getDisconnect());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserPositionsMessage(UserPositionsMessage message, Session senderSession) {
        this.userService.updateUserPosition();
        return ShouldForward.FORWARD;
    }

    /**
     * Sends the list of currently connected users (see
     * {@link SelfConnectedMessage}) when a user connects.
     * 
     * @param event The connection event.
     */
    public void sendInitialUserList(@ObservesAsync UserConnectedEvent event) {
        LOGGER.debug("sending self connected message!");
        final var userModel = event.getUserModel();
        final var message = selfConnectedMessageFactory.makeMessage(userModel);
        broadcastService.sendToUser(message, userModel.getId());
    }

    /**
     * Broadcasts a {@link UserConnectedMessage} to all other users when a user
     * connects.
     * 
     * @param event The connection event.
     */
    public void broadcastUserConnected(@ObservesAsync UserConnectedEvent event) {
        final var userModel = event.getUserModel();
        final var message = userConnectedMessageFactory.makeMessage(userModel);
        broadcastService.broadcastExceptUser(message, userModel.getId());
    }

    /**
     * Broadcasts a {@link UserDisconnecedMessage} to all other users when a user
     * disconnects.
     * 
     * The web socket connection of the disconnected user should be removed from the
     * {@link sessiojnRegistry} before the event is fired.
     * 
     * @param event The disconnection event.
     */
    public void broadcastUserDisconnected(@ObservesAsync UserDisconnectedEvent event) {
        final var userModel = event.getUserModel();
        final var message = userDisconnectedMessageFactory.makeMessage(userModel);
        broadcastService.broadcast(message);
    }

    /**
     * Sends a {@link SendLandscapeMessage} to the user who connects to the web
     * socket.
     * 
     * @param event The connection event.
     */
    public void sendLandscape(@ObservesAsync UserConnectedEvent event) {
        final var userModel = event.getUserModel();
        final var message = sendLandscapeMessageFactory.makeMessage();
        broadcastService.sendToUser(message, userModel.getId());
    }
}
