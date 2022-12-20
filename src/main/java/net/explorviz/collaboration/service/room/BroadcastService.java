package net.explorviz.collaboration.service.room;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import net.explorviz.collaboration.VrSession;
import net.explorviz.collaboration.message.BroadcastableMessage;
import net.explorviz.collaboration.service.SessionRegistry;

/**
 * A service that can be used to broadcast messages to all connected clients that are registered to
 * the {@link SessionRegistry}.
 */
public class BroadcastService {
  private final SessionRegistry sessionRegistry;
  private final Predicate<VrSession> roomSessionFilter;

  public BroadcastService(final SessionRegistry sessionRegistry,
      final Predicate<VrSession> roomSessionFilter) {
    this.sessionRegistry = sessionRegistry;
    this.roomSessionFilter = roomSessionFilter;
  }

  /**
   * Broadcasts a message to all connected clients.
   *
   * @param message The message to broadcast.
   * @return A future that completes when the message has been set to all web sockets.
   */
  public Future<Void> broadcast(final BroadcastableMessage message) {
    return this.broadcastWhere(message, (session) -> true);
  }

  /**
   * Broadcasts a message to all connected clients except for the given websocket connections.
   *
   * This method is used for example to forward a message to all other clients except for the
   * original sender of the message.
   *
   * @param message The message to broadcast.
   * @param excludedSessions The web socket connections to exclude from the broadcast.
   * @return A future that completes when the message has been send to all other web sockets.
   */
  public Future<Void> broadcastExcept(final BroadcastableMessage message,
      final VrSession...excludedSessions) {
    return this.broadcastWhere(message,
        (session) -> !Arrays.stream(excludedSessions).anyMatch(session::equals));
  }

  /**
   * Broadcasts a message to all connected clients whose web socket connection satisfies the given
   * predicate.
   *
   * @param message The message to broadcast.
   * @param predicate The predicate that tests whether a message should be set or not.
   * @return A future that completes when the message has been set to all users.
   */
  public Future<Void> broadcastWhere(final BroadcastableMessage message,
      final Predicate<VrSession> predicate) {
    final var futures = this.sessionRegistry.getSessions().stream().filter(this.roomSessionFilter)
        .filter(predicate).map((session) -> session.send(message)).toArray();
    return CompletableFuture.allOf((CompletableFuture<?>[]) futures);
  }
}
