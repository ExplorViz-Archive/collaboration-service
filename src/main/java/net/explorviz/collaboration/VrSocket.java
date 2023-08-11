package net.explorviz.collaboration; // NOPMD

import io.quarkus.scheduler.Scheduled;
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
import net.explorviz.collaboration.event.UserConnectedEvent;
import net.explorviz.collaboration.event.UserDisconnectedEvent;
import net.explorviz.collaboration.message.ForwardedMessage;
import net.explorviz.collaboration.message.ForwardedMessage.ShouldForward;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageDecoder;
import net.explorviz.collaboration.message.ReceivableMessageHandler;
import net.explorviz.collaboration.message.SendableMessageEncoder;
import net.explorviz.collaboration.message.receivable.AppClosedMessage;
import net.explorviz.collaboration.message.receivable.AppOpenedMessage;
import net.explorviz.collaboration.message.receivable.ComponentUpdateMessage;
import net.explorviz.collaboration.message.receivable.DetachedMenuClosedMessage;
import net.explorviz.collaboration.message.receivable.HeatmapUpdateMessage;
import net.explorviz.collaboration.message.receivable.HighlightingUpdateMessage;
import net.explorviz.collaboration.message.receivable.JoinVrMessage;
import net.explorviz.collaboration.message.receivable.MenuDetachedMessage;
import net.explorviz.collaboration.message.receivable.MousePingUpdateMessage;
import net.explorviz.collaboration.message.receivable.ObjectGrabbedMessage;
import net.explorviz.collaboration.message.receivable.ObjectMovedMessage;
import net.explorviz.collaboration.message.receivable.ObjectReleasedMessage;
import net.explorviz.collaboration.message.receivable.PingUpdateMessage;
import net.explorviz.collaboration.message.receivable.SpectatingUpdateMessage;
import net.explorviz.collaboration.message.receivable.TimestampUpdateMessage;
import net.explorviz.collaboration.message.receivable.TransparencyUpdateMessage;
import net.explorviz.collaboration.message.receivable.UserControllerConnectMessage;
import net.explorviz.collaboration.message.receivable.UserControllerDisconnectMessage;
import net.explorviz.collaboration.message.receivable.UserPositionsMessage;
import net.explorviz.collaboration.message.respondable.ObjectClosedResponse;
import net.explorviz.collaboration.message.respondable.ObjectGrabbedResponse;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage;
import net.explorviz.collaboration.message.sendable.MenuDetachedForwardMessage;
import net.explorviz.collaboration.message.sendable.MenuDetachedResponse;
import net.explorviz.collaboration.message.sendable.SelfConnectedMessage;
import net.explorviz.collaboration.message.sendable.UserConnectedMessage;
import net.explorviz.collaboration.message.sendable.factory.InitialLandscapeMessageFactory;
import net.explorviz.collaboration.message.sendable.factory.SelfConnectedMessageFactory;
import net.explorviz.collaboration.message.sendable.factory.TimestampUpdateTimerMessageFactory;
import net.explorviz.collaboration.message.sendable.factory.UserConnectedMessageFactory;
import net.explorviz.collaboration.message.sendable.factory.UserDisconnectedMessageFactory;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.RoomService;
import net.explorviz.collaboration.service.SessionRegistry;
import net.explorviz.collaboration.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/v2/vr/{ticket-id}", decoders = {
    ReceivableMessageDecoder.class}, encoders = {SendableMessageEncoder.class})
@ApplicationScoped
public class VrSocket implements ReceivableMessageHandler<ShouldForward, VrSession> { // NOPMD

  private static final Logger LOGGER = LoggerFactory.getLogger(VrSocket.class);

  @Inject
  /* default */ RoomService roomService; // NOCS

  @Inject
  /* default */ TicketService ticketService; // NOCS

  @Inject
  /* default */ SessionRegistry sessionRegistry; // NOCS

  @Inject
  /* default */ SelfConnectedMessageFactory selfConnectedMessageFactory; // NOCS

  @Inject
  /* default */ UserConnectedMessageFactory userConnectedMessageFactory; // NOCS

  @Inject
  /* default */ UserDisconnectedMessageFactory userDisconnectedMessageFactory; // NOCS

  @Inject
  /* default */ InitialLandscapeMessageFactory sendLandscapeMessageFactory;// NOCS

  @Inject
  /* default */ TimestampUpdateTimerMessageFactory timestampUpdateTimerMessageFactory;// NOCS

  @OnOpen public void onOpen(@PathParam("ticket-id") final String ticketId,
      final Session websocketSession) {
    LOGGER.debug("opened websocket");


    final var ticket = this.ticketService.redeemTicket(ticketId);
    final var room = ticket.getRoom();
    final var userModel = ticket.getUser();

    // Associate the opened websocket connection with a new session.
    final var session = new VrSession(websocketSession, room, userModel);
    this.sessionRegistry.register(session);

    // Add the user from the lobby to the room.
    room.getUserService().addUser(userModel);

  }

  @OnClose public void onClose(final Session websocketSession) {
    LOGGER.debug("closed websocket");

    // If the session was closed before it was initialized, no cleanup is necessary.
    final var session = this.sessionRegistry.lookupSession(websocketSession);
    if (session == null) {
      return;
    }

    // First remove the association of the user with the websocket connection
    // such that the disconnect message is not broadcasted to the user who left.
    this.sessionRegistry.unregister(session);

    // Remove the user from the room.
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().removeUser(user);
  }

  @OnError public void onError(final Session session, final Throwable throwable) {
    LOGGER.error("websocket error", throwable);
  }

  @OnMessage
  public void onMessage(final ReceivableMessage message, final Session senderWebsocketSession) {
    final var senderSession = this.sessionRegistry.lookupSession(senderWebsocketSession);
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

  @Override public ShouldForward handleAppClosedMessage(final AppClosedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var success = room.getApplicationService().closeApplication(message.getAppId());
    message.sendResponse(new ObjectClosedResponse(success));
    return ShouldForward.FORWARD;
  }

  @Override
  public ShouldForward handleDetachedMenuClosedMessage(final DetachedMenuClosedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var success = room.getDetachedMenuService().closeDetachedMenu(message.getMenuId());
    message.sendResponse(new ObjectClosedResponse(success));
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleObjectGrabbedMessage(final ObjectGrabbedMessage message,
      final VrSession session) {
    // Try to grab object and respond whether the operation was successful.
    final var room = session.getRoom();
    final var userId = session.getUser().getId();
    final var success = room.getGrabService().grabObject(userId, message.getObjectId());
    message.sendResponse(new ObjectGrabbedResponse(success));
    return ShouldForward.NO_FORWARD;
  }

  @Override public ShouldForward handleAppOpenedMessage(final AppOpenedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    room.getApplicationService()
        .openApplication(message.getId(), message.getPosition(), message.getQuaternion(),
            message.getScale());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleMenuDetachedMessage(final MenuDetachedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var objectId = room.getDetachedMenuService()
        .detachMenu(message.getDetachId(), message.getSenderSession().getUser().getId(), message.getEntityType(), message.getPosition(),
            message.getQuaternion(), message.getScale());

    // Send ID of detached menu to sender.
    message.sendResponse(new MenuDetachedResponse(objectId));

    // Notify all other users. Since the menu id is not part of the original
    // message,
    final var forwardMessage =
        new MenuDetachedForwardMessage(objectId, message.getSenderSession().getUser().getId(),
            message.getEntityType(), message.getDetachId(), message.getPosition(),
            message.getQuaternion(), message.getScale());
    room.getBroadcastService().broadcastExcept(forwardMessage, session);
    return ShouldForward.NO_FORWARD;
  }

  @Override public ShouldForward handleObjectReleasedMessage(final ObjectReleasedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var userId = session.getUser().getId();
    room.getGrabService().releaseObject(userId, message.getObjectId());
    return ShouldForward.NO_FORWARD;
  }

  @Override public ShouldForward handleObjectMovedMessage(final ObjectMovedMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var userId = session.getUser().getId();
    final var allowedToMove = room.getGrabService()
        .moveObject(userId, message.getObjectId(), message.getPosition(), message.getQuaternion(),
            message.getScale());
    if (allowedToMove) {
      return ShouldForward.FORWARD;
    }
    return ShouldForward.NO_FORWARD;
  }

  @Override public ShouldForward handleComponentUpdateMessage(final ComponentUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    room.getApplicationService()
        .updateComponent(message.getComponentId(), message.getAppId(), message.isFoundation(),
            message.isOpened());
    return ShouldForward.FORWARD;
  }

  @Override
  public ShouldForward handleHighlightingUpdateMessage(final HighlightingUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().updateHighlighting(user, message.getAppId(), message.getEntityId(),
        message.getEntityType(), message.isHighlighted());
    return ShouldForward.FORWARD;
  }

  @Override
  public ShouldForward handleTransparencyUpdateMessage(final TransparencyUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    room.getApplicationService().updateTransparency(message.getAppId(), message.getEntityIds());
    return ShouldForward.NO_FORWARD;
  }

  @Override
  public ShouldForward handleSpectatingUpdateMessage(final SpectatingUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().updateSpectating(user, message.isSpectating());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleHeatmapUpdateMessage(final HeatmapUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    room.getHeatmapService().setActive(message.isActive());
    room.getHeatmapService().setMode(message.getMode());
    room.getHeatmapService().setMetric(message.getMetric());
    room.getHeatmapService().setApplicationId(message.getApplicationId());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleUserControllerConnectMessage(
      final UserControllerConnectMessage message, final VrSession session) {
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().connectController(user, message.getController());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleUserControllerDisconnectMessage(
      final UserControllerDisconnectMessage message, final VrSession session) {
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().disconnectController(user, message.getControllerId());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleJoinVrMessage(
      final JoinVrMessage message, final VrSession session) {
      return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handlePingUpdateMessage(final PingUpdateMessage message,
      final VrSession session) {
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleMousePingUpdateMessage(final MousePingUpdateMessage message,
      final VrSession session) {
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleUserPositionsMessage(final UserPositionsMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    final var user = session.getUser();
    room.getUserService().updateUserPose(user, message.getCamera());
    room.getUserService().updateControllerPose(user.getController(0), message.getController1());
    room.getUserService().updateControllerPose(user.getController(1), message.getController2());
    return ShouldForward.FORWARD;
  }

  @Override public ShouldForward handleTimestampUpdateMessage(final TimestampUpdateMessage message,
      final VrSession session) {
    final var room = session.getRoom();
    room.getLandscapeService().updateTimestamp(message.getTimestamp());
    room.getApplicationService().closeAllApplications();
    room.getDetachedMenuService().closeAllDetachedMenus();
    return ShouldForward.FORWARD;
  }

  /**
   * Sends the list of currently connected users (see {@link SelfConnectedMessage}) when a user
   * connects.
   *
   * @param event The connection event.
   */
  public void sendInitialUserList(@ObservesAsync final UserConnectedEvent event) {
    LOGGER.debug("sending self connected message!");
    final var room = event.getRoom();
    final var userModel = event.getUserModel();
    final var session = this.sessionRegistry.lookupSessionOfUser(room, userModel.getId());
    final var message = this.selfConnectedMessageFactory.makeMessage(userModel, room);
    session.send(message);
  }

  /**
   * Broadcasts a {@link UserConnectedMessage} to all other users when a user connects.
   *
   * @param event The connection event.
   */
  public void broadcastUserConnected(@ObservesAsync final UserConnectedEvent event) {
    final var room = event.getRoom();
    final var userModel = event.getUserModel();
    final var session = this.sessionRegistry.lookupSessionOfUser(room, userModel.getId());
    final var message = this.userConnectedMessageFactory.makeMessage(userModel);
    room.getBroadcastService().broadcastExcept(message, session);
  }

  /**
   * Broadcasts a {@link UserDisconnectedMessageFactory} to all other users when a user disconnects.
   * <p>
   * The web socket connection of the disconnected user should be removed from the
   * {@link SessionRegistry} before the event is fired.
   *
   * @param event The disconnection event.
   */
  public void broadcastUserDisconnected(@ObservesAsync final UserDisconnectedEvent event) {
    final var room = event.getRoom();
    final var userModel = event.getUserModel();
    final var message = this.userDisconnectedMessageFactory.makeMessage(userModel);
    room.getBroadcastService().broadcast(message);
  }

  /**
   * Sends a {@link InitialLandscapeMessage} to the user who connects to the web socket.
   *
   * @param event The connection event.
   */
  public void sendLandscape(@ObservesAsync final UserConnectedEvent event) {
    LOGGER.debug("sending initial landscape message!");
    final var room = event.getRoom();
    final var userModel = event.getUserModel();
    final var session = this.sessionRegistry.lookupSessionOfUser(room, userModel.getId());
    final var message = this.sendLandscapeMessageFactory.makeMessage(room);
    session.send(message);
  }

  @Scheduled(every = "10s")
  public void increment() {
    for (final Room room : this.roomService.getRooms()) {
      final var message = this.timestampUpdateTimerMessageFactory.makeMessage(room);
      room.getBroadcastService().broadcast(message);
    }
  }
}
