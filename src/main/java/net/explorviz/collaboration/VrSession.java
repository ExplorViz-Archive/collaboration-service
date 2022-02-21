package net.explorviz.collaboration;

import java.util.concurrent.CompletableFuture;
import javax.websocket.Session;
import net.explorviz.collaboration.message.SendableMessage;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

/**
 * Contains all information associated with a currently open websocket connection.
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

  public VrSession(final Session websocketSession, final Room room, final UserModel user) {
    this.websocketSession = websocketSession;
    this.room = room;
    this.user = user;
  }

  public Session getWebsocketSession() {
    return this.websocketSession;
  }

  public Room getRoom() {
    return this.room;
  }

  public UserModel getUser() {
    return this.user;
  }

  /**
   * Sends the given message via the websocket connection of this session to the connected client.
   *
   * @param message The message to send.
   * @return A future that completes once the message has been sent.
   */
  public CompletableFuture<Void> send(final SendableMessage message) {
    final var future = new CompletableFuture<Void>();
    this.websocketSession.getAsyncRemote().sendObject(message, (result) -> {
      if (result.isOK()) {
        future.complete(null);
      } else {
        future.completeExceptionally(result.getException());
      }
    });
    return future;
  }
}
