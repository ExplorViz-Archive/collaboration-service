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

import net.explorviz.extension.vr.message.ForwardedMessage;
import net.explorviz.extension.vr.message.ForwardedMessage.ShouldForward;
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
public class VRSocket implements ReceivedMessageHandler<ShouldForward, Session> {

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
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.debug("closed websocket");
        final String userId = this.sessionRegistry.lookupId(session);
        if (userId != null) {
            this.userService.removeUser(userId);
            this.sessionRegistry.unregister(userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("websocket error", throwable);
    }

    @OnMessage
    public void onMessageList(List<VRMessage> messages, Session senderSession) {
        // Handle all messages that are receivable.
        for (VRMessage message : messages) {
            if (message instanceof ReceivedMessage) {
                handleMessage((ReceivedMessage) message, senderSession);
            } else if (message != null) {
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
        // Process the message.
        LOGGER.debug("received message: {}", message);
        final var shouldForward = message.handleWith(this, senderSession);

        // Optionally forward the message.
        if (ShouldForward.FORWARD.equals(shouldForward)) {
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
    public ShouldForward handleAppGrabbedMessage(AppGrabbedMessage message, Session senderSession) {
        this.entityService.grabbApp(message.getAppID(), ""); // TODO userId
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleAppOpenedMessage(AppOpenedMessage message, Session senderSession) {
        this.entityService.openApp(message.getId(), message.getPosition(), message.getQuaternion());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleAppReleasedMessage(AppReleasedMessage message, Session senderSession) {
        this.entityService.releaseApp(message.getId(), message.getPosition(), message.getQuaternion());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleAppTranslatedMessage(AppTranslatedMessage message, Session senderSession) {
        this.entityService.translateApp();
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleComponentUpdateMessage(ComponentUpdateMessage message, Session senderSession) {
        this.entityService.updateComponent(message.getComponentID(), message.getAppID(), message.getIsFoundation(),
                message.getIsOpened());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleHightlightingUpdateMessage(HightlightingUpdateMessage message, Session senderSession) {
        this.userService.updateHighlighting("", message.getAppID(), message.getEntityID(), message.getEntityType(),
                message.getIsHighlighted()); // TODO userId
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleLandscapePositionMessage(LandscapePositionMessage message, Session senderSession) {
        this.entityService.updateLandscapePosition(message.getOffset(), message.getQuaternion());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleNodegroupUpdateMessage(NodegroupUpdateMessage message, Session senderSession) {
        this.entityService.updateNodegroup(message.getId(), message.getIsOpen());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleSpectatingUpdateMessage(SpectatingUpdateMessage message, Session senderSession) {
        this.userService.updateSpectating("", message.getIsSpectating()); // TODO userId
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleSystemUpdateMessage(SystemUpdateMessage message, Session senderSession) {
        this.entityService.updateSystem(message.getId(), message.getIsOpen());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserControllersMessage(UserControllersMessage message, Session senderSession) {
        this.userService.updateUserControllers("", message.getConnect(), message.getDisconnect()); // TODO userId
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserPositionsMessage(UserPositionsMessage message, Session senderSession) {
        this.userService.updateUserPosition();
        return ShouldForward.FORWARD;
    }
}
