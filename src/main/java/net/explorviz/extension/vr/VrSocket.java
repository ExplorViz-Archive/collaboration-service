package net.explorviz.extension.vr;

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
import net.explorviz.extension.vr.message.ResponseMessage;
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
import net.explorviz.extension.vr.message.sendable.MenuDetachedForwardMessage;
import net.explorviz.extension.vr.message.sendable.MenuDetachedResponse;
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.SendLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.factory.SelfConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.SendLandscapeMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserConnectedMessageFactory;
import net.explorviz.extension.vr.message.sendable.factory.UserDisconnectedMessageFactory;
import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.RoomService;

@ServerEndpoint(value = "/v2/vr/{room-id}", decoders = { ReceivableMessageDecoder.class }, encoders = {
        SendableMessageEncoder.class })
@ApplicationScoped
public class VrSocket implements ReceivableMessageHandler<ShouldForward, MessageArgs> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VrSocket.class);
    
    @Inject
    RoomService roomService;

    @Inject
    SelfConnectedMessageFactory selfConnectedMessageFactory;

    @Inject
    UserConnectedMessageFactory userConnectedMessageFactory;

    @Inject
    UserDisconnectedMessageFactory userDisconnectedMessageFactory;

    @Inject
    SendLandscapeMessageFactory sendLandscapeMessageFactory;

    @OnOpen
    public void onOpen(@PathParam("room-id") String roomId, Session session) {
        LOGGER.debug("opened websocket");
        final var room = roomService.lookupRoom(roomId);
        final var userModel = room.getUserService().makeUserModel();
        room.getSessionRegistry().register(userModel.getId(), session);
        room.getUserService().addUser(userModel, room);
    }

    @OnClose
    public void onClose(@PathParam("room-id") String roomId, Session session) {
        LOGGER.debug("closed websocket");
        final var room = roomService.lookupRoom(roomId);
        final String userId = room.getSessionRegistry().lookupId(session);
        room.getSessionRegistry().unregister(userId);
        room.getUserService().removeUser(userId, room);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("websocket error", throwable);
    }

    @OnMessage
    public void onMessage(@PathParam("room-id") String roomId, ReceivableMessage message, Session senderSession) {
        final var room = roomService.lookupRoom(roomId);

        // Process the message.
        final var shouldForward = message.handleWith(this, new MessageArgs(room, senderSession));

        // Optionally forward the message.
        if (shouldForward == ShouldForward.FORWARD) {
            final var userId = room.getSessionRegistry().lookupId(senderSession);
            final var forwardedMessage = new ForwardedMessage(userId, message);
            room.getBroadcastService().broadcastExcept(forwardedMessage, senderSession);
        }
    }

    @Override
    public ShouldForward handleAppClosedMessage(AppClosedMessage message, MessageArgs args) {
        final var success = args.room.getEntityService().closeApp(message.getAppID());
        final var response = new ResponseMessage(message.getNonce(), new ObjectClosedResponse(success));
        args.room.getBroadcastService().sendTo(response, args.session);
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleDetachedMenuClosedMessage(DetachedMenuClosedMessage message, MessageArgs args) {
        final var success = args.room.getEntityService().closeDetachedMenu(message.getMenuId());
        final var response = new ResponseMessage(message.getNonce(), new ObjectClosedResponse(success));
        args.room.getBroadcastService().sendTo(response, args.session);
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleObjectGrabbedMessage(ObjectGrabbedMessage message, MessageArgs args) {
        // Try to grab object and respond whether the operation was successful.
        final var success = args.room.getEntityService()
                .grabbObject(args.room.getSessionRegistry().lookupId(args.session), message.getObjectId());
        final var response = new ResponseMessage(message.getNonce(), new ObjectGrabbedResponse(success));
        args.room.getBroadcastService().sendTo(response, args.session);
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleAppOpenedMessage(AppOpenedMessage message, MessageArgs args) {
        args.room.getEntityService().openApp(message.getId(), message.getPosition(), message.getQuaternion(),
                message.getScale());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleMenuDetachedMessage(MenuDetachedMessage message, MessageArgs args) {
        final var objectId = args.room.getEntityService().detachMenu(message.getDetachId(), message.getEntityType(),
                message.getPosition(), message.getQuaternion(), message.getScale());
        final var response = new ResponseMessage(message.getNonce(), new MenuDetachedResponse(objectId));
        args.room.getBroadcastService().sendTo(response, args.session);
        final var forwardMessage = new MenuDetachedForwardMessage(objectId, message.getEntityType(),
                message.getDetachId(), message.getPosition(), message.getQuaternion(), message.getScale());
        args.room.getBroadcastService().broadcastExcept(forwardMessage, args.session);
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleObjectReleasedMessage(ObjectReleasedMessage message, MessageArgs args) {
        args.room.getEntityService().releaseObject(args.room.getSessionRegistry().lookupId(args.session),
                message.getObjectId());
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleObjectMovedMessage(ObjectMovedMessage message, MessageArgs args) {
        final var userId = args.room.getSessionRegistry().lookupId(args.session);
        final var allowedToMove = args.room.getEntityService().moveObject(userId, message.getObjectId(),
                message.getPosition(), message.getQuaternion(), message.getScale());
        if (allowedToMove) {
            return ShouldForward.FORWARD;
        }
        return ShouldForward.NO_FORWARD;
    }

    @Override
    public ShouldForward handleComponentUpdateMessage(ComponentUpdateMessage message, MessageArgs args) {
        args.room.getEntityService().updateComponent(message.getComponentID(), message.getAppID(),
                message.getIsFoundation(), message.getIsOpened());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleHighlightingUpdateMessage(HighlightingUpdateMessage message, MessageArgs args) {
        args.room.getUserService().updateHighlighting(args.room.getSessionRegistry().lookupId(args.session),
                message.getAppID(), message.getEntityID(), message.getEntityType(), message.getIsHighlighted());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleSpectatingUpdateMessage(SpectatingUpdateMessage message, MessageArgs args) {
        args.room.getUserService().updateSpectating(args.room.getSessionRegistry().lookupId(args.session),
                message.getIsSpectating());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserControllersMessage(UserControllersMessage message, MessageArgs args) {
        args.room.getUserService().updateUserControllers(args.room.getSessionRegistry().lookupId(args.session),
                message.getConnect(), message.getDisconnect());
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handlePingUpdateMessage(PingUpdateMessage message, MessageArgs args) {
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleUserPositionsMessage(UserPositionsMessage message, MessageArgs args) {
        args.room.getUserService().updateUserPosition();
        return ShouldForward.FORWARD;
    }

    @Override
    public ShouldForward handleTimestampUpdateMessage(TimestampUpdateMessage message, MessageArgs args) {
        args.room.getEntityService().getLandscape().setTimestamp(message.getTimestamp());
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
        final var room = event.getRoom();
        final var message = selfConnectedMessageFactory.makeMessage(userModel, room);
        room.getBroadcastService().sendToUser(message, userModel.getId());
    }

    /**
     * Broadcasts a {@link UserConnectedMessage} to all other users when a user
     * connects.
     * 
     * @param event The connection event.
     */
    public void broadcastUserConnected(@ObservesAsync UserConnectedEvent event) {
        final var userModel = event.getUserModel();
        final var room = event.getRoom();
        final var message = userConnectedMessageFactory.makeMessage(userModel);
        room.getBroadcastService().broadcastExceptUser(message, userModel.getId());
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
        final var room = event.getRoom();
        final var message = userDisconnectedMessageFactory.makeMessage(userModel);
        room.getBroadcastService().broadcast(message);
    }

    /**
     * Sends a {@link SendLandscapeMessage} to the user who connects to the web
     * socket.
     * 
     * @param event The connection event.
     */
    public void sendLandscape(@ObservesAsync UserConnectedEvent event) {
        final var userModel = event.getUserModel();
        final var room = event.getRoom();
        final var message = sendLandscapeMessageFactory.makeMessage(room);
        room.getBroadcastService().sendToUser(message, userModel.getId());
    }
}

class MessageArgs {
    public final Session session;
    public final Room room;

    public MessageArgs(Room room, Session session) {
        this.room = room;
        this.session = session;
    }
}
