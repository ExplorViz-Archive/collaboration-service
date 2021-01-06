package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 * A service that associates user names with websocket connections.
 */
@ApplicationScoped
public class SessionRegistry {
	/**
	 * Maps user names to the corresponding websocket sessions.
	 */
	private final Map<String, Session> sessions = new ConcurrentHashMap<>();
	private final Map<Session, String> userIDs = new ConcurrentHashMap<>();

	/**
	 * Gets all currently open sessions.
	 * 
	 * @return A collection of open sessions.
	 */
	public Collection<Session> getSessions() {
		return sessions.values();
	}

	/**
	 * Associates the given user ID with the given websocket connection.
	 * 
	 * If the user ID is associated with another session already, the existing
	 * association is overwritten.
	 * 
	 * @param username The ID of the user.
	 * @param session  The webdocket connection.
	 */
	public void register(String userID, Session session) {
		sessions.put(userID, session);
		userIDs.put(session, userID);
	}

	/**
	 * Removes the association with a websocket connection for the user with the
	 * given ID.
	 * 
	 * Does nothing if the user ID is not associated with any websocket connection.
	 * 
	 * @param username The name of the user whose websocket connection to forget.
	 */
	public void unregister(String userID) {
		final Session session = sessions.get(userID);
		if (session != null)
			userIDs.remove(session);
		sessions.remove(userID);
	}

	/**
	 * Looks up the associated user id for a given websocket connection.
	 * 
	 * @param session The websocket connection.
	 * @return The user ID or null if websocket connection does not exist.
	 */
	public String lookupId(Session session) {
		final String userID = this.userIDs.get(session);
		if (userID == null) throw new IllegalStateException("Session not found");
		return userID;
	}
}
