package net.explorviz.extension.vr.service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

import net.explorviz.extension.vr.message.VRMessage;

/**
 * A service that can be used to broadcast messages to all connected clients
 * that are registered to the {@link SessionRegistry}.
 */
@ApplicationScoped
public class BroadcastService {
    @Inject
    SessionRegistry sessionRegistry;

    /**
     * Broadcasts a message to all connected clients.
     * 
     * @param message The message to broadcast.
     * @return A future that completes when the message has been set to all web
     *         sockets.
     */
    public Future<Void> broadcast(VRMessage message) {
        return broadcastWhere(message, (session) -> true);
    }

    /**
     * Broadcasts a message to all connected clients except for the given websocket
     * connections.
     * 
     * This method is used for example to forward a message to all other clients
     * except for the original sender of the message.
     * 
     * @param message          The message to broadcast.
     * @param excludedSessions The web socket connections to exclude from the
     *                         broadcast.
     * @return A future that completes when the message has been send to all other
     *         web sockets.
     */
    public Future<Void> broadcastExcept(VRMessage message, Session... excludedSessions) {
        return broadcastWhere(message, (session) -> !Arrays.stream(excludedSessions).anyMatch(session::equals));
    }

    /**
     * Broadcasts a message to all connected clients except for the web socket
     * connections associated with the given user IDs.
     * 
     * @param message         The message to broadcast.
     * @param excludedUserIds The IDs of the users whose web socket connection to
     *                        exclude from the broadcast.
     * @return A future that completes when the message has been send to all other
     *         web sockets.
     */
    public Future<Void> broadcastExcept(VRMessage message, String... excludedUserIds) {
        return broadcastExcept(message,
                Arrays.stream(excludedUserIds).map(sessionRegistry::lookupSession).toArray((n) -> new Session[n]));
    }

    /**
     * Broadcasts a message to all connected clients whose web socket connection
     * satisfies the given predicate.
     * 
     * @param message   The message to broadcast.
     * @param predicate The predicate that tests whether a message should be set or
     *                  not.
     * @return A future that completes when the message has been set to all users.
     */
    public Future<Void> broadcastWhere(VRMessage message, Predicate<Session> predicate) {
        final var futures = sessionRegistry.getSessions().stream().filter(predicate)
                .map((session) -> sendTo(message, session)).collect(Collectors.toList());
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
    }

    /**
     * Sends a message via the given web socket connection to a client.
     * 
     * @param message The message to send.
     * @param session The web socket connection to send the message to.
     * @return A future that completes once the message has been sent.
     */
    public CompletableFuture<Void> sendTo(VRMessage message, Session session) {
        final var future = new CompletableFuture<Void>();
        session.getAsyncRemote().sendObject(message, (result) -> {
            if (result.isOK()) {
                future.complete(null);
            } else {
                future.completeExceptionally(result.getException());
            }
        });
        return future;
    }

    /**
     * Sends a message via the web socket connection associated with the given user
     * ID.
     * 
     * @param message The message to send.
     * @param userId  The ID of the user whose web socket to send the message to.
     * @return A future that completes once the message has been sent.
     */
    public CompletableFuture<Void> sendTo(VRMessage message, String userId) {
        final var session = sessionRegistry.lookupSession(userId);
        return sendTo(message, session);
    }
}
