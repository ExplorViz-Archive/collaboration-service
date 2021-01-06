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
    private final Map<Session, String> userIds = new ConcurrentHashMap<>();

    /**
     * Gets all currently open sessions.
     * 
     * @return A collection of open sessions.
     */
    public Collection<Session> getSessions() {
        return sessions.values();
    }

    /**
     * Associates the given user name with the given websocket connection.
     * 
     * If the user name is associated with another session already, the existing
     * association is overwritten.
     * 
     * @param username The name of the user.
     * @param session  The webdocket connection.
     */
    public void register(String username, Session session) {
        sessions.put(username, session);
        userIds.put(session, username);
    }

    /**
     * Removes the association with a websocket connection for the user user with
     * the given name.
     * 
     * Does nothing if the user name is not associated with any websocket
     * connection.
     * 
     * @param username The name of the user whose websocket connection to forget.
     */
    public void unregister(String username) {
    	final Session session = sessions.get(username);
    	if (session != null) userIds.remove(session);
        sessions.remove(username);
    }
    
    /**
     * Looks up the associated user id for a given websocket connection.
     * 
     * @param session The websocket connection.
     * @return The user id or null if websocket connection does not exist.
     */
    public String lookupId(Session session) {
    	return this.userIds.get(session);
    }
}
