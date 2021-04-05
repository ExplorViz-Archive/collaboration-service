package net.explorviz.extension.vr;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.message.ForwardedMessage;
import net.explorviz.extension.vr.message.ForwardedMessage.ShouldForward;
import net.explorviz.extension.vr.message.ReceivableMessage;
import net.explorviz.extension.vr.message.ReceivableMessageDecoder;
import net.explorviz.extension.vr.message.ReceivableMessageHandler;
import net.explorviz.extension.vr.message.SendableMessageEncoder;
import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.DetachedMenuClosedMessage;
import net.explorviz.extension.vr.message.receivable.HighlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.MenuDetachedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectMovedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectReleasedMessage;
import net.explorviz.extension.vr.message.receivable.PingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.TimestampUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;
import net.explorviz.extension.vr.message.respondable.ObjectClosedResponse;
import net.explorviz.extension.vr.message.respondable.ObjectGrabbedResponse;
import net.explorviz.extension.vr.message.sendable.InitialLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.MenuDetachedForwardMessage;
import net.explorviz.extension.vr.message.sendable.MenuDetachedResponse;
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.factory.InitialLandscapeMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.SelfConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserDisconnectedMessageFactory;
import net.explorviz.extension.vr.service.RoomService;
import net.explorviz.extension.vr.service.SessionRegistry;
import net.explorviz.extension.vr.service.TicketService;

@ServerEndpoint(value = "/v2/vr/{ticket-id}", decoders = { ReceivableMessageDecoder.class }, encoders = {
		SendableMessageEncoder.class })
@ApplicationScoped
public class VrSocket implements ReceivableMessageHandler<ShouldForward, VrSession> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VrSocket.class);

	@Inject
	RoomService roomService;

	@Inject
	TicketService ticketService;

	@Inject
	SessionRegistry sessionRegistry;

	@Inject
	SelfConnectedMessageFactory selfConnectedMessageFactory;

	@Inject
	UserConnectedMessageFactory userConnectedMessageFactory;

	@Inject
	UserDisconnectedMessageFactory userDisconnectedMessageFactory;

	@Inject
	InitialLandscapeMessageFactory sendLandscapeMessageFactory;

	@OnOpen
	public void onOpen(@PathParam("ticket-id") String ticketId, Session websocketSession) throws IOException {
		LOGGER.debug("opened websocket");

		try {
			final var ticket = ticketService.redeemTicket(ticketId);
			final var room = ticket.getRoom();
			final var userModel = ticket.getUser();
	
			// Associate the opened websocket connection with a new session.
			final var session = new VrSession(websocketSession, room, userModel);
			sessionRegistry.register(session);
	
			// Add the user from the lobby to the room.
			room.getUserService().addUser(userModel);
		} catch (Throwable throwable) {
			LOGGER.error("websocket error during initialization", throwable);
			websocketSession.close();
		}
	}

	@OnClose
	public void onClose(Session websocketSession) {
		LOGGER.debug("closed websocket");

		// If the session was closed before it was initialized, no cleanup is necessary.
		final var session = sessionRegistry.lookupSession(websocketSession);
		if (session == null) return;
		
		// First remove the association of the user with the websocket connection
		// such that the disconnect message is not broadcasted to the user who left.
		sessionRegistry.unregister(session);

		// Remove the user from the room.
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		room.getUserService().removeUser(userId);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		LOGGER.error("websocket error", throwable);
	}

	@OnMessage
	public void onMessage(ReceivableMessage message, Session senderWebsocketSession) {
		final var senderSession = sessionRegistry.lookupSession(senderWebsocketSession);
		message.setSenderSession(senderSession);

		// Process the message.
		final var shouldForward = message.handleWith(this, senderSession);

		// Optionally forward the message.
		if (shouldForward == ShouldForward.FORWARD) {
			final var room = senderSession.getRoom();
			final var userId = senderSession.getUser().getId();
			final var forwardedMessage = new ForwardedMessage(userId, message);
			room.getBroadcastService().broadcastExcept(forwardedMessage, senderSession);
		}
	}

	@Override
	public ShouldForward handleAppClosedMessage(AppClosedMessage message, VrSession session) {
		final var room = session.getRoom();
		final var success = room.getApplicationService().closeApplication(message.getAppID());
		message.sendResponse(new ObjectClosedResponse(success));
		return ShouldForward.FORWARD;
	}

	@Override
    public ShouldForward handleDetachedMenuClosedMessage(DetachedMenuClosedMessage message, VrSession session) {
    	final var room = session.getRoom();
        final var success = room.getDetachedMenuService().closeDetachedMenu(message.getMenuId());
        message.sendResponse(new ObjectClosedResponse(success));
        return ShouldForward.FORWARD;
    }

	@Override
	public ShouldForward handleObjectGrabbedMessage(ObjectGrabbedMessage message, VrSession session) {
		// Try to grab object and respond whether the operation was successful.
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		final var success = room.getGrabService().grabObject(userId, message.getObjectId());
		message.sendResponse(new ObjectGrabbedResponse(success));
		return ShouldForward.NO_FORWARD;
	}

	@Override
	public ShouldForward handleAppOpenedMessage(AppOpenedMessage message, VrSession session) {
		final var room = session.getRoom();
		room.getApplicationService().openApplication(message.getId(), message.getPosition(), message.getQuaternion(),
				message.getScale());
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleMenuDetachedMessage(MenuDetachedMessage message, VrSession session) {
		final var room = session.getRoom();
		final var objectId = room.getDetachedMenuService().detachMenu(message.getDetachId(), message.getEntityType(),
				message.getPosition(), message.getQuaternion(), message.getScale());

		// Send ID of detached menu to sender.
		message.sendResponse(new MenuDetachedResponse(objectId));

		// Notify all other users. Since the menu id is not part of the original
		// message,
		final var forwardMessage = new MenuDetachedForwardMessage(objectId, message.getEntityType(),
				message.getDetachId(), message.getPosition(), message.getQuaternion(), message.getScale());
		room.getBroadcastService().broadcastExcept(forwardMessage, session);
		return ShouldForward.NO_FORWARD;
	}

	@Override
	public ShouldForward handleObjectReleasedMessage(ObjectReleasedMessage message, VrSession session) {
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		room.getGrabService().releaseObject(userId, message.getObjectId());
		return ShouldForward.NO_FORWARD;
	}

	@Override
	public ShouldForward handleObjectMovedMessage(ObjectMovedMessage message, VrSession session) {
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		final var allowedToMove = room.getGrabService().moveObject(userId, message.getObjectId(), message.getPosition(),
				message.getQuaternion(), message.getScale());
		if (allowedToMove) {
			return ShouldForward.FORWARD;
		}
		return ShouldForward.NO_FORWARD;
	}

	@Override
	public ShouldForward handleComponentUpdateMessage(ComponentUpdateMessage message, VrSession session) {
		final var room = session.getRoom();
		room.getApplicationService().updateComponent(message.getComponentID(), message.getAppID(),
				message.getIsFoundation(), message.getIsOpened());
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleHighlightingUpdateMessage(HighlightingUpdateMessage message, VrSession session) {
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		room.getUserService().updateHighlighting(userId, message.getAppID(), message.getEntityID(),
				message.getEntityType(), message.getIsHighlighted());
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleSpectatingUpdateMessage(SpectatingUpdateMessage message, VrSession session) {
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		room.getUserService().updateSpectating(userId, message.getIsSpectating());
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleUserControllersMessage(UserControllersMessage message, VrSession session) {
		final var room = session.getRoom();
		final var userId = session.getUser().getId();
		room.getUserService().updateUserControllers(userId, message.getConnect(), message.getDisconnect());
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handlePingUpdateMessage(PingUpdateMessage message, VrSession session) {
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleUserPositionsMessage(UserPositionsMessage message, VrSession session) {
		final var room = session.getRoom();
		room.getUserService().updateUserPosition();
		return ShouldForward.FORWARD;
	}

	@Override
	public ShouldForward handleTimestampUpdateMessage(TimestampUpdateMessage message, VrSession session) {
		final var room = session.getRoom();
		room.getLandscapeService().updateTimestamp(message.getTimestamp());
		room.getApplicationService().closeAllApplications();
		room.getDetachedMenuService().closeAllDetachedMenus();
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
		final var room = event.getRoom();
		final var userModel = event.getUserModel();
		final var session = sessionRegistry.lookupSessionOfUser(room, userModel.getId());
		final var message = selfConnectedMessageFactory.makeMessage(userModel, room);
		session.send(message);
	}

	/**
	 * Broadcasts a {@link UserConnectedMessage} to all other users when a user
	 * connects.
	 * 
	 * @param event The connection event.
	 */
	public void broadcastUserConnected(@ObservesAsync UserConnectedEvent event) {
		final var room = event.getRoom();
		final var userModel = event.getUserModel();
		final var session = sessionRegistry.lookupSessionOfUser(room, userModel.getId());
		final var message = userConnectedMessageFactory.makeMessage(userModel);
		room.getBroadcastService().broadcastExcept(message, session);
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
		final var room = event.getRoom();
		final var userModel = event.getUserModel();
		final var message = userDisconnectedMessageFactory.makeMessage(userModel);
		room.getBroadcastService().broadcast(message);
	}

	/**
	 * Sends a {@link InitialLandscapeMessage} to the user who connects to the web
	 * socket.
	 * 
	 * @param event The connection event.
	 */
	public void sendLandscape(@ObservesAsync UserConnectedEvent event) {
		final var room = event.getRoom();
		final var userModel = event.getUserModel();
		final var session = sessionRegistry.lookupSessionOfUser(room, userModel.getId());
		final var message = sendLandscapeMessageFactory.makeMessage(room);
		session.send(message);
	}
}
