package net.explorviz.extension.vr.payload.sendable;

public class LobbyJoinedResponse {
	private final String ticketId;
	private final long validUntil;

	public LobbyJoinedResponse(String ticketId, long validUntil) {
		this.ticketId = ticketId;
		this.validUntil = validUntil;
	}

	public String getTicketId() {
		return ticketId;
	}

	public long getValidUntil() {
		return validUntil;
	}
}
