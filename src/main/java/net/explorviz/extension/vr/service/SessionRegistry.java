package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import net.explorviz.extension.vr.VrSession;

/**
 * A service that keeps track of all open websocket connections.
 */
@ApplicationScoped
public class SessionRegistry {
	/**
	 * Maps websocket connections to sessions.
	 */
	private final Map<Session, VrSession> sessions = new ConcurrentHashMap<>();

	/**
	 * Maps the room and ID of a user to their sessions.
	 */
	private final Map<Room, Map<String, VrSession>> sessionsByRoom = new ConcurrentHashMap<>();

	/**
	 * Gets all currently open sessions.
	 *
	 * @return A collection of open sessions.
	 */
	public Collection<VrSession> getSessions() {
		return sessions.values();
	}

	/**
	 * Associates the given session with its underlying websocket connection.
	 *
	 * If the websocket connection is associated with another session already, the
	 * existing association is overwritten.
	 *
	 * @param session The websocket connection.
	 */
	public void register(VrSession session) {
		final var websocketSession = session.getWebsocketSession();
		sessions.put(websocketSession, session);

		// Create entry for reverse lookup.
		final var sessionsByUserId = sessionsByRoom.computeIfAbsent(session.getRoom(),
				(room) -> new ConcurrentHashMap<>());
		sessionsByUserId.put(session.getUser().getId(), session);
	}

	/**
	 * Removes the association with of the given session with its underlying
	 * websocket connection.
	 *
	 * Does nothing if the connection is not associated with any session.
	 *
	 * @param session The session whose association to remove.
	 */
	public void unregister(VrSession session) {
		final var websocketSession = session.getWebsocketSession();
		sessions.remove(websocketSession);

		// Remove entry for reverse lookup.
		final var room = session.getRoom();
		if (sessionsByRoom.containsKey(room)) {
			final var sessionsByUserId = sessionsByRoom.get(room);
			final var userId = session.getUser().getId();
			sessionsByUserId.remove(userId);

			// Remove entry for the room when all associations have been removed.
			if (sessionsByUserId.isEmpty()) {
				sessionsByRoom.remove(room);
			}
		}
	}

	/**
	 * Looks up the session that is associated with the given websocket connection.
	 * 
	 * @param websocketSession The websocket connection whose session to look up.
	 * @return The session associated with the websocket connection or {@code null}
	 *         if no session has been associated with the websocket connection.
	 */
	public VrSession lookupSession(Session websocketSession) {
		return sessions.get(websocketSession);
	}

	/**
	 * Searches for the session that is associated with the websocket connection of
	 * the user with the given id in the given room.
	 * 
	 * @param room   The room the user whose session to lookup is in.
	 * @param userId The ID of the user whose session to look up.
	 * @return The session associated with the users websocket connection.
	 * @throw If the user ID and room have not been associated with the given
	 *        session.
	 */
	public VrSession lookupSessionOfUser(Room room, String userId) {
		final var sessionsByUserId = sessionsByRoom.get(room);
		if (sessionsByUserId != null && sessionsByUserId.containsKey(userId)) {
			return sessionsByUserId.get(userId);
		}
		throw new IllegalStateException(
				"Session of user '" + userId + "' not found in room '" + room.getRoomId() + "'!");
	}
}
