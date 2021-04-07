package net.explorviz.vr;

import java.time.Instant;

import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.service.Room;

/**
 * A ticket that allows a user to join a room.
 * 
 * The Authentication header and additional information about the user that
 * should join a room cannot be passed via the websocket API. To enable
 * authentication and to initialize the user's model, the user has to draw a
 * ticket via the REST api before establishing a websocket connection.
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

	public VrSocketTicket(String ticketId, Room room, UserModel user, Instant validUntil) {
		this.ticketId = ticketId;
		this.room = room;
		this.user = user;
		this.validUntil = validUntil;
	}

	public String getTicketId() {
		return ticketId;
	}

	public Room getRoom() {
		return room;
	}

	public UserModel getUser() {
		return user;
	}

	public Instant getValidUntil() {
		return validUntil;
	}
}
