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
public class VRSocket implements ReceivedMessageHandler<Boolean> {

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
        final var shouldForward = message.handleWith(this);

        // Optionally forward the message.
        if (Boolean.TRUE.equals(shouldForward)) {
            broadcastService.broadcastExcept(message, senderSession);
        }
    }

	@Override
	public Boolean handleAppClosedMessage(AppClosedMessage message) {
		this.entityService.closeApp(message.getAppID());
		return null;
	}

	@Override
	public Boolean handleAppGrabbedMessage(AppGrabbedMessage message) {
		this.entityService.grabbApp(message.getAppID(), ""); // TODO userId
		return null;
	}

	@Override
	public Boolean handleAppOpenedMessage(AppOpenedMessage message) {
		this.entityService.openApp(message.getId(), message.getPosition(), message.getQuaternion());
		return null;
	}

	@Override
	public Boolean handleAppReleasedMessage(AppReleasedMessage message) {
		this.entityService.releaseApp(message.getId(), message.getPosition(), message.getQuaternion());
		return null;
	}

	@Override
	public Boolean handleAppTranslatedMessage(AppTranslatedMessage message) {
		this.entityService.translateApp();
		return null;
	}

	@Override
	public Boolean handleComponentUpdateMessage(ComponentUpdateMessage message) {
		this.entityService.updateComponent(message.getComponentID(), message.getAppID(), message.getIsFoundation(),
				message.getIsOpened());
		return null;
	}

	@Override
	public Boolean handleHightlightingUpdateMessage(HightlightingUpdateMessage message) {
		this.userService.updateHighlighting("", message.getAppID(), message.getEntityID(), message.getEntityType(),
				message.getIsHighlighted()); //TODO userId
		return null;
	}

	@Override
	public Boolean handleLandscapePositionMessage(LandscapePositionMessage message) {
		this.entityService.updateLandscapePosition(message.getOffset(), message.getQuaternion());
		return null;
	}

	@Override
	public Boolean handleNodegroupUpdateMessage(NodegroupUpdateMessage message) {
		this.entityService.updateNodegroup(message.getId(), message.getIsOpen());
		return null;
	}

	@Override
	public Boolean handleSpectatingUpdateMessage(SpectatingUpdateMessage message) {
		this.userService.updateSpectating("", message.getIsSpectating()); //TODO userId
		return null;
	}

	@Override
	public Boolean handleSystemUpdateMessage(SystemUpdateMessage message) {
		this.entityService.updateSystem(message.getId(), message.getIsOpen());
		return null;
	}

	@Override
	public Boolean handleUserControllersMessage(UserControllersMessage message) {
		this.userService.updateUserControllers("", message.getConnect(), message.getDisconnect()); //TODO userId
		return null;
	}

	@Override
	public Boolean handleUserPositionsMessage(UserPositionsMessage message) {
		this.userService.updateUserPosition();
		return null;
	}

	@Override
	public Boolean handleConnectRequestMessage(ConnectRequestMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean handleDisconnectRequestMessage(DisconnectRequestMessage message) {
		// TODO Auto-generated method stub
		return null;
	}
}
