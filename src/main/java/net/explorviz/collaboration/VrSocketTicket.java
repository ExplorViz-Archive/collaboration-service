package net.explorviz.collaboration;

import java.time.Instant;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

/**
 * A ticket that allows a user to join a room.
 *
 * The Authentication header and additional information about the user that should join a room
 * cannot be passed via the websocket API. To enable authentication and to initialize the user's
 * model, the user has to draw a ticket via the REST api before establishing a websocket connection.
 */
public class VrSocketTicket {
  /**
   * An unpredictable identifier for this ticket.
   */
  private final String ticketId;

  /**
   * The room that this ticket allows a user to join.
   */
  private final Room room;

  /**
   * The user that will join the room when this ticket is redeemed.
   */
  private final UserModel user;

  /**
   * The time until this ticket is valid.
   */
  private final Instant validUntil;

  public VrSocketTicket(final String ticketId, final Room room, final UserModel user,
      final Instant validUntil) {
    this.ticketId = ticketId;
    this.room = room;
    this.user = user;
    this.validUntil = validUntil;
  }

  public String getTicketId() {
    return this.ticketId;
  }

  public Room getRoom() {
    return this.room;
  }

  public UserModel getUser() {
    return this.user;
  }

  public Instant getValidUntil() {
    return this.validUntil;
  }
}
