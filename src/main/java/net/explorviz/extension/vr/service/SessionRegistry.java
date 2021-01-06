package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    private final Map<String, Session> sessions = new HashMap<>();

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
        sessions.remove(userID);
    }
}
