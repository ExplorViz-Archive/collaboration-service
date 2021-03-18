package net.explorviz.extension.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A service that associates user names with websocket connections.
 */
public class SessionRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRegistry.class);

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
     * @param userID  The ID of the user.
     * @param session The websocket connection.
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
     * @param userID The name of the user whose websocket connection to forget.
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
     * @return The user ID.
     * @throw If websocket connection does not exist.
     */
    public String lookupId(Session session) {
        final String userID = userIDs.get(session);
        if (userID == null) {
            LOGGER.error("Session not found!");
            throw new IllegalStateException("Session not found!");
        }
        return userID;
    }

    /**
     * Looks up the websocket connection that is associated with the given user ID.
     * 
     * @param userID The ID of the user.
     * @return The websocket connection.
     * @throw If websocket connection does not exist.
     */
    public Session lookupSession(String userID) {
        final Session session = sessions.get(userID);
        if (session == null) {
            LOGGER.error("User with ID '{}' not found!", userID);
            throw new IllegalStateException("User with ID '" + userID + "' not found!");
        }
        return session;
    }
}
