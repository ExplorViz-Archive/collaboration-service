package net.explorviz.extension.vr;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.extension.vr.message.ReceivedMessage;
import net.explorviz.extension.vr.message.ReceivedMessageHandler;
import net.explorviz.extension.vr.message.VRMessage;
import net.explorviz.extension.vr.message.VRMessageDecoder;
import net.explorviz.extension.vr.message.VRMessageEncoder;
import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.ConnectRequestMessage;
import net.explorviz.extension.vr.message.receivable.DisconnectRequestMessage;
import net.explorviz.extension.vr.message.receivable.HightlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.NodegroupUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SystemUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;
import net.explorviz.extension.vr.service.BroadcastService;
import net.explorviz.extension.vr.service.EntityService;
import net.explorviz.extension.vr.service.SessionRegistry;
import net.explorviz.extension.vr.service.UserService;

@ServerEndpoint(value = "/v2/vr", decoders = { VRMessageDecoder.class }, encoders = { VRMessageEncoder.class })
@ApplicationScoped
public class VRSocket implements ReceivedMessageHandler<Boolean, Session> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VRSocket.class);

    @Inject
    BroadcastService broadcastService;

    @Inject
    SessionRegistry sessionRegistry;

    @Inject
    UserService userService;

    @Inject
    EntityService entityService;

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.debug("opened websocket");
        final String userId = this.userService.addUser();
        this.sessionRegistry.register(userId, session);
        // sendLandscape
        // connect
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("closed websocket");
        final String userId = this.sessionRegistry.lookupId(session);
        this.userService.removeUser(userId);
        this.sessionRegistry.unregister(userId);
        // disonnect
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("websocket error", throwable);
    }

    @OnMessage
    public void onMessageList(List<VRMessage> messages, Session senderSession) {
        if (messages == null)
            return;

        // Handle all messages that are receivable.
        for (VRMessage message : messages) {
            if (message instanceof ReceivedMessage) {
                handleMessage((ReceivedMessage) message, senderSession);
            } else {
                LOGGER.debug("received message of forbidden type: {}", message);
            }
        }
    }

    /**
     * Called for each message that is received.
     *
     * The received message is logged and the corresponding method from
     * {@link ReceivedMessageHandler} is invoked.
     *
     * @param message       The received message.
     * @param senderSession The websocket connection of the client that sent the
     *                      message.
     */
    public void handleMessage(ReceivedMessage message, Session senderSession) {
        if (message == null)
            return;

        // Process the message.
        LOGGER.debug("received message: {}", message);
        final var shouldForward = message.handleWith(this, senderSession);

        // Optionally forward the message.
        if (Boolean.TRUE.equals(shouldForward)) {
            broadcastService.broadcastExcept(message, senderSession);
        }
    }

    @Override
    public Boolean handleAppClosedMessage(AppClosedMessage message, Session senderSession) {
        this.entityService.closeApp(message.getAppID());
        return null;
    }

    @Override
    public Boolean handleAppGrabbedMessage(AppGrabbedMessage message, Session senderSession) {
        this.entityService.grabbApp(message.getAppID(), this.sessionRegistry.lookupId(senderSession));
        return null;
    }

    @Override
    public Boolean handleAppOpenedMessage(AppOpenedMessage message, Session senderSession) {
        this.entityService.openApp(message.getId(), message.getPosition(), message.getQuaternion());
        return null;
    }

    @Override
    public Boolean handleAppReleasedMessage(AppReleasedMessage message, Session senderSession) {
        this.entityService.releaseApp(message.getId(), message.getPosition(), message.getQuaternion());
        return null;
    }

    @Override
    public Boolean handleAppTranslatedMessage(AppTranslatedMessage message, Session senderSession) {
        this.entityService.translateApp();
        return null;
    }

    @Override
    public Boolean handleComponentUpdateMessage(ComponentUpdateMessage message, Session senderSession) {
        this.entityService.updateComponent(message.getComponentID(), message.getAppID(), message.getIsFoundation(),
                message.getIsOpened());
        return null;
    }

    @Override
    public Boolean handleHightlightingUpdateMessage(HightlightingUpdateMessage message, Session senderSession) {
        this.userService.updateHighlighting(this.sessionRegistry.lookupId(senderSession), message.getAppID(),
                message.getEntityID(), message.getEntityType(), message.getIsHighlighted());
        return null;
    }

    @Override
    public Boolean handleLandscapePositionMessage(LandscapePositionMessage message, Session senderSession) {
        this.entityService.updateLandscapePosition(message.getOffset(), message.getQuaternion());
        return null;
    }

    @Override
    public Boolean handleNodegroupUpdateMessage(NodegroupUpdateMessage message, Session senderSession) {
        this.entityService.updateNodegroup(message.getId(), message.getIsOpen());
        return null;
    }

    @Override
    public Boolean handleSpectatingUpdateMessage(SpectatingUpdateMessage message, Session senderSession) {
        this.userService.updateSpectating(this.sessionRegistry.lookupId(senderSession), message.getIsSpectating());
        return null;
    }

    @Override
    public Boolean handleSystemUpdateMessage(SystemUpdateMessage message, Session senderSession) {
        this.entityService.updateSystem(message.getId(), message.getIsOpen());
        return null;
    }

    @Override
    public Boolean handleUserControllersMessage(UserControllersMessage message, Session senderSession) {
        this.userService.updateUserControllers(this.sessionRegistry.lookupId(senderSession), message.getConnect(),
                message.getDisconnect());
        return null;
    }

    @Override
    public Boolean handleUserPositionsMessage(UserPositionsMessage message, Session senderSession) {
        this.userService.updateUserPosition();
        return null;
    }

    @Override
    public Boolean handleConnectRequestMessage(ConnectRequestMessage message, Session senderSession) {
        // TODO remove additional connect and disconnect request (frontend)
        return null;
    }

    @Override
    public Boolean handleDisconnectRequestMessage(DisconnectRequestMessage message, Session senderSession) {
        // TODO remove additional connect and disconnect request (frontend)
        return null;
    }

}
