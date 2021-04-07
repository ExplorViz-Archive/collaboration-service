package net.explorviz.vr;

import java.util.concurrent.CompletableFuture;

import javax.websocket.Session;

import net.explorviz.vr.message.SendableMessage;
import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.service.Room;

/**
 * Contains all information associated with a currently open websocket
 * connection.
 */
public class VrSession {
	/**
	 * The actual websocket session.
	 */
	private final Session websocketSession;

	/**
	 * The room the user who connected via the websocket is in.
	 */
	private final Room room;

	/**
	 * The model of the user who is connected via this session.
	 */
	private final UserModel user;

	VrSession(Session websocketSession, Room room, UserModel user) {
		this.websocketSession = websocketSession;
		this.room = room;
		this.user = user;
	}

	public Session getWebsocketSession() {
		return websocketSession;
	}

	public Room getRoom() {
		return room;
	}

	public UserModel getUser() {
		return user;
	}

	/**
	 * Sends the given message via the websocket connection of this session to the
	 * connected client.
	 * 
	 * @param message The message to send.
	 * @return A future that completes once the message has been sent.
	 */
	public CompletableFuture<Void> send(SendableMessage message) {
		final var future = new CompletableFuture<Void>();
		websocketSession.getAsyncRemote().sendObject(message, (result) -> {
			if (result.isOK()) {
				future.complete(null);
			} else {
				future.completeExceptionally(result.getException());
			}
		});
		return future;
	}
}
