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
     * @return A future that completes when the message has been set to all
     *         websockets.
     */
    public Future<Void> broadcast(VRMessage message) {
        return broadcastWhere(message, (session) -> true);
    }

    /**
     * Broadcasts a message to all connected clients except for the given websocket
     * connections.
     * 
     * @param message          The message to broadcast.
     * @param excludedSessions
     * @return
     */
    public Future<Void> broadcastExcept(VRMessage message, Session... excludedSessions) {
        return broadcastWhere(message, (session) -> Arrays.stream(excludedSessions).anyMatch(session::equals));
    }

    /**
     * Broadcasts a message to all connected clients whose websocket connection
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
     * Sends a message via the given websocket connection to a client.
     * 
     * @param message The message to send.
     * @param session The websocket connection to send the message to.
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
}
